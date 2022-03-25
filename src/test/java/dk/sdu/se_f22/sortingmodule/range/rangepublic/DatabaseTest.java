package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database database;

    @BeforeEach
    void setup() {
        database = new Database();
    }


    @Test
    void create() {

    }

    @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
    @DisplayName("Test read double filter from range filter database")
    @CsvFileSource(resources = "DatabaseFilters.csv", numLinesToSkip = 1)
    void testReadFromRangeFilterDatabase(int id, String name, String description, String productAttribute, double min, double max) {
        // todo recreate this test for each type of filter, so it is not only double
        // Shooting for 3 filters of each type should be fine
        // So each Csv file of filters should contain 3 files. Remember to add these in the sql file.
        try {
            RangeFilter dbTestFilter = database.read(id);
            Assertions.assertAll(
                    () -> assertEquals(id, dbTestFilter.getId()),
                    () -> assertEquals(name, dbTestFilter.getName()),
                    () -> assertEquals(description, dbTestFilter.getDescription()),
                    () -> assertEquals(productAttribute, dbTestFilter.getProductAttribute()),
                    () -> assertEquals(min, dbTestFilter.getDbMinDouble()),
                    () -> assertEquals(max, dbTestFilter.getDbMaxDouble())
            );
        } catch (UnknownFilterTypeException e) {
            e.printStackTrace();
            fail("The read threw an invalid filter type exception, which means, that the type returned was something weird.");
        }

        // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
    }


    @ParameterizedTest(name = "input: {0} ")
    @DisplayName("Test read of invalid ID from Database")
    @ValueSource(ints = {-1, -100, 1000, Integer.MIN_VALUE, Integer.MAX_VALUE})
    void testReadOfInvalidIdFromDatabase(int inputId) {
        // The invalid id exception is thrown by another method, so read, should simply return null

        try {
            assertNull(database.read(inputId));
        } catch (UnknownFilterTypeException e) {
            e.printStackTrace();
            fail("The read threw an invalid type exception, instead of returning null");
        }
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