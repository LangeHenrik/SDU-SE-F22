package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;

public interface CreateRangeFilterInterface{
    int createRangeFilter(String description, String name, String productAttribute, double min, double max) throws InvalidFilterException;
}
