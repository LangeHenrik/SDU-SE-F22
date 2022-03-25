package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;


import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.Database;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;

public class RangeFilterDeleter implements DeleteRangeFilterInterface{
    private DatabaseInterface database;

    RangeFilterDeleter() {
        database = new Database();
    }

    @Override
    public RangeFilter deleteRangeFilter(int id) throws IdNotFoundException, UnknownFilterTypeException {
        RangeFilter result = database.delete(id);
        if (result==null) {
            throw new IdNotFoundException("Invalid id");
        }
        return result;
    }
}
