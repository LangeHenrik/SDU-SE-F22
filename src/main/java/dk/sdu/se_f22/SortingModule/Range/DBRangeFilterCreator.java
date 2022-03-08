package dk.sdu.se_f22.SortingModule.Range;

import dk.sdu.se_f22.SortingModule.Range.Exceptions.InvalidFilterException;
import dk.sdu.se_f22.SortingModule.Range.Validators.Validator;

public class DBRangeFilterCreator implements CreateRangeFilterInterface{
    DatabaseInterface database;

    public DBRangeFilterCreator() {
        this.database = MockDatabase.getInstance();
    }

    public static DBRangeFilter createAndCheckFilter(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException{
        Validator.NoNegativeValue(min);
        Validator.NoNegativeValue(max);

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(min,max);

        return new DBRangeFilter(description, name, productAttribute, min, max);
    }

    @Override
    public int createRangeFilter(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException {
        DBRangeFilter filter = createAndCheckFilter(description, name, productAttribute, min, max);

        DBRangeFilter createdFilter = database.create(filter);
        return createdFilter.getId();
    }
}
