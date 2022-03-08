package dk.sdu.se_f22.SortingModule.Range;

import java.util.List;

/** This interface is deliberately default modifier, since it is not going to be used outside our package.
 * <br>
 * It defines the mthods, that our daatabase class must implement to allow for performing CRUD
 */
interface DatabaseInterface {
    DBRangeFilter create(DBRangeFilter filter);
    DBRangeFilter read(int id);
    DBRangeFilter update(DBRangeFilter filter);
    DBRangeFilter delete(int id);
    List<DBRangeFilter> readAllFilters();
}
