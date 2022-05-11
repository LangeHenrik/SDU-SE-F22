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
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TimeFilterTest {

    private TimeFilter getFilter(String productAttribute) {
        Instant dbMin = Instant.parse("2018-11-30T15:35:24.00Z");
        Instant dbMax = Instant.parse("2022-11-30T15:35:24.00Z");
        return new TimeFilter(0, "test name", "test description", productAttribute, dbMin, dbMax);
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"publishedDate", "expirationDate"})
    void getType(String productAttribute) {
        TimeFilter timeFilter = getFilter(productAttribute);
        assertEquals(FilterTypes.TIME, timeFilter.getType());
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
        // There should be tests to ensure that it works with both db settings (if no or a single user setting is present)
        // Along with working with user settings when both are set.
        // Thus there should be 4 (almost) equivalent tests. 1 with no user settings present. 1 for min and 1 for max being set.
        // Then a final test where both usermin and max is set.
        // Ensure that the userMin and max being set will have a difference in what is filtered out and what isn't
        //
        // This filter is small enough that we should be able to run it for both publishedDate and expirationDate
        // Idea: Have the publishedDate and expirationDate be the same for the input objects. Would allow easy parameterization.
        // Although it brings the challenge that the method in that case could see on only one and still pass.
        // A single test that eliminates that possibility would solve that, and allow for all other tests being much cleaner.
        //

        @DisplayName("filter a list of actual products")
        @ParameterizedTest(name = "{0}")
        @ValueSource(strings = {"publishedDate", "expirationDate"})
        void useFilter(String productAttribute) {
            // preparing the filter
            TimeFilter internalFilter = getFilter(productAttribute);
            Instant userMin = Instant.parse("2019-11-30T15:35:24.00Z");
            Instant userMax = Instant.parse("2021-11-30T15:35:24.00Z");
            // Below comments will be used in a seperate test where we test if it works for the filter to use the settings set by the user
//            internalFilter.setUserMin(userMin);
//            internalFilter.setUserMax(userMax);

            // preparing the input list
            // Note: Should be tested in a separate test.
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("MockResults.csv", true);

            //crude check that the mockresults are what we expect, and have not been changed
            // Note: Not up to the standard of our unit tests
            assertEquals(7, mockResults.size());

            // preparing the expected result list
            List<Product> copy = List.copyOf(mockResults);
            ArrayList<Product> expectedResults = new ArrayList<>(copy);
            expectedResults.remove(6);
            expectedResults.remove(0);
            // Note: remember that remove, moves the index of subsequent elements

            // Executing the action
            Collection<Product> filteredResults = internalFilter.useFilter(mockResults);

            // verifying the result, and formatting it in a readable way in case the test fails
            assertEquals(expectedResults, filteredResults, () -> {
                StringBuilder out = new StringBuilder("Expected results: length=" + expectedResults.size() + "\n");
                for (Product product : expectedResults) {
                    out.append(product).append("\n");
                }

                out.append("\nActual results: length=").append(filteredResults.size()).append("\n");
                for (Product product : filteredResults) {
                    out.append(product).append("\n");
                }

                return out.toString();
            });
        }
        //It must also be able to take an empty list, along with a null list

        @DisplayName("Filtering an empty list of results")
        @ParameterizedTest(name = "{0} - setUserMinAndMax={1} - inputList={2}")
        @MethodSource("filteringAnEmptyListOfResultsArgument")
        void filteringAnEmptyListOfResults(String productAttribute, boolean setUserMinAndMax, Collection<Product> inputList) {
            // preparing the filter
            TimeFilter internalFilter = getFilter(productAttribute);
            if (setUserMinAndMax) {
                System.out.print("Setting userMin and userMax");
                Instant userMin = Instant.parse("2019-11-30T15:35:24.00Z");
                Instant userMax = Instant.parse("2021-11-30T15:35:24.00Z");
                internalFilter.setUserMin(userMin);
                internalFilter.setUserMax(userMax);
            }

            // Executing the action
            Collection<Product> filteredResults = internalFilter.useFilter(inputList);

            // Verifying the result
            assertEquals(new ArrayList<>(), filteredResults);
            // Currently fails if our method returns null.
            // When the desired behaviour of the method has been decided correct the test as needed
        }

        static Stream<Arguments> filteringAnEmptyListOfResultsArgument() {
            List<Arguments> out = new ArrayList<>();


            // create the combination of desired inputs
            // The order of the for loops determine the order of test cases.
            // Currently it will switch productAttribute first, then switch between using user settings or not. Then it will switch to the null list
            for (boolean nullInsteadOfList : new boolean[]{false, true}) {
                for (boolean setUserMinAndMax : new boolean[]{false, true}) {
                    for (String attribute : new String[]{"publishedDate", "expirationDate"}) {
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
    }
}
