package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.Helpers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

//@Execution(ExecutionMode.CONCURRENT)
// Remember to check whether concurrent has a detrimental impact on performance
class RangeFilterClassTest {

    private RangeFilterCRUD rangeFilterCRUD;
    /*
    @BeforeEach
    public void setup(){
        rangeFilterCRUD = new RangeFilterCRUD();
    }

    @AfterEach
    public void teardown(){
        Helpers.resetDB();
    }
     */
    @Nested
    @DisplayName("Non-matching tests")
    class NonMatchingTests {

        @ParameterizedTest
        @DisplayName("Filter and ordinary object")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void filterAndObject(int id, String name, String description, String productAttribute, double min, double max) {
            DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
            Object o = new Object();
            Assertions.assertFalse(doubleFilter.equals(o));
        }

        @ParameterizedTest (name = "{0}" + "+1"Cl + ",{1},{2},{3},{4},{5}")
        @DisplayName("Filters with different ids")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void filtersWithDifferentIds(int id, String name, String description, String productAttribute, double min, double max) {
            DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
            DoubleFilter doubleFilterWithDifferentIds = new DoubleFilter(id+1, name, description, productAttribute, min, max);
            Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentIds));
        }
        @ParameterizedTest (name = "{0},{1}," + "diff" + "{2},{3},{4},{5}")
        @DisplayName("Filters with different names")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void filtersWithDifferentNames(int id, String name, String description, String productAttribute, double min, double max) {
            DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
            DoubleFilter doubleFilterWithDifferentNames = new DoubleFilter(id, name + "diff", description, productAttribute, min, max);
            Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentNames));
        }
        @ParameterizedTest (name = "{0},{1},{2}," + "diff" + "{3},{4},{5}")
        @DisplayName("Filters with different descriptions")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void filtersWithDifferentDescriptions(int id, String name, String description, String productAttribute, double min, double max) {
            DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
            DoubleFilter doubleFilterWithDifferentDescriptions = new DoubleFilter(id, name, description + "diff", productAttribute, min, max);
            Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentDescriptions));
        }
        @ParameterizedTest (name = "{0},{1},{2},{3}" + "diff" + ",{4},{5}")
        @DisplayName("Filters with different product attributes")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void filtersWithDifferentProductAttributes(int id, String name, String description, String productAttribute, double min, double max) {
            DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
            DoubleFilter doubleFilterWithDifferentProductAttributes = new DoubleFilter(id, name, description, productAttribute + "diff", min, max);
            Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentProductAttributes));
        }

    }
    @Test
    @Disabled("not written yet")
    void testEquals() {
        // This test should be split up into many tests to test each step/level of the equals method

        // Here follows an overview of the necessary tests:

        // For non-matching tests, all other attributes should be equal

        // Non-matching filters:
        // A filter and an instance of an ordinary Object should not equal
        // two filters with different ids, should not equal
        // two filters with different names should not equal
        // two filters with different descriptions should not equal
        // two filters looking at different product attributes

        // In specific classes:
        // Different filter types with same attributes
        // DBMin differs
        // DBMax differs
        // UserMin differs
        // USerMax differs


        // Matching filters:
        // Maybe we should create an implementation,
        // which inherits from RangeFilterClass to test if equals works,
        // if the two filters match

        // Specific filter classes
        // Test each type for matching filters
    }
}
