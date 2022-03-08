package dk.sdu.se_f22.SortingModule.Range;

import dk.sdu.se_f22.SortingModule.Range.Exceptions.InvalidFilterException;
import dk.sdu.se_f22.SortingModule.Range.Validators.Validator;

import java.util.HashMap;
import java.util.Map;

public class DBRangeFilterCreator implements CreateRangeFilterInterface{
    Map<Integer, DBRangeFilter> DBfilters;

    public DBRangeFilterCreator() {
        this.DBfilters = new HashMap<>();
    }

    @Override
    public int createRangeFilter(int id, String description, String name, String productAttribute, double min, double max) throws InvalidFilterException {

        Validator validator = new Validator();

        validator.NoNegativeValue(min);
        validator.NoNegativeValue(max);

        validator.NoSpecialCharacters(description);
        validator.NoSpecialCharacters(name);
        validator.NoSpecialCharacters(productAttribute);

        validator.MaxLessThanMin(min,max);

        DBfilters.put(id, new DBRangeFilter(id, description, name, productAttribute, min, max));
        return id;
    }

    public DBRangeFilter getRangeFilterFromDB(int id){
        return DBfilters.get(id);
    }

}
