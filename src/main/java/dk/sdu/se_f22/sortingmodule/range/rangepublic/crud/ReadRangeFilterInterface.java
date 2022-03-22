package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

import java.util.List;

public interface ReadRangeFilterInterface {

    /**
     * Method first reads all filters from the database.
     * It then returns all filters in a list.
     * @return List<DBRangeFilter>
     */
    List<RangeFilter> getRangeFilters();

    /**
     * Method first reads a filter from the database.
     * It then returns the filter as a DBRangeFilter.
     * @param id
     * @return DBRangeFilter
     */
    RangeFilter getRangeFilter(int id) throws InvalidFilterIdException;
}
