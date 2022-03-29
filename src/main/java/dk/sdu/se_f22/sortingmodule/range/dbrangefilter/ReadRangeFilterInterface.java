package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

import java.util.List;

public interface ReadRangeFilterInterface {

    /**
     * Method first reads all filters from the database.
     * It then returns all filters in a list.
     * @return List<DBRangeFilter>
     */
    List<DBRangeFilter> getRangeFilters();

    /**
     * Method first reads a filter from the database.
     * It then returns the filter as a DBRangeFilter.
     * @param id
     * @return DBRangeFilter
     */
    DBRangeFilter getRangeFilter(int id) throws InvalidFilterIdException;
}
