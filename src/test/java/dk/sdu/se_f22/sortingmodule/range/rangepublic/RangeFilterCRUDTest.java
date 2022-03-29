package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.exceptions.EmptyDatabaseException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;


// Currently, the tests involving instants fail,
// since the string format of instants are not known at the moment,
// and should be researched
public class RangeFilterCRUDTest {

    private RangeFilterCRUD rangeFilterCRUD;

    @BeforeEach
    public void setup () {
        rangeFilterCRUD = new RangeFilterCRUD();
    }

    @Nested
    class CRUDCreatorTest  {
        @Nested
        @DisplayName("Create Db filters")
        class createDbFilters {
            @Nested
            @DisplayName("Valid filters that should pass")
            class validFiltersThatShouldPass {
                @Test
                @DisplayName("ValidFilters with regular attributes")
                    // Maybe later create csv file for testing valid attributes
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
                // Maybe later create csv file for testing valid attributes
            void validFiltersWithRegularAttributesLong() {
                String description = "This filter checks a lot of attributes bla bla";
                String name = "Sample filter";
                String productAttribute = "price";
                long min = 0;
                long max = 800;

                Assertions.assertDoesNotThrow(
                        () -> rangeFilterCRUD.create(description, name, productAttribute, min, max)
                );
            }

            @Test
            @DisplayName("ValidFilters with regular attributes")
                // Maybe later create csv file for testing valid attributes
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
                    //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
                void emptyDescription(String input) {
                    double minDouble = 0;
                    double maxDouble = 800;

                    long minLong = 0;
                    long maxLong = 800;

                    Instant minInstant = Instant.ofEpochSecond(0);
                    Instant maxInstant = Instant.ofEpochSecond(800);

                    Assertions.assertAll("Chcek that the 3 different filter types throw with empty description.",
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
                    //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
                void emptyProductAttribute(String input) {
                    double minDouble = 0;
                    double maxDouble = 800;

                    long minLong = 0;
                    long maxLong = 800;

                    Instant minInstant = Instant.ofEpochSecond(0);
                    Instant maxInstant = Instant.ofEpochSecond(800);

                    Assertions.assertAll("Chcek that the 3 different filter types throw with empty description.",
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("Testing empty product attribute", "sample name", input, minDouble, maxDouble)
                            ),
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("Testing empty product attribute", "sample name", input, minLong, maxLong)
                            ),
                            () -> Assertions.assertThrows(InvalidFilterException.class,
                                    () -> rangeFilterCRUD
                                            .create("Testing empty product attribute", "sample name", input, minInstant, maxInstant)
                            )
                    );
                }


                @ParameterizedTest(name = "filter name {0}")
                @DisplayName("Empty filter name")
                @ValueSource(strings = {"", " "})
                    //@CsvFileSource(resources= "/dk/sdu/se_f22/SortingModule/Range/emptyStrings.csv", numLinesToSkip = 0)
                void emptyFilterNameV2(String input) {
                    double minDouble = 0;
                    double maxDouble = 800;

                    long minLong = 0;
                    long maxLong = 800;

                    Instant minInstant = Instant.ofEpochSecond(0);
                    Instant maxInstant = Instant.ofEpochSecond(800);

                    Assertions.assertAll("Chcek that the 3 different filter types throw with empty description.",
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

                    @ParameterizedTest(name = "filter min negative {0}")
                    @DisplayName("Negative min time")
                    @ValueSource(ints = {-1, -3})
                    void negativeMinTime(int epochSec) {
                        Instant instant = Instant.ofEpochSecond(epochSec); // Format from epochSec. MIGHT BE A BETTER WAY?
                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative min", "name", "price", instant, Instant.ofEpochSecond(500))
                        );
                    }
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

                    @ParameterizedTest(name = "filter max negative {0}")
                    @DisplayName("Negative max double")
                    @ValueSource(ints = {-1, -3})
                    void negativeMaxTime(int epochSec) {
                        Instant instant = Instant.ofEpochSecond(epochSec); // Format from epochSec. MIGHT BE A BETTER WAY?
                        Assertions.assertThrows(InvalidFilterException.class,
                                () -> rangeFilterCRUD.create("negative Max", "name", "price", Instant.ofEpochSecond(0), instant)
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


    @Nested
    class CRUDDeleterTest  {
        @ParameterizedTest
        @DisplayName("Delete valid doubleFilter")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void deleteValidDoubleFilter(int id, String name, String description, String productAttribute, double min, double max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            }

            //This fails until database.create has been implemented
            assertNotNull(rangeFilterFromDataBase);

            RangeFilter rangeFilter = new DoubleFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.delete(rangeFilterFromDataBase.getId()), rangeFilter,
                        "The deleted filter was not the target filter, see 'equals' under 'RangeFilterCRUD' " +
                                "or check the filter id's");
                Assertions.assertThrows(IdNotFoundException.class,
                        () -> rangeFilterCRUD.read(rangeFilter.getId()), "Filter was not actually deleted");
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
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
            }

            //This fails until database.create has been implemented
            assertNotNull(rangeFilterFromDataBase);

            RangeFilter rangeFilter = new TimeFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.delete(rangeFilterFromDataBase.getId()), rangeFilter,
                        "The deleted filter was not the target filter, see 'equals' under 'RangeFilterCRUD' " +
                                "or check the filter id's");
                Assertions.assertThrows(IdNotFoundException.class,
                        () -> rangeFilterCRUD.read(rangeFilter.getId()), "Filter was not actually deleted");
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
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
            }

            //This fails until database.create has been implemented
            assertNotNull(rangeFilterFromDataBase);

            RangeFilter rangeFilter = new LongFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.delete(rangeFilterFromDataBase.getId()), rangeFilter,
                        "The deleted filter was not the target filter, see 'equals' under 'RangeFilterCRUD' " +
                                "or check the filter id's");
                Assertions.assertThrows(IdNotFoundException.class,
                        () -> rangeFilterCRUD.read(rangeFilter.getId()), "Filter was not actually deleted");
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
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
            }
            //This fails until database.create has been implemented
            assertNotNull(rangeFilterFromDataBase);

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
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            }

            //This fails until database.create has been implemented
            assertNotNull(rangeFilterFromDataBase);

            RangeFilter rangeFilter = new TimeFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            // This deletes the RangeFilter from the database, and make sure it does not throw an exception
            assertDoesNotThrow(() -> rangeFilterCRUD.delete(rangeFilter.getId()));

            // This deletes the same RangeFilter a second time and should throw the IdNotFoundException
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> rangeFilterCRUD.delete(rangeFilter.getId()));
        }

        @ParameterizedTest
        @DisplayName("Delete time filter twice")
        @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
        void deleteLongFilterTwice(int id, String name, String description, String productAttribute, long min, long max) {
            //expected fail until database.create has been implemented

            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            }

            assertNotNull(rangeFilterFromDataBase);

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
    class CRUDReaderTest {

        @ParameterizedTest
        @DisplayName("Read valid double filter")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void readValidDoubleFilter(int id, String name, String description, String productAttribute, double min, double max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            }

            //This fails until database.create has been implemented
            assertNotNull(rangeFilterFromDataBase);

            RangeFilter rangeFilter = new DoubleFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.read(rangeFilterFromDataBase.getId()),rangeFilter);
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e) {
                fail("The filter type retrieved from the database, does not match implemented types. Make sure not to make your own implementation of the interface");
            }
        }

        @ParameterizedTest
        @DisplayName("Read valid time filter")
        @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
        void readValidTimeFilter(int id, String name, String description, String productAttribute, Instant min, Instant max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            }

            //This fails until database.create has been implemented
            assertNotNull(rangeFilterFromDataBase);

            RangeFilter rangeFilter = new TimeFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.read(rangeFilterFromDataBase.getId()), rangeFilter);
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e) {
                fail("The filter type retrieved from the database, does not match implemented types. Make sure not to make your own implementation of the interface");
            }
        }

        @ParameterizedTest
        @DisplayName("Read valid long filter")
        @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
        void readValidLongFilter(int id, String name, String description, String productAttribute, long min, long max) {
            RangeFilter rangeFilterFromDataBase = null;
            try {
                rangeFilterFromDataBase = rangeFilterCRUD.create(description, name, productAttribute, min, max);
            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            }

            //This fails until database.create has been implemented
            assertNotNull(rangeFilterFromDataBase);

            RangeFilter rangeFilter = new LongFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, min, max);

            try {
                Assertions.assertEquals(rangeFilterCRUD.read(rangeFilterFromDataBase.getId()), rangeFilter);
            } catch (IdNotFoundException e) {
                fail("Fail because id did not exist");
            } catch (UnknownFilterTypeException e) {
                fail("The filter type retrieved from the database, does not match implemented types. Make sure not to make your own implementation of the interface");
            }
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
            RangeFilter doubleRangeFilterFromDataBase = null;
            RangeFilter timeRangeFilterFromDataBase = null;
            RangeFilter longRangeFilterFromDataBase = null;
            try {
                doubleRangeFilterFromDataBase = rangeFilterCRUD.create("test name double","test description for double","price",0,10);
                timeRangeFilterFromDataBase = rangeFilterCRUD.create("test name time","test description for time","expirationDate",Instant.parse("2018-11-30T18:35:24.00Z"),Instant.parse("2018-11-30T18:35:24.00Z"));
                longRangeFilterFromDataBase = rangeFilterCRUD.create("test name ean","test description for ean","ean",2,100);

            } catch (InvalidFilterException e) {
                fail("The creation of the filter failed. See 'create' under 'rangeFilterCRUD'");
            }

            //This fails until database.create has been implemented
            assertNotNull(doubleRangeFilterFromDataBase);
            assertNotNull(timeRangeFilterFromDataBase);
            assertNotNull(longRangeFilterFromDataBase);

            RangeFilter doubleRangeFilter = new DoubleFilter(doubleRangeFilterFromDataBase.getId(),"test name double","test description for double","price",0,10);
            RangeFilter timeRangeFilter = new TimeFilter(timeRangeFilterFromDataBase.getId(),"test name time","test description for time","expirationDate",Instant.parse("2018-11-30T18:35:24.00Z"),Instant.parse("2018-11-30T18:35:24.00Z"));
            RangeFilter longRangeFilter = new LongFilter(longRangeFilterFromDataBase.getId(),"test name ean","test description for ean","ean",2,100);


            Assertions.assertAll(
                    () -> Assertions.assertEquals(rangeFilterCRUD.readAll().get(0),doubleRangeFilter),
                    () -> Assertions.assertEquals(rangeFilterCRUD.readAll().get(1),timeRangeFilter),
                    () -> Assertions.assertEquals(rangeFilterCRUD.readAll().get(2),longRangeFilter, "See 'read' under 'RangeFilterCRUD' or check if test input id's exist in database")
            );
        }

        @Test
        @DisplayName("Read from an empty database")
        void readFromAnEmptyDatabase() {
            //Database have to be empty for the test to pass
            Assertions.assertThrows(EmptyDatabaseException.class,
                    () -> rangeFilterCRUD.readAll(),"Database not empty"
            );
        }
    }
}
