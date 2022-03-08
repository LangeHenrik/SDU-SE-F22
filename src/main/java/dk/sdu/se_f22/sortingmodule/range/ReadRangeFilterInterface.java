package dk.sdu.se_f22.sortingmodule.range;

import java.util.List;

public interface ReadRangeFilterInterface {
    List<DBRangeFilter> getRangeFilters();
    DBRangeFilter getRangeFilter(int id);
}
