package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalMinMaxException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LongFilterTest {

    /**
     * The returned filter has a min of 12345673 and a max of 12345675. <br>
     * It looks at the attribute: ean
     */
    static LongFilter getTestFilter() {
        return new LongFilter(0, "test name", "test description", "ean", 12345673, 12345675);
    }

    @Test
    void getType() {
        LongFilter longFilter = getTestFilter();
        assertEquals(FilterTypes.LONG, longFilter.getType());
    }

    @Test
    @Disabled("test not yet written")
    void testEquals() {
        fail("test not yet written");
    }

    @Nested
    @DisplayName("Use filter method")
    class useFilterMethod {
        //The filter method should be tested such that it will only filter out the results that do not match.
        //
        //It must also be able to take an empty list.

        @Disabled("Test does not make sense to make since LongFilters can only have 1 product attribute(ean)")
        @Test
        @DisplayName("Changing product attribute actually changes attribute used for filtering")
        void changingProductAttributeActuallyChangesBeingFiltered() {
        }

        @DisplayName("filter a list of actual products")
        @ParameterizedTest(name = "{0}")
        @MethodSource("useFilterArguments")
        void useFilter(LongFilter internalFilter) {
            // Not necessary, but should be tested in a separate test.
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("MockResults.csv", true);

            //crude check that the mockresults are what we expect, and have not been changed
            assertEquals(7, mockResults.size());


            List<Product> copy = List.copyOf(mockResults);
            ArrayList<Product> expectedResults = new ArrayList<>(copy);
            expectedResults.remove(3);
            expectedResults.remove(2);
            expectedResults.remove(1);
            expectedResults.remove(0);
            // equivalent to repeating expectedresults.remove(0) 4 times
            // This has been done to improve readability

            Collection<Product> filteredResults = internalFilter.useFilter(mockResults);

            assertEquals(expectedResults, filteredResults,  () -> {
                StringBuilder out = new StringBuilder("Expected results: length=" + expectedResults.size() + "\n");
                for (Product product: expectedResults){
                    out.append(product).append("\n");
                }

                out.append("\nActual results: length=").append(filteredResults.size()).append("\n");
                for (Product product: filteredResults){
                    out.append(product).append("\n");
                }

                return out.toString();
            });
        }

        static Stream<Arguments> useFilterArguments() {
            // Could be refactored to simply return DoubleFilters.
            // However it is like this such that the test can easily be refactored to take more arguments if needed.

            // The values chosen ensure that if something is off, and a value is used when filtering, when it is not supposed to
            // then the test will fail
            List<Arguments> out = new ArrayList<>();

            // create the combination of desired inputs

            // Add filters for purely using db settings
            out.add(arguments(getTestFilter()));


            // Add filters using a single user setting set
            // The value chosen excludes a filter more than the db settings
            // Thus if it gets applied the test fails
            long userMin = 12345674;
            long userMax = 12345674;

            LongFilter filter = getTestFilter();
            try {
                filter.setUserMax(userMax);
            } catch (IllegalMinMaxException e) {
                e.printStackTrace();
                fail("Failed to set userMax " + userMax);
            }
            out.add(arguments(filter));

            filter = getTestFilter();
            try {
                filter.setUserMin(userMin);
            } catch (IllegalMinMaxException e) {
                e.printStackTrace();
                fail("Failed to set userMin " + userMin);
            }
            out.add(arguments(filter));

            // Add filters that set both usermin and max, in such a way that the list is different than simply dbmin and max
            // The values here are the same as the default dbvalues of the filter
            userMin = 12345673;
            userMax = 12345675;

            long dbMin = 0;
            long dbMax = 12345678;
            filter = new LongFilter(0, "test name", "test description", "ean", dbMin, dbMax);
            try {
                filter.setUserMin(userMin);
                filter.setUserMax(userMax);
            } catch (IllegalMinMaxException e) {
                e.printStackTrace();
                fail("Failed to set userMin " + userMin + " and userMax " + userMax);
            }
            out.add(arguments(filter));


            return out.stream();
        }

        @DisplayName("Filtering an empty list of results")
        @ParameterizedTest(name = "setUserMinAndMax={1} - inputList={2}")
        @MethodSource("filteringAnEmptyListOfResultsArgument")
        void filteringAnEmptyListOfResults(boolean setUserMinAndMax, Collection<Product> inputList) {
            LongFilter internalFilter = new LongFilter(0, "test name", "test description", "ean", 0, 1000);
            if(setUserMinAndMax){
                try {
                    internalFilter.setUserMax(100);
                    internalFilter.setUserMin(10);
                } catch (IllegalMinMaxException e) {
                    e.printStackTrace();
                    fail("Failed to set userMin " + 10 + " and userMax " + 100);
                }
            }

            // preparing the expected result
            // Note: we create a new variable that doesn't reference inputlist since it might be modified during the action
            Collection<Product> expectedResult;

            if(inputList == null){
                expectedResult = null;
            }else {
                expectedResult = new ArrayList<>();
            }

            Collection<Product> filteredResults = internalFilter.useFilter(inputList);

            assertEquals(expectedResult, filteredResults);
        }

        @Nested
        @DisplayName("Set userMin and userMax")
        class SetUserMinAndMax {

            static Stream<LongFilter> provideLongFiltersProvider (){
                return Stream.of(
                        new LongFilter("Long filter 1", "Desc long filter 1", "price", 0, 1000),
                        new LongFilter("Long filter 2", "Desc long filter 2", "price", 0, 1)
                );
            }

            @Nested
            @DisplayName("Set valid userMin and userMax")
            class SetValidUserMinAndUserMax {

                static Stream<LongFilter> provideLongFilters(){
                    return provideLongFiltersProvider();
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Set valid DoubleFilter userMin")
                @MethodSource("provideLongFilters")
                void setValidLongFilterUserMin (LongFilter filter) {
                    Assertions.assertDoesNotThrow(
                            () -> filter.setUserMin(10));
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Set valid LongFilter userMax")
                @MethodSource("provideLongFilters")
                void setValidLongFilterUserMax (LongFilter filter) {
                    long newUserMax = filter.getDbMaxLong() - 1;
                    Assertions.assertDoesNotThrow(
                            () -> filter.setUserMax(newUserMax));
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("userMin has changed")
                @MethodSource("provideLongFilters")
                void userMinHasChanged (LongFilter filter) throws IllegalMinMaxException {
                    long newMin = 10;
                    filter.setUserMin(newMin);
                    Assertions.assertEquals(newMin, filter.getUserMinLong());
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("userMax has changed")
                @MethodSource("provideLongFilters")
                void userMaxHasChanged (LongFilter filter) throws IllegalMinMaxException {
                    long newMax = filter.getDbMaxLong() -1;
                    filter.setUserMax(newMax);
                    Assertions.assertEquals(newMax, filter.getUserMaxLong());
                }
            }

            @Nested
            @DisplayName("Set invalid userMin and userMax")
            class SetInvalidUserMinAndUserMax {

                static Stream<LongFilter> provideLongFilters(){
                    return provideLongFiltersProvider();
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Set invalid LongFilter userMin less than dbMin")
                @MethodSource("provideLongFilters")
                void setInvalidLongFilterUserMin (LongFilter filter) {
                    long newValue = filter.getDbMinLong() - 1;
                    Assertions.assertThrows(IllegalMinMaxException.class,
                            () -> filter.setUserMin(newValue));
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Set invalid LongFilter userMax less than dbMin")
                @MethodSource("provideLongFilters")
                void setInvalidLongFilterUserMax (LongFilter filter) {
                    long newValue = filter.getDbMinLong() - 1;
                    Assertions.assertThrows(IllegalMinMaxException.class,
                            () -> filter.setUserMax(newValue)
                    );
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Set invalid LongFilter userMin higher than dbMax")
                @MethodSource("provideLongFilters")
                void setInvalid (LongFilter filter) {
                    long newValue = filter.getDbMaxLong() + 1;
                    Assertions.assertThrows(IllegalMinMaxException.class,
                            () -> filter.setUserMin(newValue));
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Set invalid LongFilter userMax higher than dbMax")
                @MethodSource("provideLongFilters")
                void setInvalidLongFilterUserMaxHigherThanDBMax (LongFilter filter) {
                    long newValue = filter.getDbMaxLong() + 1;
                    Assertions.assertThrows(IllegalMinMaxException.class,
                            () -> filter.setUserMax(newValue)
                    );
                }


                @ParameterizedTest(name = "{0}")
                @DisplayName("Set invalid LongFilter userMax less than userMin")
                @MethodSource("provideLongFilters")
                void setInvalidLessThanUserMaxLessThanUserMin (LongFilter filter) throws IllegalMinMaxException {
                    long newMin = filter.getDbMaxLong() - 10;
                    long newMax = filter.getDbMaxLong() - 20;

                    filter.setUserMin(newMin);
                    Assertions.assertThrows(IllegalMinMaxException.class,
                            () -> filter.setUserMax(newMax));
                }

                @ParameterizedTest(name = "{0}")
                @DisplayName("Set invalid LongFilter userMin higher than userMax")
                @MethodSource("provideLongFilters")
                void setInvalidUserMinHigherThanUserMax (LongFilter filter) throws IllegalMinMaxException {
                    long newMax = filter.getDbMaxLong() - 20;
                    long newMin = filter.getDbMaxLong() - 10;

                    filter.setUserMin(newMin);
                    Assertions.assertThrows(IllegalMinMaxException.class,
                            () -> filter.setUserMax(newMax));
                }
            }


        }

        static Stream<Arguments> filteringAnEmptyListOfResultsArgument() {
            List<Arguments> out = new ArrayList<>();

            // create the combination of desired inputs
            // The order of the for loops determine the order of test cases.
            // Currently it will switch productAttribute first, then switch between using user settings or not. Then it will switch to the null list
            for (boolean nullInsteadOfList : new boolean[]{false, true}) {
                for (boolean setUserMinAndMax : new boolean[]{false, true}) {
                    if(nullInsteadOfList){
                        out.add(arguments(setUserMinAndMax, null));
                    }else {
                        out.add(arguments(setUserMinAndMax, new ArrayList<Product>()));
                    }
                }
            }

            return out.stream();
        }
    }
}
