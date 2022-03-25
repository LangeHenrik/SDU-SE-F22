package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;

public interface DeleteRangeFilterInterface {
    /**
     * This method uses the database delete method, which goes into the database <br>
     * and deletes the filter corresponding with the id given as a parameter. <br>
     * Throws IdNotFoundException
     * @param id Integer
     * @return RangeFilter
     */
    RangeFilter deleteRangeFilter(int id) throws IdNotFoundException, UnknownFilterTypeException;
}
