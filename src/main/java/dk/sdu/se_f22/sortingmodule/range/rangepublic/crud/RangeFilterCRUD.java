package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.*;
import dk.sdu.se_f22.sortingmodule.range.validators.Validator;

import java.time.Instant;
import java.util.List;

public class RangeFilterCRUD implements RangeFilterCRUDInterface{
    Database database = new Database();
    @Override
    public RangeFilter create(String description, String name, String productAttribute, double dbMinToSave, double dbMaxToSave) throws InvalidFilterException {
        Validator.NoNegativeValue(dbMinToSave);
        Validator.NoNegativeValue(dbMaxToSave);

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(dbMinToSave,dbMaxToSave);

        return database.create(new DoubleFilter(description, name, productAttribute, dbMinToSave, dbMaxToSave));
    }

    @Override
    public RangeFilter create(String description, String name, String productAttribute, long dbMinToSave, long dbMaxToSave) throws InvalidFilterException {
        Validator.NoNegativeValue(dbMinToSave);
        Validator.NoNegativeValue(dbMaxToSave);

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(dbMinToSave,dbMaxToSave);

        return database.create(new LongFilter(description, name, productAttribute, dbMinToSave, dbMaxToSave));
    }

    @Override
    public RangeFilter create(String description, String name, String productAttribute, Instant dbMinToSave, Instant dbMaxToSave) throws InvalidFilterException {

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(dbMinToSave,dbMaxToSave);

        return database.create(new TimeFilter(description, name, productAttribute, dbMinToSave, dbMaxToSave));
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
