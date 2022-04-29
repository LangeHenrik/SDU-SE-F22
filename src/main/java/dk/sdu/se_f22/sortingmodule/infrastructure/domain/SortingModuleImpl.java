package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.utils.Color;
import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.sortingmodule.category.CategoryFilter;
import dk.sdu.se_f22.sortingmodule.infrastructure.data.SaveSearchQuery;

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
    public void addRange(int rangeId, String startRange, String endRange) {
        this.query.addRange(rangeId, startRange, endRange);
    }

    @Override
    public void clearRange() {
        this.query.clearRange();
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
        CategoryFilter categoryFilter = new CategoryFilter();

        // Scoring

        // Pagination

        // Category
        searchHits = categoryFilter.filterProductsByCategory(searchHits, this.query.getCategory());

        // Return paginated SearchHits

        return searchHits;
    }

    private void saveSearch() {
        SaveSearchQuery.saveSearch(this.query, this.searchString);
    }
}
