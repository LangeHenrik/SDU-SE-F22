package dk.sdu.se_f22.SortingModule.Range;

import java.util.HashMap;
import java.util.Iterator;
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
    public DBRangeFilter[] getRangeFilters() {
//        DBRangeFilter[] out = new DBRangeFilter[this.map.size()];
//
//        int index = 0;
//        for (Map.Entry<Integer, DBRangeFilter> element : this.map.entrySet()) {
//            out[index] = element.getValue();
//            index++;
//        }
//
//        return out;

        return null;
    }

    @Override
    public DBRangeFilter getRangeFilter(int id) {
        return database.read(id);
    }
}
