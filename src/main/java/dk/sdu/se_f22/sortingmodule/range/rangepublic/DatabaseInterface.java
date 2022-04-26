package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;

import java.sql.SQLException;
import java.util.List;

/** This interface is deliberately default modifier, since it is not going to be used outside our package.
 * <br>
 * It defines the methods, that our database class must implement to allow for performing CRUD operations.
 */
public interface DatabaseInterface {
    RangeFilter create(RangeFilter filterToSaveInDB) throws InvalidFilterTypeException, SQLException;

    RangeFilter read(int id) throws UnknownFilterTypeException;

    RangeFilter delete(int id);

    RangeFilter update(RangeFilter updatedFilter);

    List<RangeFilter> readAllFilters();
}
