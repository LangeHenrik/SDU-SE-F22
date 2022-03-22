package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

import java.time.Instant;
import java.util.List;

/** This interface is deliberately default modifier, since it is not going to be used outside our package.
 * <br>
 * It defines the mthods, that our daatabase class must implement to allow for performing CRUD
 */
public interface DatabaseInterface {
    RangeFilter create(RangeFilter filterToSaveInDB);

    RangeFilter read(int id) throws UnknownFilterTypeException;
    RangeFilter delete(int id);

    // below mess could be avoided by changing name and Description, to not be final in RangeFilterClass
    // Along with using userMin and userMax as the variables where we store what we should update the database values to
    RangeFilter update(RangeFilter filter);

    List<RangeFilter> readAllFilters();
}
