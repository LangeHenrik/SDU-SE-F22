package dk.sdu.se_f22.SortingModule.Range;

import java.util.ArrayList;
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
    }

    public RangeSearchResultMock[] filterResults(RangeSearchResultMock[] results, RangeFilter[] rangeFilters){
        for (RangeFilter rangeFilter : rangeFilters) {
            InternalFilter iFilter = rangeFilterCreator.createInternalFilter(rangeFilter);

            if (iFilter == null) {
                continue;
            }

            results = iFilter.useFilter(results);
        }

        return results;
    }
}
