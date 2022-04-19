package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.db.DBMigration;
import dk.sdu.se_f22.sharedlibrary.db.SeedDatabase;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database database;
    private DBMigration dbMigration = new DBMigration();

    @BeforeEach
    void setup() {
        database = new Database();
    }


    @Nested
    class create {
        @BeforeEach
        void setup() {
            try {
                dbMigration.runSQLFromFile(DBConnection.getPooledConnection(), "src/main/java/dk/sdu/se_f22/sharedlibrary/db/modifiedRangeFilters.sql");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test creating a double filter")
        @CsvFileSource(resources = "DoubleFilterToCreate.csv", numLinesToSkip = 1)
        void testCreateDoubleFilter(String name, String description, String productAttribute, double min, double max){
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
                    System.out.println("Created filter id is " + createdFilter.getId());
                    RangeFilter readFilter = database.read(createdFilter.getId());

                    Assertions.assertEquals(createdFilter, readFilter);
                } catch (InvalidFilterTypeException | SQLException e) {
                    fail(e);
                }

            } catch (UnknownFilterTypeException e) {
                fail(e + ": an exception from 'read' was thrown");
            }
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test creating a time filter")
        @CsvFileSource(resources = "TimeFilterToCreate.csv", numLinesToSkip = 1)
        void testCreateTimeFilter(String name, String description, String productAttribute, String min, String max){
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
                    System.out.println("Created filter id is " + createdFilter.getId());

                    RangeFilter readFilter = database.read(createdFilter.getId());

                    Assertions.assertEquals(createdFilter, readFilter);
                } catch (InvalidFilterTypeException | SQLException e) {
                    fail(e);
                }

            } catch (UnknownFilterTypeException e) {
                fail(e + ": an exception from 'read' was thrown");
            }
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test creating a long filter")
        @CsvFileSource(resources = "LongFilterToCreate.csv", numLinesToSkip = 1)
        void testCreateLongFilter(String name, String description, String productAttribute, long min, long max){
            RangeFilter createdFilter = null;
            try {
                try {
                    RangeFilter longFilter = new LongFilter(
                            name,
                            description,
                            productAttribute,
                            min,
                            max);
                    createdFilter = database.create(longFilter);
                    System.out.println("Created filter has ID "+ createdFilter.getId());

                    RangeFilter readFilter = database.read(createdFilter.getId());
                    Assertions.assertEquals(createdFilter, readFilter);
                } catch (InvalidFilterTypeException | SQLException e) {
                    fail("Created filter " + e);
                }

            } catch (UnknownFilterTypeException e) {
                fail(e + ": an exception from 'read' was thrown");
            }
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test creating two filters with the same name")
        @CsvFileSource(resources = "DoubleFilterToCreate.csv", numLinesToSkip = 1)
        void testCreatingTwoFiltersWithTheSameName(String name, String description, String productAttribute, double min, double max){
            try {
                RangeFilter createdFilter =
                        new DoubleFilter(
                                name,
                                description,
                                productAttribute,
                                min,
                                max);
                database.create(createdFilter);
                try {
                    database.create(createdFilter);
                    fail("Should throw duplicate key exception");
                } catch (PSQLException e){
                    Assertions.assertTrue(e.getMessage().contains("ERROR: duplicate key value violates unique constraint \"sortingrangefilters_name_key\""));
                }
            } catch (InvalidFilterTypeException | SQLException e) {
                fail(e);
            }
        }
    }

    @Nested
    @DisplayName("read")
    class read {
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
        List<RangeFilter> result = database.readAllFilters();
        Assertions.assertAll("Read all filters from database and check if the values match",
                () -> Assertions.assertEquals(new DoubleFilter(1, "test name double", "test description", "price", 0, 10), result.get(0)),
                () -> Assertions.assertEquals(new LongFilter(2, "test name ean", "test description for long filter", "ean", 2, 100), result.get(1)),
                () -> Assertions.assertEquals(new TimeFilter(3, "test name time", "test description for time filter", "expirationDate", Instant.parse("2018-11-30T15:35:24Z"), Instant.parse("2022-11-30T15:35:24Z")), result.get(2))
        );
    }


}