package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.PopulateDBFromCsv;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.Database;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RangeFilterDeleterTest {

    RangeFilterDeleter rangeFilterDeleter = new RangeFilterDeleter();
    RangeFilterReader rangeFilterReader = new RangeFilterReader();
    List<RangeFilter> filters = PopulateDBFromCsv.readFiltersFromCSV("ValidRangeFilters.csv");
    DatabaseInterface db = new Database();
    int id1;
    int id2;
    int id3;

    @BeforeEach
    public void setup (){
        cleanUp();
        id1 = db.create(filters.get(0)).getId();
        id2 = db.create(filters.get(1)).getId();
        id3 = db.create(filters.get(2)).getId();
//        System.out.println(.readAllFilters().toString());
    }

    @AfterEach
    public void cleanUp (){
        db.delete(id1);
        db.delete(id2);
        db.delete(id3);
//        System.out.println(Filters.toString());
    }

    @Test
    @DisplayName("Delete valid filters")
    void deleteValidFilter() {
        Assertions.assertAll("Test deleteRangeFilter method using a valid id",
                () -> Assertions.assertEquals(rangeFilterDeleter.deleteRangeFilter(id1), filters.get(0)),
                () -> Assertions.assertEquals(rangeFilterDeleter.deleteRangeFilter(id2), filters.get(1)),
                () -> Assertions.assertEquals(rangeFilterDeleter.deleteRangeFilter(id3), filters.get(2))
        );

        assertAll(
                () -> Assertions.assertThrows(InvalidFilterIdException.class,
                        () -> rangeFilterReader.getRangeFilter(id1)
                ),
                () -> Assertions.assertThrows(InvalidFilterIdException.class,
                        () -> rangeFilterReader.getRangeFilter(id2)
                ),
                () -> Assertions.assertThrows(InvalidFilterIdException.class,
                        () -> rangeFilterReader.getRangeFilter(id3)
                )
        );

//        Assertions.assertThrows(InvalidFilterIdException.class,
//                () -> RangeFilterReader.getRangeFilter(id1)
//        );
    }

    @ParameterizedTest
    @DisplayName("Delete invalid filter throws exception")
    @ValueSource(ints = {Integer.MIN_VALUE, -10, -Integer.MAX_VALUE, Integer.MAX_VALUE, 0})
    void deleteInvalidFilter(int input) {
        if (input == 0){
            input = ++id3;
        }

        final int testInput = input;

        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> rangeFilterDeleter.deleteRangeFilter(testInput)
        );
    }

    @Test
    @DisplayName("Delete filter twice throws exception")
    void deleteFilterTwice() {
        try {
            rangeFilterDeleter.deleteRangeFilter(id1);
        } catch (InvalidFilterIdException | UnknownFilterTypeException e) {
            fail();
        }

        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> rangeFilterDeleter.deleteRangeFilter(id1)
        );
    }
}
