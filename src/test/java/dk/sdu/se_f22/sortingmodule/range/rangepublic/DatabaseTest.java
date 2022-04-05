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

    @Nested
    @DisplayName("Read valid filters from database")
    class readValidFiltersFromDatabase {

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test read double filter from range filter database")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void testReadFromRangeFilterDatabase(int id, String name, String description, String productAttribute, double min, double max) {
            // Shooting for 3 filters of each type should be fine
            // So each Csv file of filters should contain 3 files. Remember to add these in the sql file.
            try {
                RangeFilter actual = database.read(id);
                RangeFilter expected = new DoubleFilter(id, name, description, productAttribute, min, max);
                assertEquals(expected, actual);
            } catch (UnknownFilterTypeException e) {
                e.printStackTrace();
                fail("The read threw an invalid filter type exception, which means, that the type returned was something weird.");
            }

            // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test read Long filter from range filter database")
        @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
        void testReadLongFromRangeFilterDatabase(int id, String name, String description, String productAttribute, long min, long max) {
            // Shooting for 3 filters of each type should be fine
            // So each Csv file of filters should contain 3 files. Remember to add these in the sql file.
            try {
                RangeFilter actual = database.read(id);
                RangeFilter expected = new LongFilter(id, name, description, productAttribute, min, max);
                assertEquals(expected, actual);
            } catch (UnknownFilterTypeException e) {
                e.printStackTrace();
                fail("The read threw an invalid filter type exception, which means, that the type returned was something weird.");
            }

            // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test read time filter from time filter database")
        @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
        void testReadTimeFromRangeFilterDatabase(int id, String name, String description, String productAttribute, Instant min, Instant max) {
            // Shooting for 3 filters of each type should be fine
            // So each Csv file of filters should contain 3 files. Remember to add these in the sql file.
            try {
                RangeFilter actual = database.read(id);
                RangeFilter expected = new TimeFilter(id, name, description, productAttribute, min, max);
                assertEquals(expected, actual);
            } catch (UnknownFilterTypeException e) {
                e.printStackTrace();
                fail("The read threw an invalid filter type exception, which means, that the type returned was something weird.");
            }

            // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
        }
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
    @DisplayName("Database Read all existing range filters")
    void readAllFilters() {
        Assertions.assertAll("Read all filters from database and check if the values match",
                () -> Assertions.assertEquals(database.readAllFilters().get(0), new DoubleFilter(1, "test name double", "test description", "price", 0, 10)),
                () -> Assertions.assertEquals(database.readAllFilters().get(1), new LongFilter(2, "test name ean", "test description", "ean", 2, 100)),
                () -> Assertions.assertEquals(database.readAllFilters().get(2), new TimeFilter(3, "test name time", "test description", "expirationDate", Instant.parse("2018-10-18T00:00:57Z"), Instant.parse("2019-10-18T00:00:57Z")))
        );
    }
}