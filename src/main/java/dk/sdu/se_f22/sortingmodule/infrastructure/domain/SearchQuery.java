package dk.sdu.se_f22.sortingmodule.infrastructure.domain;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilterCRUD;

import java.time.Instant;
import java.util.*;

/**
 * Query class, that holds information about a search query from the user.
 */
public class SearchQuery {
    /**
     * Pagination information. 2 places. [page, page size]
     */
    private int[] pagination = { 0, 25 };

    /**
     * List of range filters
     */
    private HashMap<Integer, Double[]> rangeDouble;
    private HashMap<Integer, Long[]> rangeLong;
    private HashMap<Integer, Instant[]> rangeInstant;

    /**
     * List of categories to filter by
     */
    ArrayList<Integer> category;

    /**
     * Scoring method id
     */
    private int scoring = 0;

    public SearchQuery() {
        this.rangeDouble = new HashMap<>();
        this.rangeLong = new HashMap<>();
        this.rangeInstant = new HashMap<>();
        this.category = new ArrayList<>();
        this.scoring = 0;
    }

    /**
     * Set the categories to filter by
     * 
     * @param categories Arraylist with ids of categories
     */
    public void setCategory(ArrayList<Integer> categories) {
        this.category = categories;
    }

    /**
     * Add a new category to filter by
     * 
     * @param category Id of category to filter by
     */
    public void addCategory(int category) {
        this.category.add(category);
    }

    /**
     * Reset category filtering
     */
    public void clearCategory() {
        category = new ArrayList<>();
    }

    /**
     * Add a new range to filter by
     * 
     * @param rangeId    The id of the range to filter by
     * @param startRange The start of the range
     * @param endRange   The end of the range
     */
    public void addRangeDouble(int rangeId, double startRange, double endRange) {
        this.rangeDouble.put(rangeId, new Double[] { startRange, endRange });
    }

    public void addRangeLong(int rangeId, long startRange, long endRange) {
        this.rangeLong.put(rangeId, new Long[] {startRange, endRange });
    }

    public void addRangeInstant(int rangeId, Instant startRange, Instant endRange) {
        this.rangeInstant.put(rangeId, new Instant[] { startRange, endRange });
    }

    /**
     * Reset the range filtering
     */
    public void clearRangeDouble() {
        this.rangeDouble.clear();
    }

    public void clearRangeLong() {
        this.rangeLong.clear();
    }

    public void clearRangeInstant() {
        this.rangeInstant.clear();
    }

    public void clearAllRanges() {
        this.clearRangeDouble();
        this.clearRangeLong();
        this.clearRangeInstant();
    }



    /**
     * Set the pagination information
     * 
     * @param page     Current page to query
     * @param pageSize The amount of hits to return
     */
    public void setPagination(int page, int pageSize) {
        this.pagination[0] = page;
        this.pagination[1] = pageSize;
    }

    public SearchHits paginateHits(SearchHits searchHits) {
        Collection tempProductList = new ArrayList<>();
        Collection tempBrandList = new ArrayList<>();
        Collection tempContentList = new ArrayList<>();

        for (int i = pagination[0]*pagination[1]; i < (pagination[0] + 1)*pagination[1]; i++) {
            if (i < searchHits.getProducts().size()) {
                tempProductList.add(searchHits.getProducts().toArray()[i]);
            }

            if (i < searchHits.getBrands().size()) {
                tempBrandList.add(searchHits.getBrands().toArray()[i]);
            }

            if (i < searchHits.getContents().size()) {
                tempContentList.add(searchHits.getContents().toArray()[i]);
            }
        }

        searchHits.setProducts(tempProductList);
        searchHits.setBrands(tempBrandList);
        searchHits.setContents(tempContentList);

        return searchHits;
    }

    /**
     * Set the scoring method
     * 
     * @param scoring The id of the scoring method
     */
    public void setScoring(int scoring) {
        this.scoring = scoring;
    }

    public List<Integer> getCategory() {
        return this.category;
    }

    public Map<Integer, Double[]> getRangeDouble() {
        return this.rangeDouble;
    }

    public Map<Integer, Long[]> getRangeLong() {
        return this.rangeLong;
    }

    public Map<Integer, Instant[]> getRangeInstant() {
        return this.rangeInstant;
    }

    public ArrayList<Map> getAllRanges() {
        ArrayList<Map> mapList = new ArrayList<>();

        mapList.add(this.rangeDouble);
        mapList.add(this.rangeLong);
        mapList.add(this.rangeInstant);

        return mapList;
    }

    public int getScoring() {
        return this.scoring;
    }

    public int[] getPagination() {
        return this.pagination;
    }

    public List<RangeFilter> getAvailableRangeFilters() {
        RangeFilterCRUD tempRFC = new RangeFilterCRUD();
        return tempRFC.readAll();
    }
}