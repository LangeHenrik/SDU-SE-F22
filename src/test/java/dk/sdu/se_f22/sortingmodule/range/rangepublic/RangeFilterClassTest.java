package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RangeFilterClassTest {
    @Nested
    @DisplayName("Non-matching dummy filters should not be equal")
    class nonMatchingEqualsTests {

        @ParameterizedTest
        @DisplayName("Dummy filter and ordinary object should not be equal")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFilterAndObject(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            Object o = new Object();
            assertNotEquals(rangeFilterClassDummyFilter, o);
        }

        @ParameterizedTest(name = "{0}" + "+1" + ", {1}, {2}, {3}")
        @DisplayName("Dummy filters with different ids should not be equal")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersWithDifferentIds(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClass rangeFilterClassDummyFilterWithDifferentIds = new RangeFilterClassDummyFilter(id + 1, name, description, productAttribute);
            assertNotEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterWithDifferentIds);
        }

        @ParameterizedTest(name = "{0}, {1}=" + "diff" + ", {2}, {3}")
        @DisplayName("Dummy filters with different names should not be equal")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersWithDifferentNames(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClassDummyFilter rangeFilterClassDummyFilterWithDifferentNames = new RangeFilterClassDummyFilter(id, name + "diff", description, productAttribute);
            assertNotEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterWithDifferentNames);
        }

        @ParameterizedTest(name = "{0}, {1}, {2}=" + "diff" + ", {3}")
        @DisplayName("Dummy filters with different descriptions should not be equal")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersWithDifferentDescriptions(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClassDummyFilter rangeFilterClassDummyFilterWithDifferentDescriptions = new RangeFilterClassDummyFilter(id, name, description + "diff", productAttribute);
            assertNotEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterWithDifferentDescriptions);
        }

        @ParameterizedTest(name = "{0}, {1}, {2}, {3}=" + " diff")
        @DisplayName("Dummy filters with different product attributes should not be equal")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersWithDifferentProductAttributes(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClassDummyFilter rangeFilterClassDummyFilterWithDifferentProductAttribute = new RangeFilterClassDummyFilter(id, name, description, productAttribute + "diff");
            assertNotEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterWithDifferentProductAttribute);
        }
    }

    @Nested
    @DisplayName("Matching dummy filters should be equal")
    class matchingEqualsTests {
        @ParameterizedTest(name = "{0}, {1}, {2}, {3}")
        @DisplayName("Matching dummy filters should be equal")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersThatMatch(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClassDummyFilter rangeFilterClassDummyFilterMatching = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            assertEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterMatching);
        }
    }
}


