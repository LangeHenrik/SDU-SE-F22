package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;


import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.Database;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.UnknownFilterTypeException;

public class RangeFilterDeleter implements DeleteRangeFilterInterface{
    private DatabaseInterface database;

    RangeFilterDeleter() {
        database = new Database();
    }

    @Override
    public RangeFilter deleteRangeFilter(int id) throws InvalidFilterIdException, UnknownFilterTypeException {
        if (database.read(id)==null) {
            throw new InvalidFilterIdException("Invalid id");
        }
        return database.delete(id);
    }
}
