package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.time.Instant;
import java.util.*;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.utils.Color;
import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.sortingmodule.category.CategoryFilter;
import dk.sdu.se_f22.sortingmodule.infrastructure.data.SaveSearchQuery;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilterCRUD;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilterFilterResults;

/**
 * Implemented version of SortingModule
 */
public class SortingModuleImpl implements SortingModule {
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
    public void addRangeDouble(int rangeId, Double startRange, Double endRange) {
        this.query.addRangeDouble(rangeId, startRange, endRange);
    }

    @Override
    public void addRangeLong(int rangeId, long startRange, long endRange) {
        this.query.addRangeLong(rangeId, startRange, endRange);
    }

    @Override
    public void addRangeInstant(int rangeId, Instant startRange, Instant endRange) {
        this.query.addRangeInstant(rangeId, startRange, endRange);
    }

    public void clearRange() {
        this.query.clearAllRanges();
    }

    @Override
    public void setPagination(int page, int pageSize) {
        this.query.setPagination(page, pageSize);
    }

    @Override
    public void setScoring(int scoring) {
        this.query.setScoring(scoring);

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

        // Filters
        // Category
        CategoryFilter categoryFilter = new CategoryFilter();
        searchHits = categoryFilter.filterProductsByCategory(searchHits, this.query.getCategory());

        // Range
        RangeFilterCRUD filterCRUD = new RangeFilterCRUD();
        List<RangeFilter> selectedFilters = new ArrayList<>();

        for (Map.Entry<Integer, Double[]> tempEntry : this.query.getRangeDouble().entrySet()) {
            try {
                selectedFilters.add(filterCRUD.read(tempEntry.getKey()));

                for (Double d : tempEntry.getValue()) {

                }
            } catch (IdNotFoundException | UnknownFilterTypeException e) {
                e.printStackTrace();
            }
        }



        try {
            SearchHits resultingContent = RangeFilterFilterResults.filterResults(searchHits, selectedFilters);
        } catch (IllegalImplementationException e) {
            e.printStackTrace();
        }




        // Scoring

        // Pagination

        // Return paginated SearchHits
        return searchHits;
    }

    private void saveSearch() {
        SaveSearchQuery.saveSearch(this.query, this.searchString);
    }
}
