package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.UnknownFilterTypeException;

import java.util.List;

public interface ReadRangeFilterInterface {

    /**
     * Method first reads all filters from the database.
     * It then returns all filters in a list.
     * @return List<RangeFilter>
     */
    List<RangeFilter> getRangeFilters();

    /**
     * Method first reads a filter from the database.
     * It then returns the filter as a RangeFilter.
     * @param id
     * @return RangeFilter
     */
    RangeFilter getRangeFilter(int id) throws InvalidFilterIdException, UnknownFilterTypeException;
}
