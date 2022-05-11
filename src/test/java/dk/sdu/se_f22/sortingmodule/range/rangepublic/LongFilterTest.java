package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
            filter.setUserMax(userMax);
            out.add(arguments(filter));

            filter = getTestFilter();
            filter.setUserMin(userMin);
            out.add(arguments(filter));

            // Add filters that set both usermin and max, in such a way that the list is different than simply dbmin and max
            // The values here are the same as the default dbvalues of the filter
            userMin = 12345673;
            userMax = 12345675;

            long dbMin = 0;
            long dbMax = 12345678;
            filter = new LongFilter(0, "test name", "test description", "ean", dbMin, dbMax);
            filter.setUserMin(userMin);
            filter.setUserMax(userMax);
            out.add(arguments(filter));


            return out.stream();
        }

        @DisplayName("Filtering an empty list of results")
        @ParameterizedTest(name = "setUserMinAndMax={1} - inputList={2}")
        @MethodSource("filteringAnEmptyListOfResultsArgument")
        void filteringAnEmptyListOfResults(boolean setUserMinAndMax, Collection<Product> inputList) {
            LongFilter internalFilter = new LongFilter(0, "test name", "test description", "ean", 0, 1000);
            if(setUserMinAndMax){
                internalFilter.setUserMax(100);
                internalFilter.setUserMin(10);
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
