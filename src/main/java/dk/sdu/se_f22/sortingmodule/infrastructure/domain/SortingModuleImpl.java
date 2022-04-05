package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.util.ArrayList;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.searchmodule.infrastructure.SearchModuleImpl;
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
        SearchModuleImpl searchModule = new SearchModuleImpl();
        return searchModule.search(this.searchString);

        // Filters

        // Scoring

        // Pagination

        // Return paginated SearchHits
    }
    
    private void saveSearch() {
        SaveSearchQuery.saveSearch(this.query);
    }
}
