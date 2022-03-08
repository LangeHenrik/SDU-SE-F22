package dk.sdu.se_f22.sortingmodule.range;

import java.util.List;
import java.util.Map;

public class DBRangeFilterReader implements ReadRangeFilterInterface {
    private DatabaseInterface database;

    public DBRangeFilterReader () {
        database = MockDatabase.getInstance();
    }

    public Map<Integer, DBRangeFilter> getRangeFilterFromDB () {
        //READER FRA DATABASE
        //Kalde den klasse som reader fra databasen.
        //Bliver det sat ind i et hashmappet
        return null;
    }


    @Override
    public List<DBRangeFilter> getRangeFilters() {
        throw new UnsupportedOperationException("Unsupported");

//        return
    }

    @Override
    public DBRangeFilter getRangeFilter(int id) {
        return database.read(id);
    }
}
