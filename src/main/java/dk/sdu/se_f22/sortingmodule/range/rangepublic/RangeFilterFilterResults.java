package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;

import java.util.Collection;
import java.util.List;


public abstract class RangeFilterFilterResults {
    /**
     * This is the method that filters the products in the searchHits based on the filters given.
     * <p>
     * You are not allowed to extend {@link RangeFilter} and pass your own instances in the list.
     * You will get an {@link IllegalImplementationException} when using the filters if you try.
     *
     * @param rangeFilters The rangefilters to use for filtering the search hits, they must be in accordance with the filters stored in our DB.
     *                     See {@link RangeFilterCRUDInterface} for details on getting active/valid filters.
     * @return The {@link SearchHits} object that was given as input, but where the products Collection have been filtered
     * using the filters specified in rangeFilters param. If the products collection was null, then it will still be null after this filtering.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    //Warnings suppressed until 4.1 have decided what the types of the collections should be
    public static SearchHits filterResults(SearchHits searchHits, List<RangeFilter> rangeFilters) throws IllegalImplementationException {
        Collection productHits = searchHits.getProducts();

        for (RangeFilter rangeFilter : rangeFilters) {
            if (rangeFilter instanceof RangeFilterClass filterCasted) {
                // Currently, uses the non-strict version of useFilter
                // If a strict version is desired to be available, a companion, method to this, should be implemented where the same function is calle
                // but with a second argument of true.
                // This would get the method which throws an exception if the productAttribute of the filter is not valid
                productHits = filterCasted.useFilter(productHits);
            } else {
                throw new IllegalImplementationException("You are not allowed to write your own implementation of RangeFilter\n" +
                        "Get the filters from the database by calling: IDBRangeFilterCRUD.readAll()");
            }
        }

        searchHits.setProducts(productHits);

        return searchHits;
    }
}
