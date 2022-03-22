package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.rangepublic.*;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.validators.Validator;

import java.time.Instant;

public class RangeFilterCreator implements IRangeFilterCreator {
    DatabaseInterface database;

    public RangeFilterCreator() {
        this.database = new Database();
    }

    @Override
    public RangeFilter createRangeFilter(int id, String description, String name, String productAttribute, double min, double max) throws InvalidFilterException{
        Validator.NoNegativeValue(min);
        Validator.NoNegativeValue(max);

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(min,max);

        return new DoubleFilter(id, description, name, productAttribute, min, max);
    }

    @Override
    public RangeFilter createRangeFilter(int id, String description, String name, String productAttribute, long min, long max) throws InvalidFilterException{
        Validator.NoNegativeValue(min);
        Validator.NoNegativeValue(max);

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(min,max);

        return database.create(new LongFilter(id, description, name, productAttribute, min, max));
    }

    @Override
    public RangeFilter createRangeFilter(int id, String description, String name, String productAttribute, Instant min, Instant max) throws InvalidFilterException{

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(min,max);

        return new TimeFilter(id, description, name, productAttribute, min, max);
    }


    /* Probably not relevant
    @Override
    public RangeFilter createRangeFilter(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException {
        RangeFilter filter = createAndCheckFilter(description, name, productAttribute, min, max);

        RangeFilter createdFilter = database.create(filter);
        return createdFilter;
    }*/
}
