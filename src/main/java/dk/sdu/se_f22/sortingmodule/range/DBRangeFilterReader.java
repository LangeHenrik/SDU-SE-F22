package dk.sdu.se_f22.sortingmodule.range;

import java.util.List;

public class DBRangeFilterReader implements ReadRangeFilterInterface {
    private DatabaseInterface database;

    public DBRangeFilterReader () {
        database = MockDatabase.getInstance();
    }

    @Override
    public List<DBRangeFilter> getRangeFilters() {
        return database.readAllFilters();
    }

    @Override
    public DBRangeFilter getRangeFilter(int id) {
        return database.read(id);
    }
}
