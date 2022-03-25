package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.crud.RangeFilterCRUDInterface;

import java.util.Collection;
import java.util.List;


public class RangeFilterFilterResults {
    /**
     * This is the method that filters the products in the searchHits based on the filters given.
     * <p>
     * You are nto allowed to extend {@link RangeFilter} and pass your own instances in the list.
     * You will get an {@link IllegalImplementationException} if you try.
     *
     * @param rangeFilters The rangefilters to use for filtering the search hits, they must be in accordance with the filters stored in our DB.
     *                     See {@link RangeFilterCRUDInterface} for details on getting active/valid filters.
     * @return The {@link SearchHits} object that was given as input, but where the products Colloction have been filtered
     * using the filters specified in rangeFilters param.
     */
    public static SearchHits filterResults(SearchHits searchHits, List<RangeFilter> rangeFilters) throws IllegalImplementationException {
        Collection productHits = searchHits.getProducts();

        for (RangeFilter rangeFilter : rangeFilters) {
            if (rangeFilter instanceof RangeFilterClass) {
                RangeFilterClass filterCasted = (RangeFilterClass) rangeFilter;
                productHits = filterCasted.useFilter(productHits);
            } else {
                throw new IllegalImplementationException("You are not allowed to write your own implementation of RangeFilter\n" +
                        "Get the filters from the database by calling: IDBRangeFilterCRUD.readAll()");
            }
        }

        return searchHits;
    }
}
