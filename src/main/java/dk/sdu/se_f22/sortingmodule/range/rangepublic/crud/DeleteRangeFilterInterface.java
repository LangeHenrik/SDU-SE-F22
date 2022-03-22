package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

public interface DeleteRangeFilterInterface {
    /**
     * This method uses the database delete method, which goes into the database <br>
     * and deletes the filter corresponding with the id given as a parameter. <br>
     * Throws InvalidFilterIdException
     * @param id Integer
     * @return RangeFilter
     */
    RangeFilter deleteRangeFilter(int id) throws InvalidFilterIdException;
}
