package dk.sdu.se_f22.SortingModule.Range;

import dk.sdu.se_f22.SortingModule.Range.Exceptions.InvalidFilterException;

import java.util.HashMap;
import java.util.Map;

public class DBRangeFilterCreator implements CreateRangeFilterInterface{
    Map<Integer, DBRangeFilter> DBfilters;

    public DBRangeFilterCreator() {
        this.DBfilters = new HashMap<>();
    }

    @Override
    public int createRangeFilter(int id, String description, String name, String productAttribute, double min, double max) throws InvalidFilterException {
        if(description.equals("") || description.equals(" ")){
            throw new InvalidFilterException("description is null");
        }
        DBfilters.put(id, new DBRangeFilter(id, description, name, productAttribute, min, max));
        return id;
    }

    public DBRangeFilter getRangeFilterFromDB(int id){
        return DBfilters.get(id);
    }
}
