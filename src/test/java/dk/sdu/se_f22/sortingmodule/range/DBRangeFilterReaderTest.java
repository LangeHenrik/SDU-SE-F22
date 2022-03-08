package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
        DatabaseInterface db = MockDatabase.getInstance();

        void insertData(){
            for(DBRangeFilter filter: dbFilters){
                db.create(filter);
            }
        }

        @Test
        @DisplayName("Test getRangeFilter with valid id")
        void testGetRangeFilterWithValidId() {
            insertData();
            try {
                Assertions.assertEquals(db.read(0),dbRangeFilterReader.getRangeFilter(0));
            } catch (InvalidFilterIdException e) {
                fail("Id didnt exist");
            }
        }


        @Test
        @DisplayName("Test getRangeFilter with invalid id")
        void testGetRangeFilterWithInvalidId() {
            Assertions.assertThrows(InvalidFilterIdException.class,
                    () -> dbRangeFilterReader
                            .getRangeFilter(dbRangeFilterReader.getRangeFilters().size()+1)
            );
        }

        @Test
        @DisplayName("Test getRangeFilters")
        void testGetRangeFilters() {
            Assertions.assertAll("Testing that the objects in the array are the same as the ones in the hashmap",
                    () -> Assertions.assertTrue(db.readAllFilters().get(0).equals(dbRangeFilterReader.getRangeFilters().get(0))),
                    () -> Assertions.assertTrue(db.readAllFilters().get(1).equals(dbRangeFilterReader.getRangeFilters().get(1))),
                    () -> Assertions.assertTrue(db.readAllFilters().get(2).equals(dbRangeFilterReader.getRangeFilters().get(2)))
            );
        }
    }
}
