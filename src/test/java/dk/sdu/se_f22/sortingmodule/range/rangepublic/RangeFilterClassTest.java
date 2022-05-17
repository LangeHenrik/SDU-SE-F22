package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Instant;

//@Execution(ExecutionMode.CONCURRENT)
// Remember to check whether concurrent has a detrimental impact on performance
class RangeFilterClassTest {
    @Nested
    @DisplayName("Non-matching tests")
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
            void doubleFiltersWithDifferentUserMinValues(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentUserMin = new DoubleFilter(id, name, description, productAttribute, min, max);
                doubleFilterWithDifferentUserMin.setUserMin(12345.67890);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentUserMin));
            }

            @ParameterizedTest(name = "Different user max: {0},{1},{2},{3},{4},{5} ")
            @DisplayName("Double filters with different UserMax values")
            @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
            void doubleFiltersWithDifferentUserMaxValues(int id, String name, String description, String productAttribute, double min, double max) {
                DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentUserMax = new DoubleFilter(id, name, description, productAttribute, min, max);
                doubleFilterWithDifferentUserMax.setUserMax(12345.67890);
                Assertions.assertFalse(doubleFilter.equals(doubleFilterWithDifferentUserMax));
            }
        }

        @Nested
        @DisplayName("Non-matching long filter test")
        class NonMatchingLongFilterTests {
            @ParameterizedTest
            @DisplayName("Long filter and ordinary object")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFilterAndObject(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                Object o = new Object();
                Assertions.assertFalse(longFilter.equals(o));
            }

            @ParameterizedTest(name = "{0}" + "+1" + ",{1},{2},{3},{4},{5}")
            @DisplayName("Long filters with different ids")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentIds(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentIds = new LongFilter(id + 1, name, description, productAttribute, min, max);
                Assertions.assertFalse(longFilter.equals(longFilterWithDifferentIds));
            }

            @ParameterizedTest(name = "{0},{1}" + "diff" + ",{2},{3},{4},{5}")
            @DisplayName("Long filters with different names")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentNames(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentNames = new LongFilter(id, name + "diff", description, productAttribute, min, max);
                Assertions.assertFalse(longFilter.equals(longFilterWithDifferentNames));
            }

            @ParameterizedTest(name = "{0},{1},{2}" + "diff" + ",{3},{4},{5}")
            @DisplayName("Long filters with different descriptions")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentDescriptions(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentDescriptions = new LongFilter(id, name, description + "diff", productAttribute, min, max);
                Assertions.assertFalse(longFilter.equals(longFilterWithDifferentDescriptions));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3}" + "diff" + ",{4},{5}")
            @DisplayName("Long filters with different product attributes")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentProductAttributes(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentProductAttributes = new LongFilter(id, name, description, productAttribute + "diff", min, max);
                Assertions.assertFalse(longFilter.equals(longFilterWithDifferentProductAttributes));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3},{4}" + "+1" + ",{5}")
            @DisplayName("Long filters with different min values")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentMinValues(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentMinValues = new LongFilter(id, name, description, productAttribute, min + 1, max);
                Assertions.assertFalse(longFilter.equals(longFilterWithDifferentMinValues));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3},{4},{5}" + "+1")
            @DisplayName("Long filters with different max values")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentMaxValues(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentMaxValues = new LongFilter(id, name, description, productAttribute, min, max + 1);
                Assertions.assertFalse(longFilter.equals(longFilterWithDifferentMaxValues));
            }

            @ParameterizedTest(name = "Time filter: {0},{1},{2},{3},{4},{5}")
            @DisplayName("Long filters with different filter types: Double filter")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentFilterTypesDoubleFilter(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentType = new DoubleFilter(id, name, description, productAttribute, (double) min, (double) max);
                Assertions.assertFalse(longFilter.equals(doubleFilterWithDifferentType));
            }

            @ParameterizedTest(name = "Time filter: {0},{1},{2},{3},{4},{5}")
            @DisplayName("Long filters with different filter types: Time filter")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentFilterTypesTimeFilter(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentType = new TimeFilter(id, name, description, productAttribute, Instant.ofEpochSecond(min), Instant.ofEpochSecond(max));
                Assertions.assertFalse(longFilter.equals(timeFilterWithDifferentType));
            }

            @ParameterizedTest(name = "Different user min: {0},{1},{2},{3},{4},{5}")
            @DisplayName("Long filters with different UserMin values")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentUserMinValues(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentUserMin = new LongFilter(id, name, description, productAttribute, min, max);
                longFilterWithDifferentUserMin.setUserMin(1234567890);
                Assertions.assertFalse(longFilter.equals(longFilterWithDifferentUserMin));
            }

            @ParameterizedTest(name = "Different user max: {0},{1},{2},{3},{4},{5} ")
            @DisplayName("Long filters with different UserMax values")
            @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
            void longFiltersWithDifferentUserMaxValues(int id, String name, String description, String productAttribute, long min, long max) {
                LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
                LongFilter longFilterWithDifferentUserMax = new LongFilter(id, name, description, productAttribute, min, max);
                longFilterWithDifferentUserMax.setUserMax(1234567890);
                Assertions.assertFalse(longFilter.equals(longFilterWithDifferentUserMax));
            }


        }

        @Nested
        @DisplayName("Non-matching time filter test")
        class NonMatchingTimeFilterTests {
            @ParameterizedTest
            @DisplayName("Time filter and ordinary object")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFilterAndObject(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                Object o = new Object();
                Assertions.assertFalse(timeFilter.equals(o));
            }

            @ParameterizedTest(name = "{0}" + "+1" + ",{1},{2},{3},{4},{5}")
            @DisplayName("Time filters with different ids")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentIds(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentIds = new TimeFilter(id + 1, name, description, productAttribute, min, max);
                Assertions.assertFalse(timeFilter.equals(timeFilterWithDifferentIds));
            }

            @ParameterizedTest(name = "{0},{1}" + "diff" + ",{2},{3},{4},{5}")
            @DisplayName("Time filters with different names")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentNames(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentNames = new TimeFilter(id, name + "diff", description, productAttribute, min, max);
                Assertions.assertFalse(timeFilter.equals(timeFilterWithDifferentNames));
            }

            @ParameterizedTest(name = "{0},{1},{2}" + "diff" + ",{3},{4},{5}")
            @DisplayName("Time filters with different descriptions")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentDescriptions(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentDescriptions = new TimeFilter(id, name, description + "diff", productAttribute, min, max);
                Assertions.assertFalse(timeFilter.equals(timeFilterWithDifferentDescriptions));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3}" + "diff" + ",{4},{5}")
            @DisplayName("Time filters with different product attributes")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentProductAttributes(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentProductAttributes = new TimeFilter(id, name, description, productAttribute + "diff", min, max);
                Assertions.assertFalse(timeFilter.equals(timeFilterWithDifferentProductAttributes));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3},{4},{5}")
            @DisplayName("Time filters with different min values")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentMinValues(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentMinValues = new TimeFilter(id, name, description, productAttribute, Instant.MIN, max);
                Assertions.assertFalse(timeFilter.equals(timeFilterWithDifferentMinValues));
            }

            @ParameterizedTest(name = "{0},{1},{2},{3},{4},{5}")
            @DisplayName("Time filters with different max values")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentMaxValues(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentMaxValues = new TimeFilter(id, name, description, productAttribute, min, Instant.MAX);
                Assertions.assertFalse(timeFilter.equals(timeFilterWithDifferentMaxValues));
            }

            @ParameterizedTest(name = "Double filter: {0},{1},{2},{3}, 0.1, 99.9")
            @DisplayName("Time filters with different filter types: Double filter")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentFilterTypesDoubleFilter(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentType = new DoubleFilter(id, name, description, productAttribute, 0.1, 99.9);
                Assertions.assertFalse(timeFilter.equals(doubleFilterWithDifferentType));
            }

            @ParameterizedTest(name = "Double filter: {0},{1},{2},{3}, 1, 100")
            @DisplayName("Time filters with different filter types: Long filter")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentFilterTypesLongFilter(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                DoubleFilter doubleFilterWithDifferentType = new DoubleFilter(id, name, description, productAttribute, 1, 100);
                Assertions.assertFalse(timeFilter.equals(doubleFilterWithDifferentType));
            }

            @ParameterizedTest(name = "Different user min: {0},{1},{2},{3},{4},{5}")
            @DisplayName("Time filters with different UserMin values")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentUserMinValues(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentUserMin = new TimeFilter(id, name, description, productAttribute, min, max);
                timeFilterWithDifferentUserMin.setUserMin(Instant.MIN);
                Assertions.assertFalse(timeFilter.equals(timeFilterWithDifferentUserMin));
            }

            @ParameterizedTest(name = "Different user max: {0},{1},{2},{3},{4},{5} ")
            @DisplayName("Time filters with different UserMax values")
            @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
            void timeFiltersWithDifferentUserMaxValues(int id, String name, String description, String productAttribute, Instant min, Instant max) {
                TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
                TimeFilter timeFilterWithDifferentUserMax = new TimeFilter(id, name, description, productAttribute, min, max);
                timeFilterWithDifferentUserMax.setUserMax(Instant.MAX);
                Assertions.assertFalse(timeFilter.equals(timeFilterWithDifferentUserMax));
            }

        }


    }


    @Nested
    @DisplayName("Matching tests")
    class MatchingTests {

        @ParameterizedTest
        @DisplayName("Test matching Double filters")
        @CsvFileSource(resources = "DoubleFilter.csv", numLinesToSkip = 1)
        void testMatchingDoubleFilters(int id, String name, String description, String productAttribute, double min, double max) {
            DoubleFilter doubleFilter = new DoubleFilter(id, name, description, productAttribute, min, max);
            DoubleFilter doubleFilterduplicate = new DoubleFilter(id, name, description, productAttribute, min, max);
            Assertions.assertTrue(doubleFilter.equals(doubleFilterduplicate));
        }

        @ParameterizedTest
        @DisplayName("Test matching Long filters")
        @CsvFileSource(resources = "LongFilter.csv", numLinesToSkip = 1)
        void testMatchingLongFilters(int id, String name, String description, String productAttribute, Long min, Long max) {
            LongFilter longFilter = new LongFilter(id, name, description, productAttribute, min, max);
            LongFilter longFilterduplicate = new LongFilter(id, name, description, productAttribute, min, max);
            Assertions.assertTrue(longFilter.equals(longFilterduplicate));
        }

        @ParameterizedTest
        @DisplayName("Test matching Time filters")
        @CsvFileSource(resources = "TimeFilter.csv", numLinesToSkip = 1)
        void testMatchingTimeFilters(int id, String name, String description, String productAttribute, Instant min, Instant max) {
            TimeFilter timeFilter = new TimeFilter(id, name, description, productAttribute, min, max);
            TimeFilter timeFilterduplicate = new TimeFilter(id, name, description, productAttribute, min, max);
            Assertions.assertTrue(timeFilter.equals(timeFilterduplicate));
        }


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

