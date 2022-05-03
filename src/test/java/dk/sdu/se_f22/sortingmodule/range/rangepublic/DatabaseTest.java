package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.db.DBMigration;
import dk.sdu.se_f22.sharedlibrary.db.SeedDatabase;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.postgresql.util.PSQLException;
import org.w3c.dom.ranges.Range;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database database;
    private static final DBMigration dbMigration = new DBMigration();

    private static void resetDB(){
        try {
           dbMigration.runSQLFromFile(DBConnection.getPooledConnection(), "src/main/java/dk/sdu/se_f22/sharedlibrary/db/modifiedRangeFilters.sql");
        } catch (SQLException e) {
            System.out.println("error when resetting database state, pooled connection threw sql exception:");
            e.printStackTrace();
        }
    }


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
            RangeFilter createdFilter;
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
        @BeforeAll
        public static void setup(){
            resetDB();
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

    @Nested
    @DisplayName("Update")
    class update {
        @BeforeEach
        void setup() {
            try {
                dbMigration.runSQLFromFile(DBConnection.getPooledConnection(), "src/main/java/dk/sdu/se_f22/sharedlibrary/db/modifiedRangeFilters.sql");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Nested
        @DisplayName("Update existing filter")
        class UpdateExistingFilter{
            @ParameterizedTest
            @DisplayName("Update existing double filter")
            @CsvFileSource(resources = "DoubleFilterToUpdate.csv", numLinesToSkip = 1)
            void updateExistingDoubleFilter(int id, String name, String description, String productAttribute, double min, double max,
                                            double uMin, double uMax) {
                DoubleFilter doubleFilter = null;
                try {
                    doubleFilter = (DoubleFilter) database.create(new DoubleFilter(name, description, productAttribute, min, max));
                } catch (InvalidFilterTypeException e) {
                    e.printStackTrace();
                    fail("Filter type was invalid, see 'create' under 'RangeFilterCRUD'");
                } catch (SQLException e) {
                    e.printStackTrace();
                    fail("An error occurred in the database during creation of the filter used in the test");
                }

                if (doubleFilter == null) {
                    fail("Filter was 'null'");
                }

                try {
                    DoubleFilter updatedFilter = (DoubleFilter) database.update(new DoubleFilter(
                            doubleFilter.getId(),
                            doubleFilter.getName(),
                            doubleFilter.getDescription(),
                            doubleFilter.getProductAttribute(),
                            uMin,
                            uMax
                    ));
                    Assertions.assertEquals(updatedFilter, database.read(doubleFilter.getId()));
                } catch (UnknownFilterTypeException | SQLException | InvalidFilterTypeException e) {
                    e.printStackTrace();
                    fail("An exception from 'read' was thrown, see 'read' under 'RangeFilterCURD' or check if the filter " +
                            "exists in the database.");
                }
            }

            @ParameterizedTest
            @DisplayName("Update existing long filter")
            @CsvFileSource(resources = "LongFilterToUpdate.csv", numLinesToSkip = 1)
            void updateExistingLongFilter(int id, String name, String description, String productAttribute, long min, long max,
                                          long uMin, long uMax) {
                LongFilter longFilter = null;
                try {
                    longFilter = (LongFilter) database.create(new LongFilter(name, description, productAttribute, min, max));
                } catch (InvalidFilterTypeException e) {
                    e.printStackTrace();
                    fail("Filter type was invalid, see 'create' under 'RangeFilterCRUD'");
                } catch (SQLException e) {
                    e.printStackTrace();
                    fail("An error occurred in the database during creation of the filter used in the test");
                }

                if (longFilter == null) {
                    fail("Filter was 'null'");
                }

                try {
                    LongFilter updatedFilter = (LongFilter) database.update(
                                new LongFilter(
                                        longFilter.getId(),
                                        longFilter.getName(),
                                        longFilter.getDescription(),
                                        longFilter.getProductAttribute(),
                                        uMin,
                                        uMax
                                )
                            );
                    Assertions.assertEquals(updatedFilter, database.read(longFilter.getId()));
                } catch (UnknownFilterTypeException | SQLException | InvalidFilterTypeException e) {
                    e.printStackTrace();
                    fail("An exception from 'read' was thrown, see 'read' under 'RangeFilterCURD' or check if the filter " +
                        "exists in the database.");
                }
            }

            @ParameterizedTest
            @DisplayName("Update existing time filter")
            @CsvFileSource(resources = "TimeFilterToUpdate.csv", numLinesToSkip = 1)
            void updateExistingTimeFilter(int id, String name, String description, String productAttribute, Instant min, Instant max,
                                          Instant uMin, Instant uMax) {
                TimeFilter filter = null;
                TimeFilter updatedFilter = null;
                TimeFilter readFilter = null;
                try {
                    filter = (TimeFilter) database.create(new TimeFilter(name, description, productAttribute, min, max));
                } catch (SQLException | InvalidFilterTypeException e) {
                    fail("Creating filter failed " + e);
                }

                if (filter == null) {
                   fail("Created filter is null");
                }

                try{
                    updatedFilter = (TimeFilter) database.update(
                            new TimeFilter(
                                    filter.getId(),
                                    filter.getName(),
                                    filter.getDescription(),
                                    filter.getProductAttribute(),
                                    uMin,
                                    uMax
                                    )
                            );
                 } catch (Exception e ){
                    fail("Filter update failed " + e);
                }

                try {
                    readFilter = (TimeFilter) database.read(filter.getId());
                } catch (UnknownFilterTypeException e) {
                    fail("Read filter failed");
                }

                // TODO add test for update return correctly
                // Since this test, is responsible for testing whether the data returned match the data actually saved
                // There should be a test to ensure the update method returns the updated info
                // (this test will pass even if no update is performed in the db,
                // as long as the update method returns the same filter it received)
                Assertions.assertEquals(updatedFilter, readFilter);
            }
        }

        @Nested
        @DisplayName("Update nonexisting filter")
        class UpdateNonexistingFilter {
            @ParameterizedTest
            @DisplayName("Update nonexisting double filter")
            @CsvFileSource(resources = "DoubleFilterToUpdate.csv", numLinesToSkip = 1)
            void updateNonexistingDoubleFilter(int id, String name, String description, String productAttribute,
                                               double min, double max, double uMin, double uMax) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);

                Assertions.assertThrows(SQLException.class, () -> database.update(doubleFilter));
            }

            @ParameterizedTest
            @DisplayName("Update nonexisting long filter")
            @CsvFileSource(resources = "LongFilterToUpdate.csv", numLinesToSkip = 1)
            void updateNonexistingLongFilter(int id, String name, String description, String productAttribute,
                                             long min, long max, long uMin, long uMax) {

                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);

                Assertions.assertThrows(SQLException.class, () -> database.update(longFilter));
            }

            @ParameterizedTest
            @DisplayName("Update nonexisting time filter")
            @CsvFileSource(resources = "TimeFilterToUpdate.csv", numLinesToSkip = 1)
            void updateNonexistingTimeFilter(int id, String name, String description, String productAttribute,
                                             Instant min, Instant max, Instant uMin, Instant uMax) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);

                Assertions.assertThrows(SQLException.class, () -> database.update(timeFilter));
            }


            @Nested
            @DisplayName("Filter with default ID")
            class FilterWithoutID {
                @ParameterizedTest
                @DisplayName("Existing double filter default ID")
                @CsvFileSource(resources = "DoubleFilterToUpdate.csv", numLinesToSkip = 1)
                void testDoubleFilterWithoutId(int id, String name, String description, String productAttribute,
                                               double min, double max, double uMin, double uMax) {
                    DoubleFilter doubleFilter = new DoubleFilter(name, description, productAttribute, min, max);

                    Assertions.assertThrows(SQLException.class, () -> database.update(doubleFilter));
                }

                @ParameterizedTest
                @DisplayName("Existing long filter default ID")
                @CsvFileSource(resources = "LongFilterToUpdate.csv", numLinesToSkip = 1)
                void testLongFilterWithoutId(int id, String name, String description, String productAttribute,
                                             long min, long max, long uMin, long uMax) {
                    LongFilter longFilter = new LongFilter(name, description, productAttribute, min, max);

                    Assertions.assertThrows(SQLException.class, () -> database.update(longFilter));
                }

                @ParameterizedTest
                @DisplayName("Existing time filter default ID")
                @CsvFileSource(resources = "TimeFilterToUpdate.csv", numLinesToSkip = 1)
                void testTimeFilterWithoutId(int id, String name, String description, String productAttribute,
                                             Instant min, Instant max, Instant uMin, Instant Umax) {
                    TimeFilter timeFilter = new TimeFilter(name, description, productAttribute, min, max);

                    Assertions.assertThrows(SQLException.class, () -> database.update(timeFilter));
                }
            }
        }
    }
    

    @Nested
    class Delete {
        @BeforeAll
        public static void setup(){
            resetDB();
        }

        @Nested
        class DeleteValidFilters {

            @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
            @DisplayName(" Test Delete Existing Double Filter")
            @CsvFileSource(resources = "DoubleFilterToCreate.csv", numLinesToSkip = 1)
            void testDeleteExistingDoubleFilter(String name, String description, String productAttribute, double min, double max) throws UnknownFilterTypeException, IdNotFoundException, SQLException, InvalidFilterTypeException {
                RangeFilter doubleFilterFromDB = database.create(new DoubleFilter(name, description, productAttribute, min, max));
                Assertions.assertEquals(database.delete(doubleFilterFromDB.getId()), doubleFilterFromDB);
            }

            @Nested
            class ReadDeletedDoubleFilter {
                @ParameterizedTest(name = "{0} : {1}")
                @DisplayName("test read of deleted filter")
                @CsvFileSource(resources = "DoubleFilterToCreate.csv", numLinesToSkip = 1)
                void testReadOfDeletedFilter(String name , String description, String productAttribute, double min, double max) throws UnknownFilterTypeException, SQLException, InvalidFilterTypeException, IdNotFoundException {
                    RangeFilter doubleFilterFromDB = database.create(new DoubleFilter(name, description, productAttribute, min, max));
                    database.delete(doubleFilterFromDB.getId());
                    Assertions.assertNull(database.read(doubleFilterFromDB.getId()));
                }
            }
            @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
            @DisplayName(" test delete Existing Timefilter")//  læs data fra databasen, slet, læs og c
            @CsvFileSource(resources = "TimeFilterToCreate.csv", numLinesToSkip = 1)
            void testDeleteExistingTimeFilter(String name, String description, String productAttribute, Instant
                    min, Instant max) throws IdNotFoundException, UnknownFilterTypeException, SQLException, InvalidFilterTypeException {

                RangeFilter timeFilterFromDB = database.create(new TimeFilter(name, description, productAttribute, min, max));
                Assertions.assertEquals(database.delete(timeFilterFromDB.getId()), timeFilterFromDB);
                Assertions.assertNull(database.read(timeFilterFromDB.getId()));
            }

            @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
            @DisplayName("test delete Existing Longfilter")//  læs data fra databasen, slet, læs og c
            @CsvFileSource(resources = "LongFilterToCreate.csv", numLinesToSkip = 1)
            void testDeleteExistingLongFilter(String name, String description, String productAttribute, Long
                    min, Long max) throws IdNotFoundException, SQLException, InvalidFilterTypeException, UnknownFilterTypeException {

                RangeFilter longFilterFromDB = database.create(new LongFilter(name, description, productAttribute, min, max));
                Assertions.assertEquals(longFilterFromDB, database.delete(longFilterFromDB.getId()));
            }
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("test delete of invalid filterId")
        @ValueSource(ints = {-1, -100, 1000, Integer.MIN_VALUE, Integer.MAX_VALUE})
        void testdeleteOfInvalidFilterId ( int inputId) throws UnknownFilterTypeException, IdNotFoundException {
            //opret filter med invalid id, forsøg at slette og få exception som expected
            Assertions.assertThrows(IdNotFoundException.class, () -> database.delete(inputId));
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test delete double filter twice")
        @CsvFileSource(resources = "DoubleFilterToCreate.csv", numLinesToSkip = 1)
        void testDeleteDoubleFilterTwice(String name, String description, String productAttribute, double
                min, double max) throws SQLException, InvalidFilterTypeException {
            RangeFilter doubleFilterFromDB = database.create(new DoubleFilter(name, description, productAttribute, min, max));
            //skal ikke kaste exception da filteret findes
            assertDoesNotThrow( () -> database.delete(doubleFilterFromDB.getId()));
            // skal kaste da filteret ikke findes længere
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> database.delete(doubleFilterFromDB.getId()));
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test delete time filter twice")
        @CsvFileSource(resources = "TimeFilterToCreate.csv", numLinesToSkip = 1)
        void testDeleteTimeFilterTwice(String name, String description, String productAttribute, Instant
                min, Instant max) throws SQLException, InvalidFilterTypeException {

            RangeFilter TimeFilterFromDB = database.create(new TimeFilter(name, description, productAttribute, min, max));

            //skal ikke kaste exception da filteret findes
            assertDoesNotThrow( () -> database.delete(TimeFilterFromDB.getId()));

            // skal kaste da filteret ikke findes længere
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> database.delete(TimeFilterFromDB.getId()));
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Test delete Long filter twice")
        @CsvFileSource(resources = "LongFilterToCreate.csv", numLinesToSkip = 1)
        void testDeleteLongFilterTwice(String name, String description, String productAttribute, Long
                min, Long max) throws SQLException, InvalidFilterTypeException {

            RangeFilter LongFilterFromDB = database.create(new DoubleFilter(name, description, productAttribute, min, max));

            //skal ikke kaste exception da filteret findes
            assertDoesNotThrow( () -> database.delete(LongFilterFromDB.getId()));

            // skal kaste da filteret ikke findes længere
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> database.delete(LongFilterFromDB.getId()));
        }
    }
}