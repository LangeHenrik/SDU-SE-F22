package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

import java.util.List;

public class RangeFilterCRUD implements RangeFilterCRUDInterface{
    @Override
    public RangeFilter create(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter read(int id) throws InvalidFilterIdException {
        return null;
    }

    @Override
    public RangeFilter update() throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter delete(int id) throws InvalidFilterIdException {
        return null;
    }

    @Override
    public List<RangeFilter> readAll() {
        return null;
    }
}
