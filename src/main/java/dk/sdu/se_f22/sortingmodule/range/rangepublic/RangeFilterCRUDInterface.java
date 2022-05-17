package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.*;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public interface RangeFilterCRUDInterface {
    /**
     * Creates a filter and saves it to the database using double values for dbMinToSave and dbMinToMax
     * Returns a DoubleFilter
     * @param name String
     * @param description String
     * @param productAttribute String
     * @param dbMinToSave double
     * @param dbMaxToSave double
     * @return DoubleFilter
     */
    RangeFilter create(String name, String description, String productAttribute, double dbMinToSave, double dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException;
    /**
     * Creates a filter and saves it to the database using long values for dbMinToSave and dbMinToMax
     * Returns a LongFilter
     * @param name String
     * @param description String
     * @param productAttribute String
     * @param dbMinToSave long
     * @param dbMaxToSave long
     * @return LongFilter
     */
    RangeFilter create(String name, String description, String productAttribute, long dbMinToSave, long dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException;
    /**
     * Creates a filter and saves it to the database using long values for dbMinToSave and dbMinToMax
     * Returns a LongFilter
     * @param name String
     * @param description String
     * @param productAttribute String
     * @param dbMinToSave Instant
     * @param dbMaxToSave Instant
     * @return InstantFilter
     */
    RangeFilter create(String name, String description, String productAttribute, Instant dbMinToSave, Instant dbMaxToSave) throws InvalidFilterException, InvalidFilterTypeException;

    /**
     * Method first reads a filter from the database.
     * It then returns the filter as a RangeFilter.
     * @param id Integer
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
    RangeFilter delete(int id) throws IdNotFoundException, UnknownFilterTypeException;

    /**
     * This method updates a RangeFilter's name attribute <br>
     * and then also updates the RangeFilter's name in the database.
     * @param filter RangeFilter
     * @param newName String
     * @return RangeFilter with the modified name from database.
     * @throws InvalidFilterException Should be thrown if newName is containing special characters. <br>
     * However currently only throws if newName only contains a special character as the only character.
     */
    RangeFilter update(RangeFilter filter, String newName) throws SQLException, RangeFilterException;

    /**
     * This method updates a RangeFilter's name and description attributes <br>
     * and then also updates the RangeFilter's name and description in the database.
     * @param filter RangeFilter
     * @param newName String
     * @param newDescription String
     * @return RangeFilter with the modified name and description from database.
     * @throws InvalidFilterException Should be thrown if newName or description contains special characters. <br>
     * However currently only throws if newName or description only contains a special character as the only character.
     */
    RangeFilter update(RangeFilter filter, String newName, String newDescription) throws RangeFilterException, SQLException;

    /**
     * This method updates a RangeFilter's (of type DoubleFilter) DB_MIN and DB_MAX values <br>
     * and then updates the corresponding RangeFilter in the database with the same values.
     * @param filter RangeFilter
     * @param dbMinToSave Double
     * @param dbMaxToSave Double
     * @return RangeFilter with the modified DB_MIN and DB_MAX from database
     * @throws InvalidFilterException Throws if dbMinToSave or dbMaxToSave is negative <br>
     * or if dbMinToSave is greater than dbMaxToSave.
     */
    RangeFilter update(RangeFilter filter, double dbMinToSave, double dbMaxToSave) throws RangeFilterException, SQLException;

    /**
     * This method updates a RangeFilter's (of type LongFilter) DB_MIN and DB_MAX values <br>
     * and then updates the corresponding RangeFilter in the database with the same values.
     * @param filter RangeFilter
     * @param dbMinToSave Long
     * @param dbMaxToSave Long
     * @return RangeFilter with the modified DB_MIN and DB_MAX from database
     * @throws InvalidFilterException Throws if dbMinToSave or dbMaxToSave is negative <br>
     * or if dbMinToSave is greater than dbMaxToSave.
     */
    RangeFilter update(RangeFilter filter, long dbMinToSave, long dbMaxToSave) throws RangeFilterException, SQLException;

    /**
     * This method updates a RangeFilter's (of type TimeFilter) DB_MIN and DB_MAX values <br>
     * and then updates the corresponding RangeFilter in the database with the same values.
     * @param filter RangeFilter
     * @param dbMinToSave Instant
     * @param dbMaxToSave Instant
     * @return RangeFilter with the modified DB_MIN and DB_MAX from database
     * @throws InvalidFilterException Throws if dbMinToSave is later than dbMaxToSave.
     */
    RangeFilter update(RangeFilter filter, Instant dbMinToSave, Instant dbMaxToSave) throws RangeFilterException, SQLException;

    /**
     * Method first reads all filters from the database.
     * It then returns all filters in a list.
     * @return List<RangeFilter>
     */
    List<RangeFilter> readAll();
}
