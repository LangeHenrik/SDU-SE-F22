package dk.sdu.se_f22.sortingmodule.range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDatabase implements DatabaseInterface{
    private static MockDatabase instance;
    private Map<Integer, DBRangeFilter> mockDB;
    private int id = 0;

    static {
        instance = new MockDatabase();
    }

    private MockDatabase() {
        this.mockDB = new HashMap<>();
    }

    public static MockDatabase getInstance() {
        return instance;
    }

    @Override
    public DBRangeFilter read(int id) {
        return this.mockDB.get(id);
    }

    @Override
    public DBRangeFilter create(DBRangeFilter filter) {
        filter.setId(id);
        this.mockDB.put(id++, filter);
        return filter;
    }

    @Override
    public DBRangeFilter update(DBRangeFilter filter) {
        this.mockDB.put(filter.getId(), filter);
        return filter;
    }

    @Override
    public DBRangeFilter delete(int id) {
        DBRangeFilter out = this.mockDB.get(id);
        this.mockDB.remove(id);
        return out;
    }

    @Override
    public List<DBRangeFilter> readAllFilters() {
        return new ArrayList<DBRangeFilter>(this.mockDB.values());
    }
}
