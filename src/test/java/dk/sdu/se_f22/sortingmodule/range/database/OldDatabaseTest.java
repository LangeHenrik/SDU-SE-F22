package dk.sdu.se_f22.sortingmodule.range.database;

import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class OldDatabaseTest {

    private OldDatabase oldDatabase;

    @BeforeEach
    void setup() {
        oldDatabase = new OldDatabase();
    }


    @Test
    void create() {

    }

    @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
    @DisplayName("Test read from range filter database")
    @CsvFileSource(resources = "DatabaseFilters.csv", numLinesToSkip = 1)
    void testReadFromRangeFilterDatabase(int id, String name, String description, String productAttribute, double min, double max) {
//        int filterId = Integer.parseInt(id);
//        double filterMin = Double.parseDouble(min);
//        double filterMax = Double.parseDouble(max);
        // All lines above are commented because it turns out that CSV sources can automatically parse input data
        DBRangeFilter dbTestFilter = oldDatabase.read(id);
        Assertions.assertAll(
                () -> assertEquals(id, dbTestFilter.getId()),
                () -> assertEquals(name, dbTestFilter.getName()),
                () -> assertEquals(description, dbTestFilter.getDescription()),
                () -> assertEquals(productAttribute, dbTestFilter.getProductAttribute()),
                () -> assertEquals(min, dbTestFilter.getMin()),
                () -> assertEquals(max, dbTestFilter.getMax())
        );

        // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
    }


    @ParameterizedTest(name = "input: {0} ")
    @DisplayName("Test read of invalid ID from Database")
    @ValueSource(ints = {-1, 1000, Integer.MIN_VALUE, Integer.MAX_VALUE})
    void testReadOfInvalidIdFromDatabase(int inputId) {
//        assertThrows(InvalidFilterIdException.class,
//                () -> database.read(inputId)
//        );
        // Uncommented because it may not be the desired functionality
        // The mock database simply returns null, so that is the functionality described in the interface
        // The exception is then subsequently thrown by another method

        assertNull(oldDatabase.read(inputId));
    }

    @Test
    void update() {

    }

    @Test
    void delete() {

    }

    @Test
    void readAllFilters() {

    }
}