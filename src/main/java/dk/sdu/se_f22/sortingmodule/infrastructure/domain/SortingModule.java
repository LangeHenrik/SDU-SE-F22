package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import java.util.ArrayList;

import dk.sdu.se_f22.sharedlibrary.SearchHits;

/**
 * Search product, brand and content with range- and category filtering and scoring as well as pagination
 */
public interface SortingModule {
    /**
     * Set the search string the users enters, when searching
     *
     * @param searchString A text string from user
     */
    public void setSearchString(String searchString);

    /**
     * Set the categories for the search filter
     * 
     * @param categories List of all categories to filter by, as a ArrayList
     */
    public void setCategory(ArrayList<Integer> categories);

    /**
     * Add a category for the search filter
     * 
     * @param category Category id, to add to the total filter list
     */
    public void addCategory(int category);

    /**
     * Remove all category filters from the search
     */
    public void clearCategory();

    /**
     * Add a range to filter by
     * 
     * @param rangeId    The id of the range to filter by
     * @param startRange The start of the range - Formattet as a string, but should align with the data type the range require
     * @param endRange   The start of the range - Formattet as a string, but should align with the data type the range require
     */
    public void addRange(int rangeId, String startRange, String endRange);

    /**
     * Remove all range filters from the search
     */
    public void clearRange();

    /**
     * Set the paginations for the search result
     * 
     * @param page The page, for the search. -1 for the first page.
     * @param pageSize The number of hits to return for the current page. The default is 25.
     */
    public void setPagination(int page, int pageSize);

    /**
     * Set the scoring method for the search result
     * 
     * @param scoring Scoring method
     */
    public void setScoring(int scoring);

    /**
     * Run search, and get the hits the search creates, that is filtered and paginated
     */
    public SearchHits search();
}
