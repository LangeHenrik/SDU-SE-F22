package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.database.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.database.MockDatabase;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

import java.util.List;

public class DBRangeFilterReader implements ReadRangeFilterInterface {
    private DatabaseInterface database;

    public DBRangeFilterReader () {
        database = MockDatabase.getInstance();
    }

    @Override
    public List<DBRangeFilter> getRangeFilters() {
        return database.readAllFilters();
    }

    @Override
    public DBRangeFilter getRangeFilter(int id) throws InvalidFilterIdException {
        if (database.read(id) == null) {
            throw new InvalidFilterIdException("Invalid id");
        }
        return database.read(id);
    }
}
