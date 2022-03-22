package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.crud.RangeFilterDeleter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.crud.RangeFilterReader;

import java.util.List;

public class DBRangeFilterCRUD implements IDBRangeFilterCRUD{
    DBRangeFilterCreator creator;
    RangeFilterReader reader;
    RangeFilterDeleter deleter;

    public DBRangeFilterCRUD(){
        creator = new DBRangeFilterCreator();
        reader = new RangeFilterReader();
        deleter = new RangeFilterDeleter();
    }

    @Override
    public DBRangeFilter create(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException {
        return creator.createRangeFilter(description, name, productAttribute, min, max);
    }

    @Override
    public RangeFilter read(int id) throws InvalidFilterIdException {
        return reader.getRangeFilter(id);
    }

    @Override
    public DBRangeFilter update() {
        return null;
    }

    @Override
    public RangeFilter delete(int id) throws InvalidFilterIdException {
        return deleter.deleteRangeFilter(id);
    }

    @Override
    public List<RangeFilter> readAll() {
        return reader.getRangeFilters();
    }
}
