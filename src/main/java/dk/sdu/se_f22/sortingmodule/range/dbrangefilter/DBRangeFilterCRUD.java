package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

import java.util.List;

public class DBRangeFilterCRUD implements IDBRangeFilterCRUD{
    DBRangeFilterCreator creator;
    DBRangeFilterReader reader;

    public DBRangeFilterCRUD(){
        creator = new DBRangeFilterCreator();
        reader = new DBRangeFilterReader();
    }

    @Override
    public DBRangeFilter create(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException {
        return creator.createRangeFilter(description, name, productAttribute, min, max);
    }

    @Override
    public DBRangeFilter read(int id) throws InvalidFilterIdException {
        return reader.getRangeFilter(id);
    }

    @Override
    public DBRangeFilter update() {
        return null;
    }

    @Override
    public DBRangeFilter delete(int id) throws InvalidFilterIdException {
        return null;
    }

    @Override
    public List<DBRangeFilter> readAll() {
        return reader.getRangeFilters();
    }
}
