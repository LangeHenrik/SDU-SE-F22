package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.database.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.database.MockDatabase;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

public class DBRangeFilterDeleter implements DeleteRangeFilterInterface{
    private DatabaseInterface database;

    DBRangeFilterDeleter() {
        database = MockDatabase.getInstance();
    }

    @Override
    public DBRangeFilter deleteRangeFilter(int id) throws InvalidFilterIdException {
        if (database.read(id)==null) {
            throw new InvalidFilterIdException("Invalid id");
        }
        return database.delete(id);
    }
}
