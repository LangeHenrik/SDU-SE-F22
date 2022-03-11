package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

import java.util.List;

public interface IDBRangeFilterCRUD {
    DBRangeFilter create(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException;
    DBRangeFilter read(int id) throws InvalidFilterIdException;
    DBRangeFilter update() throws InvalidFilterException;
    DBRangeFilter delete(int id) throws InvalidFilterIdException;

    List<DBRangeFilter> readAll();
}
