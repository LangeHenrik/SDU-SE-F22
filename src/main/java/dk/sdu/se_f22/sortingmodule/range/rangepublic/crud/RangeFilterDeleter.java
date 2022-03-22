package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.database.Database;
import dk.sdu.se_f22.sortingmodule.range.database.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.database.MockDatabase;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

public class RangeFilterDeleter implements DeleteRangeFilterInterface{
    private DatabaseInterface database;

    RangeFilterDeleter() {
        database = new Database();
    }

    @Override
    public RangeFilter deleteRangeFilter(int id) throws InvalidFilterIdException {
        if (database.read(id)==null) {
            throw new InvalidFilterIdException("Invalid id");
        }
        return database.delete(id);
    }
}
