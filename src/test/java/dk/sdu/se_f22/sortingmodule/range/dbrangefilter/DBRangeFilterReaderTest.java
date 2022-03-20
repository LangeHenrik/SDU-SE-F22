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
    DBRangeFilterReader dbRangeFilterReader = new DBRangeFilterReader();
    static List<DBRangeFilter> dbFilters = PopulateDBFromCsv.readDBFiltersFromCSV("ValidDBRangeFilters.csv");
    static DatabaseInterface db = MockDatabase.getInstance();
    int plads1;
    int plads2;
    int plads3;

    @BeforeEach
    public void setup (){
        cleanUp();
        plads1 = db.create(dbFilters.get(0)).getId();
        plads2 = db.create(dbFilters.get(1)).getId();
        plads3 = db.create(dbFilters.get(2)).getId();
        System.out.println(db.readAllFilters().toString());
    }

    @AfterEach
    public void cleanUp (){
        db.delete(plads1);
        db.delete(plads2);
        db.delete(plads3);
        System.out.printf(dbFilters.toString());
    }

    @Test
    @DisplayName("Test getRangeFilter with valid id")
    void testGetRangeFilterWithValidId() {
        try {
            Assertions.assertEquals(db.read(plads1),dbRangeFilterReader.getRangeFilter(plads1));
        } catch (InvalidFilterIdException e) {
            fail("Id didnt exist");
        }
    }

    @Test
    @DisplayName("Test getRangeFilter with invalid id")
    void testGetRangeFilterWithInvalidId() {
        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> dbRangeFilterReader
                        .getRangeFilter(-1)
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
