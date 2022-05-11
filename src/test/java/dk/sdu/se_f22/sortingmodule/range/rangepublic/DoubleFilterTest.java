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

class DoubleFilterTest {
    static DoubleFilter getTestFilter(String productAttribute) {
        return new DoubleFilter(0, "test name", "test description", productAttribute, 0.0, 1000.0);
    }

    @Test
    void getType() {
        DoubleFilter doubleFilter = getTestFilter("price");
        assertEquals(FilterTypes.DOUBLE, doubleFilter.getType());
    }

    @Test
    @Disabled("test not yet written")
    void testEquals() {
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
        void useFilter(DoubleFilter internalFilter) {
            // The reason for the test fail is known, go to the comment starting with HERE
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("MockResults.csv", true);
            // the last 3 attributes of the product (size, clockspeed and weight) should be set to "unavailable" if they are not applicable

            //crude check that the mockresults are what we expect, and have not been changed
            assertEquals(7, mockResults.size());

            // HERE:
            // Expected results could be read from a csv file,
            // instead of being put in like this.

            // If it is to avoid accidental bricking of the test, then the other settings (max, and min)
            // could also be saved in a (separate) csv file

            List<Product> copy = List.copyOf(mockResults);
            ArrayList<Product> expectedResults = new ArrayList<>(copy);
            // Remember that the list indexes change when items are removed
            expectedResults.remove(6);
            expectedResults.remove(3);

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
            String[] strings = {"price", "weight"};

            // Add filters for purely using db settings
            for(String attribute: strings){
                out.add(arguments(getTestFilter(attribute)));
            }

            // Add filters using a single user setting set
            double userMin = 10.0;
            double userMax = 10.0;
            for(String attribute: strings){
                DoubleFilter filter = getTestFilter(attribute);
                filter.setUserMax(userMax);
                out.add(arguments(filter));

                filter = getTestFilter(attribute);
                filter.setUserMin(userMin);
                out.add(arguments(filter));
            }

            // Add filters that set both usermin and max, in such a way that the list is different than simply dbmin and max
            userMin = 0.0;
            userMax = 1000.0;
            for(String attribute: strings){
                double dbMin = -100.0;
                double dbMax = 10000.0;
                DoubleFilter filter = new DoubleFilter(0, "test name", "test description", attribute, dbMin, dbMax);
                filter.setUserMin(userMin);
                filter.setUserMax(userMax);
                out.add(arguments(filter));
            }


            return out.stream();
        }

        @DisplayName("Filtering an empty list of results")
        @ParameterizedTest(name = "{0} - setUserMinAndMax={1} - inputList={2}")
        @MethodSource("filteringAnEmptyListOfResultsArgument")
        void filteringAnEmptyListOfResults(String productAttribute, boolean setUserMinAndMax, Collection<Product> inputList) {
            DoubleFilter internalFilter = new DoubleFilter(0, "test name", "test description", productAttribute, 0, 1000);
            if(setUserMinAndMax){
                internalFilter.setUserMax(100.0);
                internalFilter.setUserMin(10.0);
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
                    for (String attribute : new String[]{"price", "weight"}) {
                        if(nullInsteadOfList){
                            out.add(arguments(attribute, setUserMinAndMax, null));
                        }else {
                            out.add(arguments(attribute, setUserMinAndMax, new ArrayList<Product>()));
                        }
                    }
                }
            }

            return out.stream();
        }

        @Nested
        @DisplayName("Set userMin and userMax")
        @Disabled("Not yet implemented")
        class SetUserMinAndMax {

            @Nested
            @DisplayName("Set valid userMin and userMax")
            class SetValidUserMinAndUserMax {

                @Test
                @DisplayName("Set valid DoubleFilter userMin and userMax")
                void setValidDoubleFilterUserMinAndUserMax () {

                }

                @Test
                @DisplayName("Set valid LongFilter userMin and userMax")
                void setValidLongFilterUserMinAndUserMax () {

                }

                @Test
                @DisplayName("Set valid TimeFilter userMin and userMax")
                void setValidTimeFilterUserMinAndUserMax () {

                }
            }

            @Nested
            @DisplayName("Set invalid userMin and userMax")
            @Disabled("Not yet implemented")
            class SetInvalidUserMinAndUserMax {

                @Test
                @DisplayName("Set invalid DoubleFilter userMin and userMax")
                void setInvalidDoubleFilterUserMinAndUserMax () {

                }

                @Test
                @DisplayName("Set invalid LongFilter userMin and userMax")
                void setInvalidLongFilterUserMinAndUserMax () {

                }

                @Test
                @DisplayName("Set invalid TimeFilter userMin and userMax")
                void setInvalidTimeFilterUserMinAndUserMax () {

                }
            }
        }
    }


    //todo test setters
}