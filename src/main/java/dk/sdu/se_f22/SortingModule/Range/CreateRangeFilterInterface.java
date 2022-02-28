package dk.sdu.se_f22.SortingModule.Range;

import dk.sdu.se_f22.SortingModule.Range.Exceptions.InvalidFilterException;

public interface CreateRangeFilterInterface {
    int createRangeFilter(int id, String description, String name, String productAttribute, double min, double max) throws InvalidFilterException;
}
