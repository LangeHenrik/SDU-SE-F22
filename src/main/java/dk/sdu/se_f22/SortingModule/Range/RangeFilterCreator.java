package dk.sdu.se_f22.SortingModule.Range;

import java.util.List;

public class RangeFilterCreator implements CreateRangeFilterInterface{
    List<DBRangeFilter> DBfilters;

    @Override
    public int createRangeFilter(int id, String description, String name, String productAttribute, double min, double max) {
        DBfilters.add(new DBRangeFilter(id, description, name, productAttribute, min, max));
        return id;
    }
}
