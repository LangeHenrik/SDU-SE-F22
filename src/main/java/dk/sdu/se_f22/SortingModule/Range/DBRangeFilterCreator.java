package dk.sdu.se_f22.SortingModule.Range;

import dk.sdu.se_f22.SortingModule.Range.Exceptions.InvalidFilterException;
import dk.sdu.se_f22.SortingModule.Range.Validators.Validator;

import java.util.HashMap;
import java.util.Map;

public class DBRangeFilterCreator implements CreateRangeFilterInterface{
    Map<Integer, DBRangeFilter> DBfilters;
    DatabaseInterface database;

    public DBRangeFilterCreator() {
        this.DBfilters = new HashMap<>();
        this.database = MockDatabase.getInstance();
    }

    @Override
    public int createRangeFilter(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException {
        Validator.NoNegativeValue(min);
        Validator.NoNegativeValue(max);

        Validator.NoSpecialCharacters(description);
        Validator.NoSpecialCharacters(name);
        Validator.NoSpecialCharacters(productAttribute);

        Validator.MaxLessThanMin(min,max);


        DBRangeFilter createdFilter = database.create(new DBRangeFilter(description, name, productAttribute, min, max));
        return createdFilter.getId();
    }

    public DBRangeFilter getRangeFilterFromDB(int id){
        return database.read(id);
    }
}
