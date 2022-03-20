package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

public interface DeleteRangeFilterInterface {
    /**
     * This method uses the database delete method, which goes into the database <br>
     * and deletes the filter corresponding with the id given as a parameter. <br>
     * Throws InvalidFilterIdException
     * @param id Integer
     * @return DBRangeFilter
     */
    DBRangeFilter deleteRangeFilter(int id) throws InvalidFilterIdException;
}
