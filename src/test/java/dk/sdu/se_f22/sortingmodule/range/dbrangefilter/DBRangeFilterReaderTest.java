package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.PopulateDBFromCsv;
import dk.sdu.se_f22.sortingmodule.range.database.Database;
import dk.sdu.se_f22.sortingmodule.range.database.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.database.MockDatabase;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilterReader;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import org.junit.jupiter.api.*;

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
        static List<DBRangeFilter> dbFilters = PopulateDBFromCsv.readDBFiltersFromCSV("ValidDBRangeFilters.csv");
        static DatabaseInterface db = MockDatabase.getInstance();

        @BeforeAll
        public static void setup (){
            for(DBRangeFilter filter: dbFilters){
                db.create(filter);
            }
        }

        @Test
        @DisplayName("Test getRangeFilter with valid id")
        void testGetRangeFilterWithValidId() {
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
                    () -> Assertions.assertEquals(db.readAllFilters().get(0), dbRangeFilterReader.getRangeFilters().get(0)),
                    () -> Assertions.assertEquals(db.readAllFilters().get(1), dbRangeFilterReader.getRangeFilters().get(1)),
                    () -> Assertions.assertEquals(db.readAllFilters().get(2), dbRangeFilterReader.getRangeFilters().get(2))
            );
        }
    }
}
