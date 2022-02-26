package dk.sdu.se_f22.SortingModule.Range;

/**
 * 1. Filter(id, min, max) bliver sendt fra SOM-1
 * 2. Vi bruger InternalFilter til at matche Filter og DBRangeFilter
 *      2.1 InternalFilter(Filter, productAttribute)
 * 3. Internal filter bruges til at validere på
**/

public class RangeMain {
    public static void main(String[] args) {

    }

    public Object[] filterResults(Object[] results, RangeFilter[] rangeFilters){
        for (RangeFilter rangeFilter : rangeFilters) {
            InternalFilter iFilter = RangeFilterChecker.check(rangeFilter);
            if (iFilter == null ) {
                continue;
            }

            results = iFilter.useFilter(results);
        }

        return results;
    }
}
