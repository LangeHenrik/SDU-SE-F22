package dk.sdu.se_f22.SortingModule.Range;

public interface ReadRangeFilterInterface {
    DBRangeFilter[] getRangeFilters();
    DBRangeFilter getRangeFilter(int id);
}
