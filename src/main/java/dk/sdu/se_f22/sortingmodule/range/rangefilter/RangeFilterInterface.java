package dk.sdu.se_f22.sortingmodule.range.rangefilter;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

import java.util.List;

public interface RangeFilterInterface {
    /**This is the method for filtering searchHits using an array of RangeFilters.
     * The rangefilters must match an id in the database of rangeFilters.
     * Additionally their min and max must be within the range specified by that filter.
     * If it fails either criteria the filter will be skipped.
     * If a product does not have the attribute that a filter is comparing, it will <i>not</i> be removed from the results.
      * @param searchHits The searchHits to filter
     *  @param rangeFilters The rangefilters to use for filtering the search hits
     * @return The {@link SearchHits} object, where the products attribute now contains the filtered list
     */
    SearchHits filterResults(SearchHits searchHits, List<RangeFilter> rangeFilters) throws InvalidFilterIdException;
}
