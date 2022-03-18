package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.database.Database;
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
        // Refactor needed
        // In this implementation, you make the same call to the database twice,
        // you should use a local variable instead, like:
        // DBRangeFilter result = database.read(id);
        if (database.read(id) == null) {
            throw new InvalidFilterIdException("Invalid id");
        }
        return database.read(id);
    }
}
