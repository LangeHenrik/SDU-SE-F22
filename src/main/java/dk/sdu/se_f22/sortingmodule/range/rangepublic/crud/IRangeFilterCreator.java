package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;

import java.time.Instant;

public interface IRangeFilterCreator{
    RangeFilter createRangeFilter(int id, String description, String name, String productAttribute, double min, double max) throws InvalidFilterException;
    RangeFilter createRangeFilter(int id,String description, String name, String productAttribute, long min, long max) throws InvalidFilterException;
    RangeFilter createRangeFilter(int id,String description, String name, String productAttribute, Instant min, Instant max) throws InvalidFilterException;
}

