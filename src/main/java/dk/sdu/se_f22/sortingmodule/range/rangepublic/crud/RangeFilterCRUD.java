package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

import java.time.Instant;
import java.util.List;

public class RangeFilterCRUD implements RangeFilterCRUDInterface{
    @Override
    public RangeFilter create(String description, String name, String productAttribute, double dbMinToSave, double dbMaxToSave) throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter create(String description, String name, String productAttribute, long dbMinToSave, long dbMaxToSave) throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter create(String description, String name, String productAttribute, Instant dbMinToSave, Instant dbMaxToSave) throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter read(int id) throws InvalidFilterIdException {
        return null;
    }

    @Override
    public RangeFilter delete(int id) throws InvalidFilterIdException {
        return null;
    }

    @Override
    public RangeFilter update(RangeFilter filter, String newName) throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter update(RangeFilter filter, String newName, String newDescription) throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter update(RangeFilter filter, double dbMinToSave, double dbMaxToSave) throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter update(RangeFilter filter, long dbMinToSave, long dbMaxToSave) throws InvalidFilterException {
        return null;
    }

    @Override
    public RangeFilter update(RangeFilter filter, Instant dbMinToSave, Instant dbMaxToSave) throws InvalidFilterException {
        return null;
    }

    @Override
    public List<RangeFilter> readAll() {
        return null;
    }
}
