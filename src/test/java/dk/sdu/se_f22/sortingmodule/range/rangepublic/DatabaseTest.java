package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database database;

    @BeforeEach
    void setup() {
        database = new Database();
    }


    @Nested
    class create {
        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test creating a double filter")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void testCreateDoubleFilter(int id, String name, String description, String productAttribute, double min, double max){
            try {
                try {
                    RangeFilter createdFilter = database.create(
                        new DoubleFilter(
                            name,
                            description,
                            productAttribute,
                            min,
                            max)
                    );
                    RangeFilter readFilter = database.read(createdFilter.getId());
                    Assertions.assertEquals(createdFilter, readFilter);
                } catch (InvalidFilterTypeException e) {
                    fail(e);
                }

            } catch (UnknownFilterTypeException e) {
                fail(e + ": an exception from 'read' was thrown");
            }
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test creating a time filter")
        @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
        void testCreateTimeFilter(int id, String name, String description, String productAttribute, String min, String max){
            try {
                try {
                    RangeFilter createdFilter = database.create(
                            new TimeFilter(
                                    name,
                                    description,
                                    productAttribute,
                                    Instant.parse(min),
                                    Instant.parse(max))
                    );
                    RangeFilter readFilter = database.read(createdFilter.getId());
                    Assertions.assertEquals(createdFilter, readFilter);
                } catch (InvalidFilterTypeException e) {
                     fail(e);
                }

            } catch (UnknownFilterTypeException e) {
                fail(e + ": an exception from 'read' was thrown");
            }
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test creating a long filter")
        @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
        void testCreateLongFilter(int id, String name, String description, String productAttribute, long min, long max){
            try {
                try {
                    RangeFilter createdFilter = database.create(
                        new LongFilter(
                            name,
                            description,
                            productAttribute,
                            min,
                            max)
                    );
                    RangeFilter readFilter = database.read(createdFilter.getId());
                    Assertions.assertEquals(createdFilter, readFilter);
                } catch (InvalidFilterTypeException e) {
                    fail(e);
                }

            } catch (UnknownFilterTypeException e) {
                fail(e + ": an exception from 'read' was thrown");
            }
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test creating two filters with the same name")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void testCreatingTwoFiltersWithTheSameName(int id, String name, String description, String productAttribute, double min, double max){
            try {
                RangeFilter createdFilter =
                        new DoubleFilter(
                                name,
                                description,
                                productAttribute,
                                min,
                                max);
                database.create(createdFilter);
                Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,
                        () -> database.create(createdFilter)
                );
            } catch (InvalidFilterTypeException e) {
                fail(e);
            }
        }
    }

    @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
    @DisplayName("Test read double filter from range filter database")
    @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
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