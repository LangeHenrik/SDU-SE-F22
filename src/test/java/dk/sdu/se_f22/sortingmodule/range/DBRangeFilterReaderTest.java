package dk.sdu.se_f22.sortingmodule.range;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class DBRangeFilterReaderTest {
    @Nested
    @DisplayName("Read RangeFiltersFromDB")
    class createDbFilters {
    }

    /*
    Ancient relic of the past...
    public boolean equals(DBRangeFilter DBRF1, DBRangeFilter DBRF2) {
        if (DBRF1.getId() == DBRF2.getId()) {
            if (DBRF1.getName() == DBRF2.getName()) {
                if (DBRF1.getDescription() == DBRF2.getDescription()) {
                    if (DBRF1.getProductAttribute() == DBRF2.getProductAttribute()) {
                        if (DBRF1.getMin() == DBRF2.getMin()) {
                            if (DBRF1.getMax() == DBRF2.getMax()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
    */
    
    @Nested
    @DisplayName("Read rangeFilters")
    class readRangeFilters {
        DBRangeFilterReader dbRangeFilterReader = new DBRangeFilterReader();
        List<DBRangeFilter> dbFilters = PopulateDBFromCsv.readDBFiltersFromCSV("ValidDBRangeFilters.csv");

        void test(){
            DatabaseInterface db = MockDatabase.getInstance();
            DBRangeFilterCreator dbrangeFilterCreator = new DBRangeFilterCreator();
            for(DBRangeFilter filter: dbFilters){
//                dbrangeFilterCreator.createRangeFilter(filter);
                db.create(filter);
            }
        }


//        @Test
//        @DisplayName("Test getRangeFilter with valid id")
//        void testGetRangeFilterWithValidId() {
//            dbRangeFilterReader.map = new HashMap<>();
//            dbRangeFilterReader.map.put(test1.getId(),test1);
//            Assertions.assertSame(dbRangeFilterReader.getRangeFilter(0),test1);
//        }
//
//        @Test
//        @DisplayName("Test getRangeFilter with invalid id")
//        void testGetRangeFilterWithInvalidId() {
//            dbRangeFilterReader.map = new HashMap<>();
//            dbRangeFilterReader.map.put(test1.getId(),test1);
//            Assertions.assertEquals(null, dbRangeFilterReader.getRangeFilter(1));
//        }
//
//        @Test
//        @DisplayName("Test getRangeFilters")
//        void testGetRangeFilters() {
//            dbRangeFilterReader.map = new HashMap<>();
//            dbRangeFilterReader.map.put(test1.getId(), test1);
//            dbRangeFilterReader.map.put(test2.getId(), test2);
//            dbRangeFilterReader.map.put(test3.getId(), test3);
//            dbRangeFilterReader.map.put(test4.getId(), test4);
//
//            Assertions.assertAll("Testing that the objects in the array are the same as the ones in the hashmap",
//                    () -> Assertions.assertEquals(dbRangeFilterReader.map.get(0), dbRangeFilterReader.getRangeFilters()[0]),
//                    () -> Assertions.assertEquals(dbRangeFilterReader.map.get(1), dbRangeFilterReader.getRangeFilters()[1]),
//                    () -> Assertions.assertEquals(dbRangeFilterReader.map.get(2), dbRangeFilterReader.getRangeFilters()[2]),
//                    () -> Assertions.assertEquals(dbRangeFilterReader.map.get(3), dbRangeFilterReader.getRangeFilters()[3])
//            );
//        }
    }
}
