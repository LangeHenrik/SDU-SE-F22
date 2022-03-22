package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.database.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.database.MockDatabase;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

import java.util.List;

public class RangeFilterReader implements ReadRangeFilterInterface {
    private DatabaseInterface database;

    public RangeFilterReader() {
        database = MockDatabase.getInstance();
    }

    @Override
    public List<RangeFilter> getRangeFilters() {
        return database.readAllFilters();
    }

    @Override
    public RangeFilter getRangeFilter(int id) throws InvalidFilterIdException {
        // Refactor needed
        // In this implementation, you make the same call to the database twice,
        // you should use a local variable instead, like:
//        RangeFilter result = database.read(id);
        if (database.read(id) == null) {
            throw new InvalidFilterIdException("Invalid id");
        }
        return database.read(id);
    }
}
