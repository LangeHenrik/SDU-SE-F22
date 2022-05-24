package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RangeFilterClassTest {
    @Nested
<<<<<<< Updated upstream
    @DisplayName("Non-matching equals tests")
    class NonMatchingTests {

        @Nested
        @DisplayName("Non-matching double filter tests")
        class NonMatchingDoubleFilterTests {

            @ParameterizedTest
            @DisplayName("Double filter and ordinary object")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFilterAndObject(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                Object o = new Object();
                Assertions.assertFalse(doubleFilter.equals(o));
            }

            @ParameterizedTest(name = "{0}" + "+1" + ",{1},{2},{3},{4},{5}")
            @DisplayName("Double filters with different ids")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentIds(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentIds = new DoubleFilter(id + 1, name, description, productAttribute, min, max);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentIds));
            }

            @ParameterizedTest(name = "{0},{1}" + "diff" + ",{2},{3},{4},{5}")
            @DisplayName("Double filters with different names")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentNames(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentNames = new DoubleFilter(id, name + "diff", description, productAttribute, min, max);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentNames));
            }

            @ParameterizedTest(name = "{0},{1},{2}" + "diff" + ",{3},{4},{5}")
            @DisplayName("Double filters with different descriptions")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentDescriptions(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentDescriptions = new DoubleFilter(id, name, description + "diff", productAttribute, min, max);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentDescriptions));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3}" + "diff" + ",{4},{5}")
            @DisplayName("Double filters with different product attributes")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentProductAttributes(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentProductAttributes = new DoubleFilter(id, name, description, productAttribute + "diff", min, max);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentProductAttributes));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3},{4}" + "+1" + ",{5}")
            @DisplayName("Double filters with different min values")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentMinValues(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentMinValues = new DoubleFilter(id, name, description, productAttribute, min + 1, max);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentMinValues));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3},{4},{5}" + "+1")
            @DisplayName("Double filters with different max values")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentMaxValues(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentMaxValues = new DoubleFilter(id, name, description, productAttribute, min, max + 1);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentMaxValues));
            }

            @ParameterizedTest(name = "Long filter: {0},{1},{2},{3},{4},{5}")
            @DisplayName("Double filters with different filter types: Long filter")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentFilterTypesLongFilter(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentType = new LongFilter(id, name, description, productAttribute, (long) min, (long) max);
                Assertions.assertFalse(doubleFilter.equals(longFilterWithDifferentType));
            }

            @ParameterizedTest(name = "Time filter: {0},{1},{2},{3},{4},{5}")
            @DisplayName("Double filters with different types: Time filter")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentTypesTimeFilter(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doublefilter = new DoubleFilter(id, name, description, productAttribute, min, min);
                TimeFilter timeFilterWithDifferentType = new TimeFilter(id, name, description, productAttribute, Instant.MIN, Instant.MAX);
                Assertions.assertFalse(doublefilter.equals(timeFilterWithDifferentType));
            }


            @ParameterizedTest(name = "Different user min: {0},{1},{2},{3},{4},{5}")
            @DisplayName("Double filters with different UserMin values")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentUserMinValues(int id, String name, String description, String productAttribute, double min, double max) throws IllegalMinMaxException {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentUserMin = new DoubleFilter(id, name, description, productAttribute, min, max);
                doubleFilterWithDifferentUserMin.setUserMin(12345.67890);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentUserMin));
            }
=======
    @DisplayName("Non-matching dummy filter equals test")
    class nonMatchingEqualsTests {
>>>>>>> Stashed changes

        @ParameterizedTest
        @DisplayName("Dummy filter and ordinary object")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFilterAndObject(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            Object o = new Object();
            assertNotEquals(rangeFilterClassDummyFilter, o);
        }

        @ParameterizedTest(name = "{0}" + "+1" + ",{1},{2},{3}")
        @DisplayName("Dummy filters with different ids")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersWithDifferentIds(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClass rangeFilterClassDummyFilterWithDifferentIds = new RangeFilterClassDummyFilter(id + 1, name, description, productAttribute);
            assertNotEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterWithDifferentIds);
        }

        @ParameterizedTest(name = "{0},{1}" + "diff" + ",{2},{3}")
        @DisplayName("Dummy filters with different names")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersWithDifferentNames(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClassDummyFilter rangeFilterClassDummyFilterWithDifferentNames = new RangeFilterClassDummyFilter(id, name + "diff", description, productAttribute);
            assertNotEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterWithDifferentNames);
        }

<<<<<<< Updated upstream

    @Nested
    @DisplayName("Matching equals tests")
    class MatchingTests {

        @ParameterizedTest
        @DisplayName("Test matching Double filters")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void testMatchingDoubleFilters(int id, String name, String description, String productAttribute, double min, double max) {
            DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
            DoubleFilter doubleFilterduplicate = new DoubleFilter(id, name, description, productAttribute, min, max);
            Assertions.assertTrue(doubleFilter.equals(doubleFilterduplicate));
=======
        @ParameterizedTest(name = "{0},{1},{2}" + "diff" + ",{3}")
        @DisplayName("Dummy filters with different descriptions")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersWithDifferentDescriptions(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClassDummyFilter rangeFilterClassDummyFilterWithDifferentDescriptions = new RangeFilterClassDummyFilter(id, name, description + "diff", productAttribute);
            assertNotEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterWithDifferentDescriptions);
>>>>>>> Stashed changes
        }

        @ParameterizedTest(name = "{0},{1},{2},{3}" + "diff")
        @DisplayName("Long filters with different product attributes")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersWithDifferentProductAttributes(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClassDummyFilter rangeFilterClassDummyFilterWithDifferentProductAttributes = new RangeFilterClassDummyFilter(id, name, description, productAttribute + "diff");
            assertNotEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterWithDifferentProductAttributes);
        }
    }
    @Nested
    @DisplayName("Matching dummy filter equals tests")
    class matchingEqualsTests {
        @ParameterizedTest(name = "{0},{1},{2},{3}")
        @DisplayName("Matching dummy filters test")
        @CsvFileSource(resources = "DummyFilter.csv", numLinesToSkip = 1)
        void dummyFiltersThatMatch(int id, String name, String description, String productAttribute) {
            RangeFilterClassDummyFilter rangeFilterClassDummyFilter = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            RangeFilterClassDummyFilter rangeFilterClassDummyFilterMatching = new RangeFilterClassDummyFilter(id, name, description, productAttribute);
            assertEquals(rangeFilterClassDummyFilter, rangeFilterClassDummyFilterMatching);
        }
    }
}




