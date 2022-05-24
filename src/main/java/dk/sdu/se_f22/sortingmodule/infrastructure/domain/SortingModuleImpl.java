package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.time.Instant;
import java.util.*;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.utils.Color;
import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.sortingmodule.category.CategoryFilter;
import dk.sdu.se_f22.sortingmodule.infrastructure.data.MockData;
import dk.sdu.se_f22.sortingmodule.infrastructure.data.SaveSearchQuery;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.*;
import dk.sdu.se_f22.sortingmodule.scoring.IScoring;
import dk.sdu.se_f22.sortingmodule.scoring.ScoreSortType;
import dk.sdu.se_f22.sortingmodule.scoring.Scoring;

/**
 * Implemented version of SortingModule
 */
public class SortingModuleImpl implements SortingModule {

    // Dont change these variables. change them in SortingModuleDemo.java
    private boolean useMockDataBrand = false;
    private boolean useMockDataContent = false;
    private boolean useMockDataProduct = false;

    /**
     * The search query object, that holds query information
     */
    private SearchQuery query;
    /**
     * Search text
     */
    private String searchString;

    public SortingModuleImpl() {
        this.query = new SearchQuery();
    }

    @Override
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public void setCategory(ArrayList<Integer> categories) {
        this.query.setCategory(categories);

    }

    @Override
    public void addCategory(int category) {
        this.query.addCategory(category);

    }

    @Override
    public void clearCategory() {
        this.query.clearCategory();
    }

    @Override
    public void addRange(int rangeId, Double startRange, Double endRange) {
        this.query.addRange(rangeId, startRange, endRange);
    }

    @Override
    public void addRange(int rangeId, long startRange, long endRange) {
        this.query.addRange(rangeId, startRange, endRange);
    }

    @Override
    public void addRange(int rangeId, Instant startRange, Instant endRange) {
        this.query.addRange(rangeId, startRange, endRange);
    }

    @Override
    public void clearRange() {
        this.query.clearAllRanges();
    }

    @Override
    public void setPagination(int page, int pageSize) {
        this.query.setPagination(page, pageSize);
    }

    @Override
    public void setScoring(ScoreSortType scoring) {
        this.query.setScoring(scoring);

    }

    @Override
    public List<RangeFilter> getAvailableRangeFilters() {
        return this.query.getAvailableRangeFilters();
    }

    @Override
    public void printAvailableRangeFilters() {
        System.out.println(this.getAvailableRangeFilters().toString()
                .replaceAll("ID", "\n\tID")
                .replaceAll("RangeFilterClass", "")
                .replaceAll("[\\[\\]{}]", "")
                .replaceAll("LongFilter", "\nLongFilter")
                .replaceAll("DoubleFilter", "\nDoubleFilter")
                .replaceAll("TimeFilter", "\nTimeFilter")
                .replaceAll(", ", "\n\t")
        );
    }

    @Override
    public SearchHits paginateHits(SearchHits searchHits) {
        return this.query.paginateHits(searchHits);
    }
  
    @Override
    public List getAllCategories() {
        CategoryFilter categoryFilter = new CategoryFilter();
        return categoryFilter.getAllCategories();
    }

    @Override
    public SearchHits search() {
        // Save the query
        this.saveSearch();

        // Search
        SearchModule searchModule = new SearchModuleImpl();
        
        SearchHits searchHits;
        try {
            searchHits = searchModule.search(this.searchString);
        } catch (NoSuchElementException e) {
            System.out.println(Color.YELLOW + "Something went wrong when searching!" + Color.RESET);
            searchHits = new SearchHits();
        }

        // Mock Data if requestd.
        if( this.useMockDataContent || this.useMockDataBrand || this.useMockDataProduct ) {
            MockData mockData = new MockData(this.useMockDataBrand, this.useMockDataContent, this.useMockDataProduct);
            searchHits = mockData.updateSearchHits(searchHits);
        }

        // Filters
        // Category
        CategoryFilter categoryFilter = new CategoryFilter();
        searchHits = categoryFilter.filterProductsByCategory(searchHits, this.query.getCategory());

        // Range
        RangeFilterCRUD filterCRUD = new RangeFilterCRUD();
        List<RangeFilter> selectedFilters = new ArrayList<>();

        this.query.getRangeDouble().forEach((Integer id, Double[] boundaries) -> {
            try {
                selectedFilters.add(filterCRUD.read(id));
                if (selectedFilters.get(selectedFilters.size() - 1).getType() == FilterTypes.DOUBLE) {
                    selectedFilters.get(selectedFilters.size() - 1).setUserMin(boundaries[0]);
                    selectedFilters.get(selectedFilters.size() - 1).setUserMax(boundaries[1]);
                }
            } catch (IdNotFoundException | UnknownFilterTypeException | InvalidFilterTypeException e) {
                e.printStackTrace();
            }
        });

        this.query.getRangeLong().forEach((Integer id, Long[] boundaries) -> {
            try {
                selectedFilters.add(filterCRUD.read(id));
                if (selectedFilters.get(selectedFilters.size() - 1).getType() == FilterTypes.LONG) {
                    selectedFilters.get(selectedFilters.size() - 1).setUserMin(boundaries[0]);
                    selectedFilters.get(selectedFilters.size() - 1).setUserMax(boundaries[1]);
                }
            } catch (IdNotFoundException | UnknownFilterTypeException | InvalidFilterTypeException e) {
                e.printStackTrace();
            }
        });

        this.query.getRangeInstant().forEach((Integer id, Instant[] boundaries) -> {
            try {
                selectedFilters.add(filterCRUD.read(id));
                if (selectedFilters.get(selectedFilters.size() - 1).getType() == FilterTypes.TIME) {
                    selectedFilters.get(selectedFilters.size() - 1).setUserMin(boundaries[0]);
                    selectedFilters.get(selectedFilters.size() - 1).setUserMax(boundaries[1]);
                }
            } catch (IdNotFoundException | UnknownFilterTypeException | InvalidFilterTypeException e) {
                e.printStackTrace();
            }
        });


        try {
            searchHits = RangeFilterFilterResults.filterResults(searchHits, selectedFilters);
        } catch (IllegalImplementationException e) {
            e.printStackTrace();
        }

        // Scoring
        IScoring scoring = new Scoring();
        searchHits = scoring.scoreSort(searchHits, this.query.getScoring());

        // Pagination
        searchHits = paginateHits(searchHits);

        // Return paginated SearchHits
        return searchHits;
    }

    private void saveSearch() {
        SaveSearchQuery.saveSearch(this.query, this.searchString);
    }

    /**
     * Following  method, is used to enable mock data
     */
    public void useMockData(boolean b, boolean c, boolean p) {
        this.useMockDataBrand = b;
        this.useMockDataContent = c;
        this.useMockDataProduct = p;
    }



}