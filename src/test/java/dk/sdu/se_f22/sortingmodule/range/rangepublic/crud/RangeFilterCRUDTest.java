package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.EmptyDatabaseException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.DoubleFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.LongFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.TimeFilter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;


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
