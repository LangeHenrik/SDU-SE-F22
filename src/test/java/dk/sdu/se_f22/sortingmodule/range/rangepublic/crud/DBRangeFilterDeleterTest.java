package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.PopulateDBFromCsv;
import dk.sdu.se_f22.sortingmodule.range.database.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.database.MockDatabase;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.crud.RangeFilterDeleter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.crud.RangeFilterReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DBRangeFilterDeleterTest {

    RangeFilterDeleter dbRangeFilterDeleter = new RangeFilterDeleter();
    RangeFilterReader dbRangeFilterReader = new RangeFilterReader();
    List<DBRangeFilter> dbFilters = PopulateDBFromCsv.readDBFiltersFromCSV("ValidDBRangeFilters.csv");
    DatabaseInterface db = MockDatabase.getInstance();
    int plads1;
    int plads2;
    int plads3;

    @BeforeEach
    public void setup (){
        cleanUp();
        plads1 = db.create(dbFilters.get(0)).getId();
        plads2 = db.create(dbFilters.get(1)).getId();
        plads3 = db.create(dbFilters.get(2)).getId();
//        System.out.println(db.readAllFilters().toString());
    }

    @AfterEach
    public void cleanUp (){
        db.delete(plads1);
        db.delete(plads2);
        db.delete(plads3);
//        System.out.println(dbFilters.toString());
    }

    @Test
    @DisplayName("Delete valid filters")
    void deleteValidFilter() {
        Assertions.assertAll("Test deleteRangeFilter method using a valid id",
                () -> Assertions.assertEquals(dbRangeFilterDeleter.deleteRangeFilter(plads1), dbFilters.get(0)),
                () -> Assertions.assertEquals(dbRangeFilterDeleter.deleteRangeFilter(plads2), dbFilters.get(1)),
                () -> Assertions.assertEquals(dbRangeFilterDeleter.deleteRangeFilter(plads3), dbFilters.get(2))
        );

        assertAll(
                () -> Assertions.assertThrows(InvalidFilterIdException.class,
                        () -> dbRangeFilterReader.getRangeFilter(plads1)
                ),
                () -> Assertions.assertThrows(InvalidFilterIdException.class,
                        () -> dbRangeFilterReader.getRangeFilter(plads2)
                ),
                () -> Assertions.assertThrows(InvalidFilterIdException.class,
                        () -> dbRangeFilterReader.getRangeFilter(plads3)
                )
        );

//        Assertions.assertThrows(InvalidFilterIdException.class,
//                () -> dbRangeFilterReader.getRangeFilter(plads1)
//        );
    }

    @ParameterizedTest
    @DisplayName("Delete invalid filter throws exception")
    @ValueSource(ints = {Integer.MIN_VALUE, -10, -Integer.MAX_VALUE, Integer.MAX_VALUE, 0})
    void deleteInvalidFilter(int input) {
        if (input == 0){
            input = ++plads3;
        }

        final int testInput = input;

        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> dbRangeFilterDeleter.deleteRangeFilter(testInput)
        );
    }

    @Test
    @DisplayName("Delete filter twice throws exception")
    void deleteFilterTwice() {
        try {
            dbRangeFilterDeleter.deleteRangeFilter(plads1);
        } catch (InvalidFilterIdException e) {
            fail();
        }

        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> dbRangeFilterDeleter.deleteRangeFilter(plads1)
        );
    }
}
