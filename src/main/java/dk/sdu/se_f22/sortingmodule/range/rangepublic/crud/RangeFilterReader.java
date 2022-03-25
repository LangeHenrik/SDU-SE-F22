package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.Database;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;

import java.util.List;

public class RangeFilterReader implements ReadRangeFilterInterface {
    private DatabaseInterface database;

    public RangeFilterReader() {
        database = new Database();
    }

    @Override
    public List<RangeFilter> getRangeFilters() {
        return database.readAllFilters();
    }

    @Override
    public RangeFilter getRangeFilter(int id) throws IdNotFoundException, UnknownFilterTypeException {
        // Refactor needed
        // In this implementation, you make the same call to the database twice,
        // you should use a local variable instead, like:
//        RangeFilter result = database.read(id);
        RangeFilter result = database.read(id);
        if (result == null) {
            throw new IdNotFoundException("Invalid id");
        }
        return result;
    }
}
