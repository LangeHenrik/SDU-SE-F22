package dk.sdu.se_f22.sortingmodule.range.rangepublic.crud;

import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterException;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.DoubleFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.LongFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.RangeFilter;
import dk.sdu.se_f22.sortingmodule.range.rangepublic.TimeFilter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

public class RangeFilterCRUDTest {

    private RangeFilterCRUD rangeFilterCRUD;

    @BeforeAll
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
            RangeFilter rangeFilter = new LongFilter(rangeFilterFromDataBase.getId(), name, description, productAttribute, (long) min, (long)max);

            // This deletes the RangeFilter from the database, and make sure it does not throw an exception
            assertDoesNotThrow(() -> rangeFilterCRUD.delete(rangeFilter.getId()));


            // This deletes the same RangeFilter a second time and should throw the IdNotFoundException
            Assertions.assertThrows(IdNotFoundException.class,
                    () -> rangeFilterCRUD.delete(rangeFilter.getId()));
            org.junit.jupiter.api.Assertions.fail("not yet implemented");
        }
    }

}
