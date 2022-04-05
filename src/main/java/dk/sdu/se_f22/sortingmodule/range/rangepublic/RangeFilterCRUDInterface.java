package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;

import java.time.Instant;
import java.util.List;

public interface RangeFilterCRUDInterface {
    /**
     * Creates a filter and saves it to the database using double values for dbMinToSave and dbMinToMax
     * Returns a DoubleFilter
     * @param name
     * @param description
     * @param productAttribute
     * @param dbMinToSave
     * @param dbMaxToSave
     * @return DoubleFilter
     */
    RangeFilter create(String name, String description, String productAttribute, double dbMinToSave, double dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException;
    /**
     * Creates a filter and saves it to the database using long values for dbMinToSave and dbMinToMax
     * Returns a LongFilter
     * @param name
     * @param description
     * @param productAttribute
     * @param dbMinToSave
     * @param dbMaxToSave
     * @return LongFilter
     */
    RangeFilter create(String name, String description, String productAttribute, long dbMinToSave, long dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException;
    /**
     * Creates a filter and saves it to the database using long values for dbMinToSave and dbMinToMax
     * Returns a LongFilter
     * @param name
     * @param description
     * @param productAttribute
     * @param dbMinToSave
     * @param dbMaxToSave
     * @return InstantFilter
     */
    RangeFilter create(String name, String description, String productAttribute, Instant dbMinToSave, Instant dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException;

    /**
     * Method first reads a filter from the database.
     * It then returns the filter as a RangeFilter.
     * @param id
     * @return RangeFilter
     */
    RangeFilter read(int id) throws IdNotFoundException, UnknownFilterTypeException;

    /**
     * This method uses the database delete method, which goes into the database <br>
     * and deletes the filter corresponding with the id given as a parameter. <br>
     * Throws IdNotFoundException
     * @param id Integer
     * @return RangeFilter
     */
    RangeFilter delete(int id) throws IdNotFoundException;

    RangeFilter update(RangeFilter filter, String newName) throws InvalidFilterException;
    RangeFilter update(RangeFilter filter, String newName, String newDescription) throws InvalidFilterException;
    RangeFilter update(RangeFilter filter, double dbMinToSave, double dbMaxToSave) throws InvalidFilterException;
    RangeFilter update(RangeFilter filter, long dbMinToSave, long dbMaxToSave) throws InvalidFilterException;
    RangeFilter update(RangeFilter filter, Instant dbMinToSave, Instant dbMaxToSave) throws InvalidFilterException;

    /**
     * Method first reads all filters from the database.
     * It then returns all filters in a list.
     * @return List<RangeFilter>
     */
    List<RangeFilter> readAll();
}
