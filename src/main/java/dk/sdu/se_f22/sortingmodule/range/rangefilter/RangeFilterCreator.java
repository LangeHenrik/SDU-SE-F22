package dk.sdu.se_f22.sortingmodule.range.rangefilter;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.ReadRangeFilterInterface;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.CreateRangeFilterInterface;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilterCreator;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilterReader;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

import java.util.Collection;
import java.util.List;

public class RangeFilterCreator  implements RangeFilterInterface {
    private CreateRangeFilterInterface dbRangeFilterCreator;
    private ReadRangeFilterInterface dbReader;

    public RangeFilterCreator() {
        this(new DBRangeFilterCreator());
    }

    public RangeFilterCreator(DBRangeFilterCreator dbRangeFilterCreator){
        this.dbRangeFilterCreator = dbRangeFilterCreator;
        try{
            dbRangeFilterCreator.createRangeFilter( "hello object", "uno", "price", 0, 2000);
            dbRangeFilterCreator.createRangeFilter("hello fella", "dos", "height", 0, 4000);
        }catch (InvalidFilterException e){
            System.out.println(e.getMessage());
        }

        dbReader = new DBRangeFilterReader();
    }

    private boolean validateFilter() {
        return true;
    }

    public InternalFilter createInternalFilter(RangeFilter filterCheck) throws InvalidFilterIdException {
        //check if the filter exists in the database
        //if it doesn't, or min, max is invalid
//        return null;
        //if it does and everything is valid:
        DBRangeFilter dbfilter = this.dbReader.getRangeFilter(filterCheck.getId());
        if (validateFilter()) {
            return new InternalFilter(filterCheck.getMin(), filterCheck.getMax(), dbfilter.getProductAttribute());
        } else {
            return null;
        }
    }

    /**This is the method that filters the products in the searchHits based on the filters given.
     *
     * @param rangeFilters The rangefilters to use for filtering the search hits, they must be in accordance with the filters stored in our DB.
     *                     See {@link ReadRangeFilterInterface} for details on getting active/valid filters.
     * @return The {@link SearchHits} object that was given as input, but where the products Colloction have been filtered
     * using the filters specified in rangeFilters param.
     */
    public SearchHits filterResults(SearchHits searchHits, List<RangeFilter> rangeFilters) throws InvalidFilterIdException {
        Collection productHits = searchHits.getProducts();

        for (RangeFilter rangeFilter : rangeFilters) {
            InternalFilter iFilter = this.createInternalFilter(rangeFilter);

            if (iFilter == null) {
                continue;
            }


            productHits = iFilter.useFilter(productHits);
        }

        return searchHits;
    }
}