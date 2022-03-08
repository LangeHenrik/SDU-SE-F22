package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sortingmodule.infrastructure.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

import java.util.Collection;
import java.util.List;

/**
 * 1. Filter(id, min, max) bliver sendt fra SOM-1
 * 2. Vi bruger InternalFilter til at matche Filter og DBRangeFilter
 *      2.1 InternalFilter(Filter, productAttribute)
 * 3. Internal filter bruges til at validere på
**/

public class RangeMain implements RangeFilterInterface{
    private RangeFilterCreator rangeFilterCreator;

    public RangeMain() {
        this.rangeFilterCreator = new RangeFilterCreator();
    }

    public static void main(String[] args) {
        System.out.println("living");
    }

    /**
     * rangeFilters should be changed to a list instead
     * Throw an exception if the internalfilters are invalid
     * @param searchHits
     * @param rangeFilters The rangefilters to use for filtering the search hits
     * @return
     */
    public SearchHits filterResults(SearchHits searchHits, List<RangeFilter> rangeFilters) throws InvalidFilterIdException {
        Collection productHits = searchHits.getProducts();

        for (RangeFilter rangeFilter : rangeFilters) {
            InternalFilter iFilter = rangeFilterCreator.createInternalFilter(rangeFilter);

            if (iFilter == null) {
                continue;
            }


            productHits = iFilter.useFilter(productHits);
        }


        return searchHits;
    }
}
