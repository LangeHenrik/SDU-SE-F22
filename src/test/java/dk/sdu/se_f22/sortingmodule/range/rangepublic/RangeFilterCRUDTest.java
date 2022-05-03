package dk.sdu.se_f22.sortingmodule.range.rangepublic;
// To run the tests in parallel that are marked to run concurrently add the following parameter to the run options of the test runner
// -Djunit.jupiter.execution.parallel.enabled=true
// (in intellij, this is added after the -ea when editing configurations for the runner)
// In theory this provides a speedup. In practice the majority of our tests are not capable of being ran concurrently
// especially our database tests, which take up the majority of the test time

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.db.DBMigration;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class RangeFilterCRUDTest {

    private RangeFilterCRUD rangeFilterCRUD;

    @BeforeEach
    public void setup() {
        rangeFilterCRUD = new RangeFilterCRUD();
    }

    @Nested
    @DisplayName("CRUD Creator")
    class CRUDCreatorTest {
        @Nested
        @DisplayName("Create Db filters")
        class createDbFilters {
            @Nested
            @DisplayName("Valid filters that should pass")
            class validFiltersThatShouldPass {
                @Test
                @DisplayName("ValidFilters with regular attributes")
                    // A csv file for testing valid attributes is in progress
                void validFiltersWithRegularAttributesDouble() {
                    String description = "This filter checks a lot of attributes bla bla";
                    String name = "Sample filter";
                    String productAttribute = "price";
                    double min = 0;
                    double max = 800;

                    Assertions.assertDoesNotThrow(
                            () -> rangeFilterCRUD.create(description, name, productAttribute, min, max)
                    );
                }
            }

            @Test
            @DisplayName("ValidFilters with regular attributes")
            void validFiltersWithRegularAttributesLong() {
                String description = "This filter checks a lot of attributes bla bla";
                String name = "Sample filter";
                String productAttribute = "price";
                long min = 0;
                long max = 800;

                Assertions.assertDoesNotThrow(
                        () -> rangeFilterCRUD.create(name, description, productAttribute, min, max)
                );
            }

            @Test
            @DisplayName("ValidFilters with regular attributes")
            void validFiltersWithRegularAttributesTime() {
                String description = "This filter checks a lot of attributes bla bla";
                String name = "Sample filter";
                String productAttribute = "price";
                Instant min = Instant.ofEpochSecond(0);
                Instant max = Instant.ofEpochSecond(800);

                Assertions.assertDoesNotThrow(
                        () -> rangeFilterCRUD.create(description, name, productAttribute, min, max)
                );
            }

            @Nested
            @DisplayName("Invalid filters that should not be created")
            class invalidFiltersThatShouldNotBeCreated {

                @ParameterizedTest(name = "filter description {0}")
                @DisplayName("Empty description")
                @ValueSource(strings = {"", " "})
                void emptyDescription(String input) {
                    double minDouble = 0;
                    double maxDouble = 800;

                    long minLong = 0;
                    long maxLong = 800;

                    Instant minInstant = Instant.ofEpochSecond(0);
                    Instant maxInstant = Instant.ofEpochSecond(800);

                    Assertions.assertAll("Check that the 3 different filter types throw with empty description.",
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create(input, "sample name", "Sample attribute", minDouble, maxDouble)
                            ),
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create(input, "sample name", "Sample attribute", minLong, maxLong)
                            ),
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create(input, "sample name", "Sample attribute", minInstant, maxInstant)
                            )
                    );
                }


                @ParameterizedTest(name = "filter attribute {0}")
                @DisplayName("Empty product attribute")
                @ValueSource(strings = {"", " "})
                void emptyProductAttribute(String input) {
                    double minDouble = 0;
                    double maxDouble = 800;

                    long minLong = 0;
                    long maxLong = 800;

                    Instant minInstant = Instant.ofEpochSecond(0);
                    Instant maxInstant = Instant.ofEpochSecond(800);

                    Assertions.assertAll("Check that the 3 different filter types throw with empty description.",
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("sample name", "Testing empty product attribute", input, minDouble, maxDouble)
                            ),
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("sample name", "Testing empty product attribute", input, minLong, maxLong)
                            ),
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("sample name", "Testing empty product attribute", input, minInstant, maxInstant)
                            )
                    );
                }


                @ParameterizedTest(name = "filter name {0}")
                @DisplayName("Empty filter name")
                @ValueSource(strings = {"", " "})
                void emptyFilterNameV2(String input) {
                    double minDouble = 0;
                    double maxDouble = 800;

                    long minLong = 0;
                    long maxLong = 800;

                    Instant minInstant = Instant.ofEpochSecond(0);
                    Instant maxInstant = Instant.ofEpochSecond(800);

                    Assertions.assertAll("Check that the 3 different filter types throw with empty description.",
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("Testing empty product attribute", input, "attribute", minDouble, maxDouble)
                            ),
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("Testing empty product attribute", input, "attribute", minLong, maxLong)
                            ),
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("Testing empty product attribute", input, "attribute", minInstant, maxInstant)
                            )
                    );
                }

                @Nested
                @DisplayName("Testing negative min")
                class negativeMin {
                    @ParameterizedTest(name = "filter min negative {0}")
                    @DisplayName("Negative min double")
                    @ValueSource(doubles = {-1.0, -3.67})
                    void negativeMinDouble(double doubles) {
                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative min", "name", "price", doubles, 500)
                        );
                    }

                    @ParameterizedTest(name = "filter min negative {0}")
                    @DisplayName("Negative min long")
                    @ValueSource(longs = {-1, -3})
                    void negativeMinLong(long longs) {
                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative min", "name", "price", longs, 500)
                        );
                    }

                    // There is no test for negative min on time filters since, the concept of a negative timestamp is hard to grasp, and does not really make sense
                }

                @Nested
                @DisplayName("Testing negative max")
                class negativeMax {
                    @ParameterizedTest(name = "filter max negative {0}")
                    @DisplayName("Negative max double")
                    @ValueSource(doubles = {-1.0, -3.67})
                    void negativeMaxDouble(double doubles) {
                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative Max", "name", "price", 0, doubles)
                        );
                    }

                    @ParameterizedTest(name = "filter max negative {0}")
                    @DisplayName("Negative max long")
                    @ValueSource(longs = {-1, -3})
                    void negativeMaxLong(long longs) {
                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative Max", "name", "price", 0, longs)
                        );
                    }
                }

                @Nested
                @DisplayName("Testing max less than min")
                class MaxLessThanMin {
                    @ParameterizedTest(name = "filter max less than min {0}")
                    @DisplayName("Max less than min double")
                    @MethodSource("integerProvider")
                        // References the stream of doubles below
                    void testMaxLessThanMin(double min, double max) {
                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative min", "name", "price", min, max)
                        );
                    }


                    @ParameterizedTest(name = "filter max less than min {0}")
                    @DisplayName("Max less than min long")
                    @MethodSource("integerProvider")
                        // References the stream of doubles below
                    void testMaxLessThanMin(long min, long max) {
                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative min", "name", "price", min, max)
                        );
                    }

                    @ParameterizedTest(name = "filter max less than min {0}")
                    @DisplayName("Max less than min instant")
                    @MethodSource("integerProvider")
                        // References the stream of doubles below
                    void testMaxLessThanMin(int min, int max) {
                        Instant minInstant = Instant.ofEpochSecond(min);
                        Instant maxInstant = Instant.ofEpochSecond(max);

                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative min", "name", "price", minInstant, maxInstant)
                        );
                    }

                    // Provides multiple parameters for the max less than min test
                    static Stream<Arguments> integerProvider() {
                        return Stream.of(
                                arguments(3, 1),
                                arguments(15, 13)
                        );
                    }
                }
            }
        }
    }


    @Disabled("Until database.delete has been implemented")
    @Nested
    @DisplayName("CRUD Deleter")
    class CRUDDeleterTest {
        @ParameterizedTest
        @DisplayName("Delete valid doubleFilter")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void deleteValidDoubleFilter(int id, String name, String description, String productAttribute, double min, double max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            } catch (InvalidFilterTypeException e) {
                fail(e);
            }


            RangeFilter rangeFilter = new DoubleFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.delete(rangeFilterFromDataBase.getId()), rangeFilter,
                        "The deleted filter was not the target filter, see 'equals' under 'RangeFilterCRUD' " +
                                "or check the filter id's");
                Assertions.assertThrows(IdNotFoundException.class,
                        () -> rangeFilterCRUD.read(rangeFilter.getId()), "Filter was not actually deleted");
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e){
                fail("Fail because filter type does not exist");
            }
        }

        @ParameterizedTest
        @DisplayName("Delete valid timeFilter")
        @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
        void deleteValidTimeFilter(int id, String name, String description, String productAttribute, Instant min, Instant max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            } catch (InvalidFilterTypeException e) {
                fail(e);
            }

            RangeFilter rangeFilter = new TimeFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.delete(rangeFilterFromDataBase.getId()), rangeFilter,
                        "The deleted filter was not the target filter, see 'equals' under 'RangeFilterCRUD' " +
                                "or check the filter id's");
                Assertions.assertThrows(IdNotFoundException.class,
                        () -> rangeFilterCRUD.read(rangeFilter.getId()), "Filter was not actually deleted");
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e){
                fail("Fail because filter type does not exist");
            }
        }

        @ParameterizedTest
        @DisplayName("Delete valid longFilter")
        @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
        void deleteValidLongFilter(int id, String name, String description, String productAttribute, long min, long max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            } catch (InvalidFilterTypeException e) {
                fail(e);
            }

            RangeFilter rangeFilter = new LongFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.delete(rangeFilterFromDataBase.getId()), rangeFilter,
                        "The deleted filter was not the target filter, see 'equals' under 'RangeFilterCRUD' " +
                                "or check the filter id's");
                Assertions.assertThrows(IdNotFoundException.class,
                        () -> rangeFilterCRUD.read(rangeFilter.getId()), "Filter was not actually deleted");
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e) {
                fail("Fail because filter type does not exist");
            }
        }

        @ParameterizedTest
        @DisplayName("Delete filter that does not exist")
        @ValueSource(ints = {Integer.MIN_VALUE, -10, -Integer.MAX_VALUE, Integer.MAX_VALUE, 0})
        void deleteFilterThatDoesNotExist(int input) {
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> rangeFilterCRUD.delete(input), "See 'delete' under 'RangeFilterCRUD' or check if test input id's exist in database"
            );
        }

        @ParameterizedTest
        @DisplayName("Delete double filter twice")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void deleteDoubleFilterTwice(int id, String name, String description, String productAttribute, double min, double max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            } catch (InvalidFilterTypeException e) {
                fail(e);
            }

            RangeFilter rangeFilter = new DoubleFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            // This deletes the RangeFilter from the database, and make sure it does not throw an exception
            assertDoesNotThrow(() -> rangeFilterCRUD.delete(rangeFilter.getId()));

            // This deletes the same RangeFilter a second time and should throw the IdNotFoundException
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> rangeFilterCRUD.delete(rangeFilter.getId()));
        }

        @ParameterizedTest
        @DisplayName("Delete time filter twice")
        @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
        void deleteTimeFilterTwice(int id, String name, String description, String productAttribute, Instant min, Instant max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD' " + e);
            } catch (InvalidFilterTypeException e) {
                fail(e);
            }

            RangeFilter rangeFilter = new TimeFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            // This deletes the RangeFilter from the database, and make sure it does not throw an exception
            assertDoesNotThrow(() -> rangeFilterCRUD.delete(rangeFilter.getId()));

            // This deletes the same RangeFilter a second time and should throw the IdNotFoundException
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> rangeFilterCRUD.delete(rangeFilter.getId()));
        }

        @ParameterizedTest
        @DisplayName("Delete long filter twice")
        @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
        void deleteLongFilterTwice(int id, String name, String description, String productAttribute, long min, long max) {
            //expected fail until database.delete has been implemented

            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD' " + e);
            } catch (InvalidFilterTypeException e) {
                fail(e);
            }

            RangeFilter rangeFilter = new LongFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            // This deletes the RangeFilter from the database, and make sure it does not throw an exception
            assertDoesNotThrow(() -> rangeFilterCRUD.delete(rangeFilter.getId()));

            // This deletes the same RangeFilter a second time and should throw the IdNotFoundException
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> rangeFilterCRUD.delete(rangeFilter.getId()));
        }
    }

    //Tests may have to clean up the database after, since we create a lot of rangefilters in the db
    @Nested
    @DisplayName("CRUD Reader")
    class CRUDReaderTest {
        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Read valid double filter")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void testReadFromRangeFilterDatabase(int id, String name, String description, String productAttribute, double min, double max) {
            // Shooting for 3 filters of each type should be fine
            // So each Csv file of filters should contain 3 files. Remember to add these in the sql file.
            try {
                RangeFilter actual = rangeFilterCRUD.read(id);
                RangeFilter expected = new DoubleFilter(id, name, description, productAttribute, min, max);
                assertEquals(expected, actual);
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e) {
                fail("The filter type retrieved from the database, does not match implemented types. Make sure not to make your own implementation of the interface");
            }

            // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Read valid long filter")
        @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
        void testReadLongFromRangeFilterDatabase(int id, String name, String description, String productAttribute, long min, long max) {
            // Shooting for 3 filters of each type should be fine
            // So each Csv file of filters should contain 3 files. Remember to add these in the sql file.
            try {
                RangeFilter actual = rangeFilterCRUD.read(id);
                RangeFilter expected = new LongFilter(id, name, description, productAttribute, min, max);
                assertEquals(expected, actual);
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e) {
                fail("The filter type retrieved from the database, does not match implemented types. Make sure not to make your own implementation of the interface");
            }

            // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
        }

        @ParameterizedTest(name = "{0} : {1} min:{4} max:{5}")
        @DisplayName("Read valid time filter")
        @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
        void testReadTimeFromRangeFilterDatabase(int id, String name, String description, String productAttribute, Instant min, Instant max) {
            // Shooting for 3 filters of each type should be fine
            // So each Csv file of filters should contain 3 files. Remember to add these in the sql file.
            try {
                RangeFilter actual = rangeFilterCRUD.read(id);
                RangeFilter expected = new TimeFilter(id, name, description, productAttribute, min, max);
                assertEquals(expected, actual);
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e) {
                fail("The filter type retrieved from the database, does not match implemented types. Make sure not to make your own implementation of the interface");
            }

            // we deliberately choose not to test if the product attributes read correspond to valid productAttributes
        }

        @ParameterizedTest
        @DisplayName("Read filter that does not exist")
        @ValueSource(ints = {Integer.MIN_VALUE, -10, -Integer.MAX_VALUE, Integer.MAX_VALUE, 0})
        void readFilterThatDoesNotExist(int input) {
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> rangeFilterCRUD.read(input), "See 'read' under 'RangeFilterCRUD' or check if test input id's exist in database"
            );
        }

        @Test
        @DisplayName("Read all existing range filters")
        void readAllExistingRangeFilters() {
            List<RangeFilter> result = rangeFilterCRUD.readAll();
            Assertions.assertAll("Read all filters from database and check if the values match",
                    () -> Assertions.assertEquals(new DoubleFilter(1, "test name double", "test description", "price", 0, 10), result.get(0)),
                    () -> Assertions.assertEquals(new LongFilter(2, "test name ean", "test description for long filter", "ean", 2, 100), result.get(1)),
                    () -> Assertions.assertEquals(new TimeFilter(3, "test name time", "test description for time filter", "expirationDate", Instant.parse("2018-11-30T15:35:24Z"), Instant.parse("2022-11-30T15:35:24Z")), result.get(2))
            );
        }

        // Below is commented out because it is very hard for us to make the database empty
        // At least if we want robust tests of the other parts to the system

//        @Test
//        @DisplayName("Read from an empty database")
//        void readFromAnEmptyDatabase() {
//            //Database have to be empty for the test to pass
//            List<RangeFilter>
//            //Fix this test, so that it expects an empty list of filters.
//            Assertions.assertThrows(EmptyDatabaseException.class,
//                    () -> rangeFilterCRUD.readAll(),"Database not empty"
//            );
//        }
    }

    @Nested
    @DisplayName("CRUD Update")
    class CRUDUpdateTest {

        @Nested
        @DisplayName("Valid updates")
        class validUpdates {
            private static List<RangeFilter> listOfRangeFilters = new ArrayList<>();

            public static List<RangeFilter> provideRangeFilterForTest() {
                RangeFilterCRUD rangeFilterCRUD2 = new RangeFilterCRUD();
                DBMigration dbMigration = new DBMigration();

                if (listOfRangeFilters.size() >= 3) {
                    listOfRangeFilters = new ArrayList<>();
                }

                try {
                    //Cleaning the database to avoid duplicate keys
                    dbMigration.runSQLFromFile(DBConnection.getPooledConnection(), "src/main/java/dk/sdu/se_f22/sharedlibrary/db/modifiedRangeFilters.sql");

                    //Adding RangeFilters to list
                    listOfRangeFilters.add(rangeFilterCRUD2.create("UpdateDoubleFilterTest", "drfghj", "Gulv", 1.0, 13.0));
                    listOfRangeFilters.add(rangeFilterCRUD2.create("UpdateLongFilterTest", "drfghj", "Gulvf", 10, 130));
                    listOfRangeFilters.add(rangeFilterCRUD2.create("UpdateTimeFilterTest", "drfghj", "Gulvc", Instant.parse("2018-11-30T15:35:24.00Z"), Instant.parse("2022-11-30T15:35:24.00Z")));
                } catch (InvalidFilterException | InvalidFilterTypeException | SQLException e) {
                    e.printStackTrace();
                }
                return listOfRangeFilters;
            }

            @Nested
            @DisplayName("Updating valid information should not throw an exception")
            class updatingValidInformationShouldNotThrowAnException {
                static List<RangeFilter> provideRangeFilterForTest() {
                    return validUpdates.provideRangeFilterForTest();
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Updating the name should not throw an exception")
                @MethodSource("provideRangeFilterForTest")
                void updatingTheNameShouldNotThrowAnException(RangeFilter rangefilter) {
                    Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getName() + " mfied1"));
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Updating the name and description should not throw an exception")
                @MethodSource("provideRangeFilterForTest")
                void updatingTheNameAndDescriptionShouldNotThrowAnException(RangeFilter rangefilter) {
                    Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getName() + " mfied2", rangefilter.getDescription() + " mfied2"));

                }

//                @ParameterizedTest(name = "{0}")
//                @DisplayName("Updating the description but not changing the name should not throw an exception")
//                @MethodSource("provideRangeFilterForTest")
//                void updatingTheDescriptionButNotChangingTheNameShouldNotThrowAnException(RangeFilter rangefilter) {
//                    Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getName(), rangefilter.getDescription() + " mfied3"));
//                }

                @Nested
                @DisplayName("Updating dbValues should not throw an exception")
                class updatingDbValuesShouldNotThrowAnException {

                    @Nested
                    @DisplayName("updating Double filters in db")
                    class updatingDoubleFiltersInDb {

                        @Test
                        @DisplayName("min updating only should not throw an exception")
                        void minUpdatingOnlyShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(1);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinDouble() + 1.0, rangefilter.getDbMaxDouble()));
                        }

                        @Test
                        @DisplayName("max updating only should not throw an exception")
                        void maxUpdatingOnlyShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(1);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinDouble(), rangefilter.getDbMaxDouble() + 1.7));
                        }

                        @Test
                        @DisplayName("Both updating should not throw an exception")
                        void bothUpdatingShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(1);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinDouble() + 1.0, rangefilter.getDbMaxDouble() + 4.0));
                        }
                    }

                    @Nested
                    @DisplayName("updating long filters in db")
                    class updatingLongFiltersInDb {

                        @Test
                        @DisplayName("min updating only should not throw an exception")
                        void minUpdatingOnlyShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(2);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinLong() + 6, rangefilter.getDbMaxLong()));
                        }


                        @Test
                        @DisplayName("max updating only should not throw an exception")
                        void maxUpdatingOnlyShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(2);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinLong(), rangefilter.getDbMaxLong() + 47));
                        }

                        @Test
                        @DisplayName("Both updating should not throw an exception")
                        void bothUpdatingShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(2);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinLong() + 10, rangefilter.getDbMaxLong() + 40));
                        }
                    }

                    @Nested
                    @DisplayName("updating time filters in db")
                    class updatingTimeFiltersInDb {

                        @Test
                        @DisplayName("min updating only should not throw an exception")
                        void minUpdatingOnlyShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(3);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinInstant(), rangefilter.getDbMaxInstant().plusSeconds(10)));
                        }

                        @Test
                        @DisplayName("max updating only should not throw an exception")
                        void maxUpdatingOnlyShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(3);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinInstant().plusSeconds(152), rangefilter.getDbMaxInstant()));
                        }

                        @Test
                        @DisplayName("Both updating should not throw an exception")
                        void bothUpdatingShouldNotThrowAnException() throws UnknownFilterTypeException, IdNotFoundException {
                            final RangeFilter rangefilter;
                            rangefilter = rangeFilterCRUD.read(3);
                            Assertions.assertDoesNotThrow(() -> rangeFilterCRUD.update(rangefilter,rangefilter.getDbMinInstant().plusSeconds(22), rangefilter.getDbMaxInstant().plusSeconds(10)));
                        }
                    }
                }
            }

            @Nested
            @DisplayName("Updating valid information should result in a change of database state")
            class updatingValidInformationShouldResultInAChangeOfDatabaseState {
                static List<RangeFilter> provideRangeFilterForTest() {
                    return validUpdates.provideRangeFilterForTest();
                }

                // the three tests below should be run for each type of filter
                @ParameterizedTest
                @DisplayName("Updating valid name should change the name stored in db")
                @MethodSource("provideRangeFilterForTest")
                void updatingValidNameShouldChangeTheNameStoredInDb(RangeFilter rangefilter) throws UnknownFilterTypeException, IdNotFoundException, IllegalImplementationException, SQLException, InvalidFilterTypeException {
                    String newName = rangefilter.getName() + "mfied4";
                    System.out.println(rangeFilterCRUD.read(rangefilter.getId()));

                    rangeFilterCRUD.update(rangefilter, newName);

                    RangeFilter modifiedFilter = rangeFilterCRUD.read(rangefilter.getId());

                    assertAll(
                            () -> assertEquals(newName, modifiedFilter.getName()),

                            () -> assertEquals(rangefilter.getType(), modifiedFilter.getType()),
                            () -> assertEquals(rangefilter.getDescription(), modifiedFilter.getDescription()),
                            () -> assertEquals(rangefilter.getProductAttribute(), modifiedFilter.getProductAttribute())
                    );
                    if (rangefilter instanceof DoubleFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinDouble(), modifiedFilter.getDbMinDouble()),
                                () -> assertEquals(rangefilter.getDbMaxDouble(), modifiedFilter.getDbMaxDouble()),
                                () -> assertEquals(rangefilter.getUserMaxDouble(), modifiedFilter.getUserMaxDouble()),
                                () -> assertEquals(rangefilter.getUserMinDouble(), modifiedFilter.getUserMinDouble())

                        );
                    }
                    if (rangefilter instanceof LongFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinLong(), modifiedFilter.getDbMinLong()),
                                () -> assertEquals(rangefilter.getDbMaxLong(), modifiedFilter.getDbMaxLong()),
                                () -> assertEquals(rangefilter.getUserMaxLong(), modifiedFilter.getUserMaxLong()),
                                () -> assertEquals(rangefilter.getUserMinLong(), modifiedFilter.getUserMinLong())

                        );
                    }
                    if (rangefilter instanceof TimeFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinInstant(), modifiedFilter.getDbMinInstant()),
                                () -> assertEquals(rangefilter.getDbMaxInstant(), modifiedFilter.getDbMaxInstant()),
                                () -> assertEquals(rangefilter.getUserMaxInstant(), modifiedFilter.getUserMaxInstant()),
                                () -> assertEquals(rangefilter.getUserMinInstant(), modifiedFilter.getUserMinInstant())

                        );
                    }
                }

                @ParameterizedTest
                @DisplayName("Updating only the description should change the description stored in the db")
                @MethodSource("provideRangeFilterForTest")
                void updatingOnlyTheDescriptionShouldChangeTheDescriptionStoredInTheDb(RangeFilter rangefilter) throws UnknownFilterTypeException, IdNotFoundException, IllegalImplementationException, SQLException, InvalidFilterTypeException {
                    String newDescription = rangefilter.getDescription() + " mfied5";
                    rangeFilterCRUD.update(rangefilter, rangefilter.getName(), newDescription);

                    RangeFilter modifiedFilter = rangeFilterCRUD.read(rangefilter.getId());

                    assertAll(
                            () -> assertEquals(newDescription, modifiedFilter.getDescription()),

                            () -> assertEquals(rangefilter.getName(), modifiedFilter.getName()),
                            () -> assertEquals(rangefilter.getType(), modifiedFilter.getType()),
                            () -> assertEquals(rangefilter.getProductAttribute(), modifiedFilter.getProductAttribute())
                    );
                    if (rangefilter instanceof DoubleFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinDouble(), modifiedFilter.getDbMinDouble()),
                                () -> assertEquals(rangefilter.getDbMaxDouble(), modifiedFilter.getDbMaxDouble()),
                                () -> assertEquals(rangefilter.getUserMaxDouble(), modifiedFilter.getUserMaxDouble()),
                                () -> assertEquals(rangefilter.getUserMinDouble(), modifiedFilter.getUserMinDouble())

                        );
                    }
                    if (rangefilter instanceof LongFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinLong(), modifiedFilter.getDbMinLong()),
                                () -> assertEquals(rangefilter.getDbMaxLong(), modifiedFilter.getDbMaxLong()),
                                () -> assertEquals(rangefilter.getUserMaxLong(), modifiedFilter.getUserMaxLong()),
                                () -> assertEquals(rangefilter.getUserMinLong(), modifiedFilter.getUserMinLong())

                        );
                    }
                    if (rangefilter instanceof TimeFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinInstant(), modifiedFilter.getDbMinInstant()),
                                () -> assertEquals(rangefilter.getDbMaxInstant(), modifiedFilter.getDbMaxInstant()),
                                () -> assertEquals(rangefilter.getUserMaxInstant(), modifiedFilter.getUserMaxInstant()),
                                () -> assertEquals(rangefilter.getUserMinInstant(), modifiedFilter.getUserMinInstant())

                        );
                    }
                }


                @ParameterizedTest
                @DisplayName("Updating both name and description should change both stored in db")
                @MethodSource("provideRangeFilterForTest")
                void updatingBothNameAndDescriptionShouldChangeBothStoredInDb(RangeFilter rangefilter) throws UnknownFilterTypeException, IdNotFoundException, IllegalImplementationException, SQLException, InvalidFilterTypeException {
                    String newName = rangefilter.getName() + "mfied6";
                    String newDescription = rangefilter.getDescription() + "mfied6";

                    rangeFilterCRUD.update(rangefilter, newName, newDescription);

                    RangeFilter modifiedFilter = rangeFilterCRUD.read(rangefilter.getId());

                    assertAll(
                            () -> assertEquals(newDescription, modifiedFilter.getDescription()),
                            () -> assertEquals(newName, modifiedFilter.getName()),

                            () -> assertEquals(rangefilter.getType(), modifiedFilter.getType()),
                            () -> assertEquals(rangefilter.getProductAttribute(), modifiedFilter.getProductAttribute())
                    );
                    if (rangefilter instanceof DoubleFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinDouble(), modifiedFilter.getDbMinDouble()),
                                () -> assertEquals(rangefilter.getDbMaxDouble(), modifiedFilter.getDbMaxDouble()),
                                () -> assertEquals(rangefilter.getUserMaxDouble(), modifiedFilter.getUserMaxDouble()),
                                () -> assertEquals(rangefilter.getUserMinDouble(), modifiedFilter.getUserMinDouble())

                        );
                    }
                    if (rangefilter instanceof LongFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinLong(), modifiedFilter.getDbMinLong()),
                                () -> assertEquals(rangefilter.getDbMaxLong(), modifiedFilter.getDbMaxLong()),
                                () -> assertEquals(rangefilter.getUserMaxLong(), modifiedFilter.getUserMaxLong()),
                                () -> assertEquals(rangefilter.getUserMinLong(), modifiedFilter.getUserMinLong())

                        );
                    }
                    if (rangefilter instanceof TimeFilter) {
                        assertAll(
                                () -> assertEquals(rangefilter.getDbMinInstant(), modifiedFilter.getDbMinInstant()),
                                () -> assertEquals(rangefilter.getDbMaxInstant(), modifiedFilter.getDbMaxInstant()),
                                () -> assertEquals(rangefilter.getUserMaxInstant(), modifiedFilter.getUserMaxInstant()),
                                () -> assertEquals(rangefilter.getUserMinInstant(), modifiedFilter.getUserMinInstant())

                        );
                    }
                }

                @Nested
                @DisplayName("Double filters updating should change db state")
                class doubleFiltersUpdatingShouldChangeDbState {

                    @Test
                    @DisplayName("Min updating only should change the min stored in db")
                    void minUpdatingOnlyShouldChangeTheMinStoredInDb() throws UnknownFilterTypeException, IdNotFoundException, InvalidFilterTypeException, IllegalImplementationException, SQLException {
                        RangeFilter filter = rangeFilterCRUD.read(1);
                        // id 1 should be a double filter

                        double valueToAdd = 1.0;
                        // update the filter
                        rangeFilterCRUD.update(filter, filter.getDbMinDouble() + valueToAdd, filter.getDbMaxDouble());

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(1);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(filter.getDbMinDouble() + valueToAdd, modifiedFilter.getDbMinDouble()),

                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getDbMaxDouble(), modifiedFilter.getDbMaxDouble()),
                                () -> assertEquals(filter.getUserMaxDouble(), modifiedFilter.getUserMaxDouble()),
                                () -> assertEquals(filter.getUserMinDouble(), modifiedFilter.getUserMinDouble())
                        );
                    }

                    @Test
                    @DisplayName("Max updating only should change the max stored in db")
                    void maxUpdatingOnlyShouldChangeTheMinStoredInDb() throws InvalidFilterTypeException, UnknownFilterTypeException, IdNotFoundException, IllegalImplementationException, SQLException {
                        RangeFilter filter = rangeFilterCRUD.read(1);
                        // id 1 should be a double filter

                        double valueToAdd = 1.0;
                        // update the filter
                        rangeFilterCRUD.update(filter, filter.getDbMinDouble(), filter.getDbMaxDouble() + valueToAdd);

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(1);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(filter.getDbMaxDouble() + valueToAdd, modifiedFilter.getDbMaxDouble()),

                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getDbMinDouble(), modifiedFilter.getDbMinDouble()),
                                () -> assertEquals(filter.getUserMaxDouble(), modifiedFilter.getUserMaxDouble()),
                                () -> assertEquals(filter.getUserMinDouble(), modifiedFilter.getUserMinDouble())
                        );
                    }

                    @Test
                    @DisplayName("Both updating min and max should change their values stored in db")
                    void bothUpdatingMinAndMaxShouldChangeTheirValuesStoredInDb() throws UnknownFilterTypeException, IdNotFoundException, InvalidFilterTypeException, IllegalImplementationException, SQLException {
                        RangeFilter filter = rangeFilterCRUD.read(1);
                        // id 1 should be a double filter

                        double valueToAdd = 1.0;
                        // update the filter
                        rangeFilterCRUD.update(filter, filter.getDbMinDouble() + valueToAdd * 2, filter.getDbMaxDouble() + valueToAdd);

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(1);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(filter.getDbMinDouble() + valueToAdd * 2, modifiedFilter.getDbMinDouble()),
                                () -> assertEquals(filter.getDbMaxDouble() + valueToAdd, modifiedFilter.getDbMaxDouble()),

                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getUserMaxDouble(), modifiedFilter.getUserMaxDouble()),
                                () -> assertEquals(filter.getUserMinDouble(), modifiedFilter.getUserMinDouble())
                        );
                    }

                }

                @Nested
                @DisplayName("Long filters updating should change db state")
                class longFiltersUpdatingShouldChangeDbState {

                    @Test
                    @DisplayName("Min updating only should change the min stored in db")
                    void minUpdatingOnlyShouldChangeTheMinStoredInDb() throws UnknownFilterTypeException, IdNotFoundException, InvalidFilterTypeException, IllegalImplementationException, SQLException {
                        int id = 2;
                        // id 2 should be a long filter
                        RangeFilter filter = rangeFilterCRUD.read(id);

                        long valueToAdd = 1;
                        // update the filter
                        rangeFilterCRUD.update(filter, filter.getDbMinLong() + valueToAdd, filter.getDbMaxLong());

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(id);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(filter.getDbMinLong() + valueToAdd, modifiedFilter.getDbMinLong()),

                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getDbMaxLong(), modifiedFilter.getDbMaxLong()),
                                () -> assertEquals(filter.getUserMaxLong(), modifiedFilter.getUserMaxLong()),
                                () -> assertEquals(filter.getUserMinLong(), modifiedFilter.getUserMinLong())
                        );
                    }

                    @Test
                    @DisplayName("Max updating only should change the max stored in db")
                    void maxUpdatingOnlyShouldChangeTheMinStoredInDb() throws UnknownFilterTypeException, IdNotFoundException, InvalidFilterTypeException, IllegalImplementationException, SQLException {
                        int id = 2;
                        // id 2 should be a long filter
                        RangeFilter filter = rangeFilterCRUD.read(id);

                        long valueToAdd = 1;
                        // update the filter
                        rangeFilterCRUD.update(filter, filter.getDbMinLong(), filter.getDbMaxLong() + valueToAdd);

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(id);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(filter.getDbMaxLong() + valueToAdd, modifiedFilter.getDbMaxLong()),

                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getDbMinLong(), modifiedFilter.getDbMinLong()),
                                () -> assertEquals(filter.getUserMaxLong(), modifiedFilter.getUserMaxLong()),
                                () -> assertEquals(filter.getUserMinLong(), modifiedFilter.getUserMinLong())
                        );
                    }

                    @Test
                    @DisplayName("Both updating min and max should change their values stored in db")
                    void bothUpdatingMinAndMaxShouldChangeTheirValuesStoredInDb() throws UnknownFilterTypeException, IdNotFoundException, InvalidFilterTypeException, IllegalImplementationException, SQLException {
                        int id = 2;
                        // id 2 should be a long filter
                        RangeFilter filter = rangeFilterCRUD.read(id);

                        long valueToAdd = 1;
                        // update the filter
                        rangeFilterCRUD.update(filter, filter.getDbMinLong() + valueToAdd * 2, filter.getDbMaxLong() + valueToAdd);

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(id);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(filter.getDbMinLong() + valueToAdd * 2, modifiedFilter.getDbMinLong()),
                                () -> assertEquals(filter.getDbMaxLong() + valueToAdd, modifiedFilter.getDbMaxLong()),
                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getUserMaxLong(), modifiedFilter.getUserMaxLong()),
                                () -> assertEquals(filter.getUserMinLong(), modifiedFilter.getUserMinLong())
                        );
                    }
                }

                @Nested
                @DisplayName("Time filters updating should change db state")
                class timeFiltersUpdatingShouldChangeDbState {

                    @Test
                    @DisplayName("Min updating only should change the min stored in db")
                    void minUpdatingOnlyShouldChangeTheMinStoredInDb() throws UnknownFilterTypeException, IdNotFoundException, InvalidFilterTypeException, IllegalImplementationException, SQLException {
                        int id = 3;
                        // id 3 should be a time filter
                        RangeFilter filter = rangeFilterCRUD.read(id);

                        int secondsToAdd = 60 * 60;
                        // one hour is 3600 seconds

                        Instant newInstant = filter.getDbMinInstant().plusSeconds(secondsToAdd);

                        // update the filter
                        rangeFilterCRUD.update(filter, newInstant, filter.getDbMaxInstant());

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(id);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(newInstant, modifiedFilter.getDbMinInstant()),

                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getDbMaxInstant(), modifiedFilter.getDbMaxInstant()),
                                () -> assertEquals(filter.getUserMaxInstant(), modifiedFilter.getUserMaxInstant()),
                                () -> assertEquals(filter.getUserMinInstant(), modifiedFilter.getUserMinInstant())
                        );
                    }

                    @Test
                    @DisplayName("Max updating only should change the max stored in db")
                    void maxUpdatingOnlyShouldChangeTheMinStoredInDb() throws UnknownFilterTypeException, IdNotFoundException, InvalidFilterTypeException, IllegalImplementationException, SQLException {
                        int id = 3;
                        // id 3 should be a time filter
                        RangeFilter filter = rangeFilterCRUD.read(id);

                        int secondsToAdd = 60 * 60;
                        // one hour is 3600 seconds

                        Instant newInstant = filter.getDbMaxInstant().plusSeconds(secondsToAdd);

                        // update the filter
                        rangeFilterCRUD.update(filter, filter.getDbMinInstant(), newInstant);

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(id);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(newInstant, modifiedFilter.getDbMaxInstant()),

                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getDbMinInstant(), modifiedFilter.getDbMinInstant()),
                                () -> assertEquals(filter.getUserMaxInstant(), modifiedFilter.getUserMaxInstant()),
                                () -> assertEquals(filter.getUserMinInstant(), modifiedFilter.getUserMinInstant())
                        );
                    }

                    @Test
                    @DisplayName("Both updating min and max should change their values stored in db")
                    void bothUpdatingMinAndMaxShouldChangeTheirValuesStoredInDb() throws UnknownFilterTypeException, IdNotFoundException, InvalidFilterTypeException, IllegalImplementationException, SQLException {
                        int id = 3;
                        // id 3 should be a time filter
                        RangeFilter filter = rangeFilterCRUD.read(id);

                        int secondsToAdd = 60 * 60;
                        // one hour is 3600 seconds

                        Instant modifiedMax = filter.getDbMaxInstant().plusSeconds(secondsToAdd);
                        Instant modifiedMin = filter.getDbMinInstant().plusSeconds(secondsToAdd * 2);

                        // update the filter
                        rangeFilterCRUD.update(filter, modifiedMin, modifiedMax);

                        RangeFilter modifiedFilter = rangeFilterCRUD.read(id);
                        // check that all values still match and that the new value has been correctly modified
                        assertAll(
                                () -> assertEquals(modifiedMax, modifiedFilter.getDbMaxInstant()),
                                () -> assertEquals(modifiedMin, modifiedFilter.getDbMinInstant()),

                                () -> assertEquals(filter.getName(), modifiedFilter.getName()),
                                () -> assertEquals(filter.getType(), modifiedFilter.getType()),
                                () -> assertEquals(filter.getDescription(), modifiedFilter.getDescription()),
                                () -> assertEquals(filter.getProductAttribute(), modifiedFilter.getProductAttribute()),
                                () -> assertEquals(filter.getUserMaxInstant(), modifiedFilter.getUserMaxInstant()),
                                () -> assertEquals(filter.getUserMinInstant(), modifiedFilter.getUserMinInstant())
                        );
                    }
                }




            }
        }

        @Nested
        @DisplayName("Invalid filter updates")
        class invalidFilterUpdates {
            @Nested
            @DisplayName("Filter type does not match variable types")
            class filterTypeDoesNotMatchVariableTypes {

                @Nested
                @DisplayName("Incompatible types, should throw an exception")
                class incompatibleTypesShouldThrowAnException {

                    @ParameterizedTest(name = "id: {0}")
                    @DisplayName("Double variables, but non-double filter should throw")
                    @ValueSource(ints = {2, 3})
                    void doubleVariablesButNonDoubleFilter(int id) throws UnknownFilterTypeException, IdNotFoundException {
                        RangeFilter filter = rangeFilterCRUD.read(id);
                        System.out.println("id 2 should be a long filter, id 3 should be a time filter");

                        assertThrows(InvalidFilterTypeException.class, () ->
                                rangeFilterCRUD.update(filter, 1.0, 2.0)
                        );
                    }

                    @ParameterizedTest(name = "id: {0}")
                    @DisplayName("Long variables, but non-long filter should throw")
                    @ValueSource(ints = {1, 3})
                    void longVariablesButNonLongFilterShouldThrow(int id) throws UnknownFilterTypeException, IdNotFoundException {
                        RangeFilter filter = rangeFilterCRUD.read(id);
                        System.out.println("id 1 should be a double filter, id 3 should be a time filter");

                        assertThrows(InvalidFilterTypeException.class, () ->
                                rangeFilterCRUD.update(filter, 1, 2)
                        );
                    }

                    @ParameterizedTest(name = "id: {0}")
                    @DisplayName("Instant variables, but non-instant filter should throw")
                    @ValueSource(ints = {1, 2})
                    void instantVariablesButNonInstantFilterShouldThrow(int id) throws UnknownFilterTypeException, IdNotFoundException {
                        RangeFilter filter = rangeFilterCRUD.read(id);
                        System.out.println("id 1 should be a double filter, id 2 should be a long filter");

                        assertThrows(InvalidFilterTypeException.class, () ->
                                rangeFilterCRUD.update(filter, Instant.parse("2021-11-30T18:35:24.00Z"), Instant.parse("2031-11-30T18:35:24.00Z"))
                        );
                    }
                }

                @Nested
                @DisplayName("Incompatible types should not alter database")
                class incompatibleTypesShouldNotAlterDatabase {
                    @BeforeEach
                    public void setup(){
                        // NOTE: commenting the below out, makes 9 tests pass out of the blue?
                        try {
                            new DBMigration().runSQLFromFile(DBConnection.getPooledConnection(), "src/main/java/dk/sdu/se_f22/sharedlibrary/db/modifiedRangeFilters.sql");
                        } catch (SQLException e) {
                            System.out.println("error when resetting database state, pooled connection threw sql exception:");
                            e.printStackTrace();
                        }
                    }

                    @ParameterizedTest(name = "id: {0}")
                    @DisplayName("Double variables, but non-double filter should not alter db")
                    @ValueSource(ints = {2, 3})
                    void doubleVariablesButNonDoubleFilterShouldNotAlterDb(int id) throws UnknownFilterTypeException, IdNotFoundException, IllegalImplementationException, SQLException {
                        List<RangeFilter> previousState = rangeFilterCRUD.readAll();

                        RangeFilter filter = rangeFilterCRUD.read(id);
                        System.out.println("id 2 should be a long filter, id 3 should be a time filter");

                        try {
                            rangeFilterCRUD.update(filter, 1.0, 2.0);
                        } catch (InvalidFilterTypeException e) {
                            System.out.println("Exception thrown correctly");
                        }

                        List<RangeFilter> endState = rangeFilterCRUD.readAll();
                        assertEquals(previousState, endState);
                    }

                    @ParameterizedTest(name = "id: {0}")
                    @DisplayName("Long variables, but non-long filter should not alter db")
                    @ValueSource(ints = {1, 3})
                    void longVariablesButNonLongFilterShouldNotAlterDb(int id) throws UnknownFilterTypeException, IdNotFoundException, IllegalImplementationException, SQLException {
                        List<RangeFilter> previousState = rangeFilterCRUD.readAll();

                        RangeFilter filter = rangeFilterCRUD.read(id);
                        System.out.println("id 1 should be a double filter, id 3 should be a time filter");

                        try {
                            rangeFilterCRUD.update(filter, 1, 2);
                        } catch (InvalidFilterTypeException e) {
                            System.out.println("Exception thrown correctly");
                        }

                        List<RangeFilter> endState = rangeFilterCRUD.readAll();
                        assertEquals(previousState, endState);
                    }

                    @ParameterizedTest(name = "id: {0}")
                    @DisplayName("Instant variables, but non-instant filter should not alter db")
                    @ValueSource(ints = {1, 2})
                    void instantVariablesButNonInstantFilterShouldNotAlterDb(int id) throws UnknownFilterTypeException, IdNotFoundException, IllegalImplementationException, SQLException {
                        List<RangeFilter> previousState = rangeFilterCRUD.readAll();

                        RangeFilter filter = rangeFilterCRUD.read(id);
                        System.out.println("id 1 should be a double filter, id 2 should be a long filter");

                        try {
                            rangeFilterCRUD.update(filter, Instant.parse("2021-11-30T18:35:24.00Z"), Instant.parse("2031-11-30T18:35:24.00Z"));
                        } catch (InvalidFilterTypeException e) {
                            System.out.println("Exception thrown correctly");
                        }

                        List<RangeFilter> endState = rangeFilterCRUD.readAll();
                        assertEquals(previousState, endState);
                    }

                }
            }

            @Nested
            @Disabled("Will be implemented in sprint 6")
            @DisplayName("Updating invalid information should throw an exception")
            class updatingInvalidInformationShouldThrowAnException {
                static List<RangeFilter> provideRangeFilterForTest() {
                    return validUpdates.provideRangeFilterForTest();
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Updating filters with invalid name should throw exception")
                @MethodSource("provideRangeFilterForTest")
                void updatingFiltersWithInvalidNameShouldThrowException(RangeFilter rangefilter) {
                    String newName = "%*./";
                    Assertions.assertThrows(InvalidFilterException.class, () -> rangeFilterCRUD.update(rangefilter, newName));
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Updating filters with invalid description should throw exception")
                @MethodSource("provideRangeFilterForTest")
                void updatingFiltersWithInvalidDescriptionShouldThrowException(RangeFilter rangefilter) {
                    String newDescription = "!!!.-";
                    Assertions.assertThrows(InvalidFilterException.class, () -> rangeFilterCRUD.update(rangefilter, rangefilter.getName(), newDescription));
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Updating filters with invalid name and description should throw exception")
                @MethodSource("provideRangeFilterForTest")
                void updatingFiltersWithInvalidNameAndDescriptionShouldThrowException(RangeFilter rangefilter) {
                    String newName = "/%   .";
                    String newDescription = "%";
                    Assertions.assertThrows(InvalidFilterException.class, () -> rangeFilterCRUD.update(rangefilter, newName, newDescription));
                }

                @Nested
                @DisplayName("Updating dbValues should throw an exception")
                class updatingDbValuesShouldThrowAnException {
                    @Test
                    @DisplayName("Updating double filter with min greater than max should throw exception")
                    void updatingDoubleFilterWithMinGreaterThanMaxShouldThrowException() throws UnknownFilterTypeException, IdNotFoundException {
                        RangeFilter rangeFilter = rangeFilterCRUD.read(1);
                        Assertions.assertThrows(InvalidFilterException.class, () -> rangeFilterCRUD.update(rangeFilter, 100.0, 1.0));
                    }

                    @Test
                    @DisplayName("Updating long filter with min greater than max should throw exception")
                    void updatingLongFilterWithMinGreaterThanMaxShouldThrowException() throws UnknownFilterTypeException, IdNotFoundException {
                        RangeFilter rangeFilter = rangeFilterCRUD.read(2);
                        Assertions.assertThrows(InvalidFilterException.class, () -> rangeFilterCRUD.update(rangeFilter, 1030, 9));
                    }

                    @Test
                    @DisplayName("Updating Time filter with min greater than max should throw exception")
                    void updatingTimeFilterWithMinGreaterThanMaxShouldThrowException() throws UnknownFilterTypeException, IdNotFoundException {
                        RangeFilter rangeFilter = rangeFilterCRUD.read(3);
                        //315569260 is 10 years in seconds
                        Assertions.assertThrows(InvalidFilterException.class, () -> rangeFilterCRUD.update(rangeFilter, rangeFilter.getDbMinInstant().plusSeconds(3), rangeFilter.getDbMinInstant()));
                    }
                }
            }

            @Nested
            @DisplayName("Invalid specializations")
            class invalidSpecializations {
                @Nested
                @DisplayName("Invalid specialization should result in an exception getting thrown")
                class invalidSpecializationShouldResultInAnExceptionGettingThrown {
                    @Test
                    @DisplayName("Invalid specialization name change throw exception")
                    void invalidSpecializationNameChangeThrowException() {
                        assertThrows(IllegalImplementationException.class,
                                () -> rangeFilterCRUD.update(new IllegalRangeFilterClass(), "new illegal name"));
                    }

                    @Test
                    @DisplayName("Invalid specialization description change throw exception")
                    void invalidSpecializationDescriptionChangeThrowException() {
                        RangeFilter illegalFilter = new IllegalRangeFilterClass();
                        assertThrows(IllegalImplementationException.class,
                                () -> rangeFilterCRUD.update(illegalFilter, illegalFilter.getName(), "new illegal description"));
                    }

                    @Test
                    @DisplayName("Invalid specialization name and description change throw exception")
                    void invalidSpecializationNameAndDescriptionChangeThrowException() {
                        assertThrows(IllegalImplementationException.class,
                                () -> rangeFilterCRUD.update(new IllegalRangeFilterClass(), "new illegal name", "new illegal description"));
                    }

                    @Test
                    @DisplayName("Double invalid specialization should throw an exception")
                    void doubleInvalidSpecializationShouldThrowAnException() {
                        assertThrows(IllegalImplementationException.class,
                                () -> rangeFilterCRUD.update(new IllegalRangeFilterClass(), 1.0, 10000.0));
                    }

                    @Test
                    @DisplayName("Long invalid specialization should throw an exception")
                    void longInvalidSpecializationShouldThrowAnException() {
                        assertThrows(IllegalImplementationException.class,
                                () -> rangeFilterCRUD.update(new IllegalRangeFilterClass(), 1, 20000));
                    }

                    @Test
                    @DisplayName("Instant invalid specialization should throw an exception")
                    void instantInvalidSpecializationShouldThrowAnException() {
                        assertThrows(IllegalImplementationException.class,
                                () -> rangeFilterCRUD.update(new IllegalRangeFilterClass(), Instant.parse("2021-11-30T18:35:24.00Z"), Instant.parse("2031-11-30T18:35:24.00Z")));
                    }
                }

                @Nested
                @DisplayName("Invalid specialization should not change database contents")
                class invalidSpecializationShouldNotChangeDatabaseContents {

                    @Test
                    @DisplayName("Invalid specialization name change should not change database contents")
                    void invalidSpecializationNameChangeShouldNotChangeDatabaseContents() throws SQLException, InvalidFilterTypeException {
                        List<RangeFilter> beforeState = rangeFilterCRUD.readAll();

                        try {
                            rangeFilterCRUD.update(new IllegalRangeFilterClass(), "new illegal name");
                        } catch ( IllegalImplementationException e) {
                            System.out.println("exception thrown correctly");
                        }

                        assertEquals(beforeState, rangeFilterCRUD.readAll());
                    }

                    @Test
                    @DisplayName("Invalid specialization description change should not change database contents")
                    void invalidSpecializationDescriptionChangeShouldNotChangeDatabaseContents() throws SQLException, InvalidFilterTypeException {

                        List<RangeFilter> beforeState = rangeFilterCRUD.readAll();

                        try {
                            RangeFilter illegalFilter = new IllegalRangeFilterClass();
                            rangeFilterCRUD.update(illegalFilter, illegalFilter.getName(), "new illegal description");
                        } catch ( IllegalImplementationException e) {
                            System.out.println("exception thrown correctly");
                        }

                        assertEquals(beforeState, rangeFilterCRUD.readAll());
                    }

                    @Test
                    @DisplayName("Invalid specialization name and description change should not alter db")
                    void invalidSpecializationNameAndDescriptionChangeShouldNotAlterDb() throws SQLException, InvalidFilterTypeException {
                        List<RangeFilter> beforeState = rangeFilterCRUD.readAll();

                        try {
                            rangeFilterCRUD.update(new IllegalRangeFilterClass(), "new illegal name", "new illegal description");
                        } catch ( IllegalImplementationException e) {
                            System.out.println("exception thrown correctly");
                        }

                        assertEquals(beforeState, rangeFilterCRUD.readAll());
                    }

                    @Test
                    @DisplayName("Double variables should not alter db state with illegal implementation")
                    void doubleVariablesShouldNotAlterDbStateWithIllegalImplementation() throws InvalidFilterTypeException, SQLException {
                        List<RangeFilter> beforeState = rangeFilterCRUD.readAll();

                        try {
                            rangeFilterCRUD.update(new IllegalRangeFilterClass(), 1.0, 10000.0);
                        } catch ( IllegalImplementationException e) {
                            System.out.println("exception thrown correctly");
                        }

                        assertEquals(beforeState, rangeFilterCRUD.readAll());
                    }

                    @Test
                    @DisplayName("Long variables should not alter db state with illegal implementation")
                    void longVariablesShouldNotAlterDbStateWithIllegalImplementation() throws InvalidFilterTypeException, SQLException {
                        List<RangeFilter> beforeState = rangeFilterCRUD.readAll();

                        try {
                            rangeFilterCRUD.update(new IllegalRangeFilterClass(), 1, 20000);
                        } catch ( IllegalImplementationException e) {
                            System.out.println("exception thrown correctly");
                        }

                        assertEquals(beforeState, rangeFilterCRUD.readAll());
                    }

                    @Test
                    @DisplayName("Instant variables should not alter db state with illegal implementation")
                    void instantVariablesShouldNotAlterDbStateWithIllegalImplementation() throws InvalidFilterTypeException, SQLException {
                        List<RangeFilter> beforeState = rangeFilterCRUD.readAll();

                        try {
                            rangeFilterCRUD.update(new IllegalRangeFilterClass(), Instant.parse("2021-11-30T18:35:24.00Z"), Instant.parse("2031-11-30T18:35:24.00Z"));
                        } catch ( IllegalImplementationException e) {
                            System.out.println("exception thrown correctly");
                        }

                        assertEquals(beforeState, rangeFilterCRUD.readAll());
                    }
                }


                /**
                 * THis class is used as an example of an illegal implementation of the RangeFilter interface <br>
                 * This can be seen since it does not extend RangeFilterClass
                 */
                class IllegalRangeFilterClass implements RangeFilter {
                    @Override
                    public int getId() {
                        return 0;
                    }

                    @Override
                    public String getName() {
                        return "illegal class";
                    }

                    @Override
                    public String getDescription() {
                        return "illegal class description";
                    }

                    @Override
                    public String getProductAttribute() {
                        return "illegal class product";
                    }

                    @Override
                    public FilterTypes getType() {
                        // This could be debated how smart it is to choose a class explicitly.
                        // It has been done since it may be quite obvious to validate if the class has a gettype that returns
                        // Something else than null
                        return FilterTypes.DOUBLE;
                    }

                    @Override
                    public double getDbMinDouble() {
                        return 0;
                    }

                    @Override
                    public Instant getDbMinInstant() {
                        return null;
                    }

                    @Override
                    public long getDbMinLong() {
                        return 0;
                    }

                    @Override
                    public double getDbMaxDouble() {
                        return 0;
                    }

                    @Override
                    public Instant getDbMaxInstant() {
                        return null;
                    }

                    @Override
                    public long getDbMaxLong() {
                        return 0;
                    }

                    @Override
                    public double getUserMinDouble() {
                        return 0;
                    }

                    @Override
                    public Instant getUserMinInstant() {
                        return null;
                    }

                    @Override
                    public long getUserMinLong() {
                        return 0;
                    }

                    @Override
                    public double getUserMaxDouble() {
                        return 0;
                    }

                    @Override
                    public Instant getUserMaxInstant() {
                        return null;
                    }

                    @Override
                    public long getUserMaxLong() {
                        return 0;
                    }

                    @Override
                    public double setUserMin(double userMin) {
                        return 0;
                    }

                    @Override
                    public Instant setUserMin(Instant userMin) {
                        return null;
                    }

                    @Override
                    public long setUserMin(long userMin) {
                        return 0;
                    }

                    @Override
                    public double setUserMax(double userMax) {
                        return 0;
                    }

                    @Override
                    public Instant setUserMax(Instant userMax) {
                        return null;
                    }

                    @Override
                    public long setUserMax(long userMax) {
                        return 0;
                    }
                }
            }
        }

        @AfterAll
        public static void teardown() {
            try {
                new DBMigration().runSQLFromFile(DBConnection.getPooledConnection(), "src/main/java/dk/sdu/se_f22/sharedlibrary/db/modifiedRangeFilters.sql");
            } catch (SQLException e) {
                System.out.println("error when resetting database state, pooled connection threw sql exception:");
                e.printStackTrace();
            }
        }
    }
}
