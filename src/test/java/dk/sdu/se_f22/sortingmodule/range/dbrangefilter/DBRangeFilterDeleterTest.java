package dk.sdu.se_f22.sortingmodule.range.dbrangefilter;

import dk.sdu.se_f22.sortingmodule.range.PopulateDBFromCsv;
import dk.sdu.se_f22.sortingmodule.range.database.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.database.MockDatabase;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.validators.Validator;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DBRangeFilterDeleterTest {

    DBRangeFilterDeleter dbRangeFilterDeleter = new DBRangeFilterDeleter();
    DBRangeFilterReader dbRangeFilterReader = new DBRangeFilterReader();
    static List<DBRangeFilter> dbFilters = PopulateDBFromCsv.readDBFiltersFromCSV("ValidDBRangeFilters.csv");
    static DatabaseInterface db = MockDatabase.getInstance();
    static int plads1;
    static int plads2;
    static int plads3;

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
    @DisplayName("Delete valid filter")
    void deleteValidFilter() {
        Assertions.assertAll("Test deleteRangeFilter method using a valid id",
                () -> Assertions.assertEquals(dbRangeFilterDeleter.deleteRangeFilter(plads1),dbFilters.get(0)),
                () -> Assertions.assertEquals(dbRangeFilterDeleter.deleteRangeFilter(plads2),dbFilters.get(1)),
                () -> Assertions.assertEquals(dbRangeFilterDeleter.deleteRangeFilter(plads3),dbFilters.get(2))
        );


        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> dbRangeFilterReader.getRangeFilter(plads1)
        );
    }

    @ParameterizedTest
    @DisplayName("Delete invalid filter")
    @ValueSource(ints = {Integer.MIN_VALUE, -10, -Integer.MAX_VALUE, Integer.MAX_VALUE})
    void deleteInvalidFilter(int input) {
        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> dbRangeFilterDeleter.deleteRangeFilter(input)
        );
    }

    @Test
    @DisplayName("Delete filter twice")
    void deleteFilterTwice() {
        try {
            dbRangeFilterDeleter.deleteRangeFilter(plads1);
        } catch (InvalidFilterIdException e) {
            e.printStackTrace();
        }

        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> dbRangeFilterDeleter.deleteRangeFilter(plads1)
        );
    }
}
