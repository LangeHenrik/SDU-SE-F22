package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.PopulateDBFromCsv;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.Database;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.DatabaseInterface;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class RangeFilterReaderTest {
    RangeFilterReader rangeFilterReader = new RangeFilterReader();
    static List<RangeFilter> filters = PopulateDBFromCsv.readFiltersFromCSV("ValidDBRangeFilters.csv");
    static DatabaseInterface db = new Database();
    int id1;
    int id2;
    int id3;

    @BeforeEach
    public void setup (){
        cleanUp();
        id1 = db.create(filters.get(0)).getId();
        id2 = db.create(filters.get(1)).getId();
        id3 = db.create(filters.get(2)).getId();
        System.out.println(db.readAllFilters().toString());
    }

    @AfterEach
    public void cleanUp (){
        db.delete(id1);
        db.delete(id2);
        db.delete(id3);
        System.out.println(filters.toString());
    }

    @Test
    @DisplayName("Test getRangeFilter with valid id")
    void testGetRangeFilterWithValidId() {
        try {
            Assertions.assertEquals(db.read(id1),rangeFilterReader.getRangeFilter(id1));
        } catch (InvalidFilterIdException e) {
            fail("Id didnt exist");
        }
    }

    @Test
    @DisplayName("Test getRangeFilter with invalid id")
    void testGetRangeFilterWithInvalidId() {
        Assertions.assertThrows(InvalidFilterIdException.class,
                () -> rangeFilterReader
                        .getRangeFilter(-1)
        );
    }

    @Test
    @DisplayName("Test getRangeFilters")
    void testGetRangeFilters() {
        Assertions.assertAll("Testing that the objects in the array are the same as the ones in the hashmap",
                () -> Assertions.assertEquals(db.readAllFilters().get(0), rangeFilterReader.getRangeFilters().get(0)),
                () -> Assertions.assertEquals(db.readAllFilters().get(1), rangeFilterReader.getRangeFilters().get(1)),
                () -> Assertions.assertEquals(db.readAllFilters().get(2), rangeFilterReader.getRangeFilters().get(2))
        );
    }
}
