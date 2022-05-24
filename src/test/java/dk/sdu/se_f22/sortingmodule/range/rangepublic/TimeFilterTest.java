package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.exceptions.*;
import org.junit.Assert;
import org.junit.jupiter.api.*;
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

    /**
     *The returned filter has: <br>
     * min: 2018-11-30T15:35:24.00Z<br>
     * max: 2022-11-30T15:35:24.00Z
     *
     * @param productAttribute can be either "publishedDate" or "expirationDate"
     */
    static TimeFilter getTestFilter(String productAttribute) {
        Instant dbMin = Instant.parse("2018-11-30T15:35:24.00Z");
        Instant dbMax = Instant.parse("2022-11-30T15:35:24.00Z");
        return new TimeFilter(0, "test name", "test description", productAttribute, dbMin, dbMax);
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"publishedDate", "expirationDate"})
    void getType(String productAttribute) {
        TimeFilter timeFilter = getTestFilter(productAttribute);
        assertEquals(FilterTypes.TIME, timeFilter.getType());
    }

    @Nested
    @DisplayName("Use filter method")
    class useFilterMethod {
        // Make a test that ensures that changing productAttribute actually changes the attribute being filtered

        @ParameterizedTest
        @DisplayName("Changing product attribute actually changes attribute used for filtering")
        @ValueSource(strings = {"publishedDate", "expirationDate"})
        void changingProductAttributeActuallyChangesBeingFiltered(String productAttribute) throws IllegalMinMaxException {
            // Creating filter with productattribute publishDate or experationDate
            TimeFilter filter = getTestFilter(productAttribute);
            Instant userMin = Instant.parse("2019-11-30T15:35:24.00Z");
            Instant userMax = Instant.parse("2021-11-30T15:35:24.00Z");

            filter.setUserMin(userMin);
            filter.setUserMax(userMax);

            // Preparing input list
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("rangepublic/ProductsForTimeFilterTest.csv", true);

            // Preparing the expected result list
            // Removes index one in the first if statement,
            // because the productattribute is not inside the usermin and usermax range
            List<Product> copy = List.copyOf(mockResults);
            ArrayList<Product> expectedResults = new ArrayList<>(copy);
            if(productAttribute.equals("publishedDate")) {
                expectedResults.remove(1);
            }
            if(productAttribute.equals("expirationDate")) {
                expectedResults.remove(0);
            }

            Collection<Product> filteredResults = filter.useFilter(mockResults);

            Assertions.assertEquals(expectedResults,filteredResults);
        }


        @DisplayName("filter a list of actual products")
        @ParameterizedTest(name = "{0}")
        @MethodSource("useFilterArguments")
    void useFilter(TimeFilter internalFilter) {
            // preparing the input list
            // Note: Helpers.readMockProductResultsFromFile Should be tested in a separate test.
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("MockResults.csv", true);

            //crude check that the mockresults are what we expect, and have not been changed
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

        static Stream<Arguments> useFilterArguments() throws RangeFilterException, IllegalMinMaxException {
            // Could be refactored to simply return TimeFilters.
            // However it is like this such that the test can easily be refactored to take more arguments if needed.

            // The timestamps for the instants have been carefully chosen to ensure that they align with the products.
            // The values chosen ensure that if something is off, and a value is used when filtering, when it is not supposed to
            // then the test will fail
            List<Arguments> out = new ArrayList<>();

            // create the combination of desired inputs
            String[] strings = {"publishedDate", "expirationDate"};

            // Add filters for purely using db settings
            for(String attribute: strings){
                out.add(arguments(getTestFilter(attribute)));
            }

            // Add filters using a single user setting set
            Instant userMin = Instant.parse("2019-11-30T15:35:24.00Z");
            Instant userMax = Instant.parse("2021-11-30T15:35:24.00Z");
            for(String attribute: strings){
                TimeFilter filter = getTestFilter(attribute);

                filter.setUserMax(userMax);

                out.add(arguments(filter));

                filter = getTestFilter(attribute);

                filter.setUserMin(userMin);

                out.add(arguments(filter));
            }

            // Add filters that set both usermin and max, in such a way that the list is different than simply dbmin and max
            userMin = Instant.parse("2018-11-30T15:35:24.00Z");
            userMax = Instant.parse("2022-11-30T15:35:24.00Z");
            for(String attribute: strings){
                Instant dbMin = Instant.parse("2017-11-30T15:35:24.00Z");
                Instant dbMax = Instant.parse("2023-11-30T15:35:24.00Z");
                TimeFilter filter = new TimeFilter(0, "test name", "test description", attribute, dbMin, dbMax);

                filter.setUserMin(userMin);
                filter.setUserMax(userMax);

                out.add(arguments(filter));
            }


            return out.stream();
        }

        //It must also be able to take an empty list, along with a null list

        @DisplayName("Filtering an empty list of results")
        @ParameterizedTest(name = "{0} - setUserMinAndMax={1} - inputList={2}")
        @MethodSource("filteringAnEmptyListOfResultsArgument")
        void filteringAnEmptyListOfResults(String productAttribute, boolean setUserMinAndMax, Collection<Product> inputList) throws RangeFilterException, IllegalMinMaxException {
            // preparing the filter
            TimeFilter internalFilter = getTestFilter(productAttribute);
            if (setUserMinAndMax) {
                System.out.print("Setting userMin and userMax");
                Instant userMin = Instant.parse("2019-11-30T15:35:24.00Z");
                Instant userMax = Instant.parse("2021-11-30T15:35:24.00Z");
                internalFilter.setUserMin(userMin);
                internalFilter.setUserMax(userMax);
            }

            // preparing the expected result
            // Note: we create a new variable that doesn't reference inputlist since it might be modified during the action
            Collection<Product> expectedResult;

            if(inputList == null){
                expectedResult = null;
            }else {
                expectedResult = new ArrayList<>();
            }

            // Executing the action
            Collection<Product> filteredResults = internalFilter.useFilter(inputList);

            // Verifying the result
            assertEquals(expectedResult, filteredResults);
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
    @Nested
    @DisplayName("Set userMin and userMax")
    class SetUserMinAndMax {

        static Stream<TimeFilter> provideTimeFiltersProvider (){
            return Stream.of(
                    new TimeFilter("Time filter 1", "Desc time filter 1", "publish_date", Instant.parse("2022-05-06T18:35:24.00Z"), Instant.parse("2022-05-20T18:35:24.00Z"))
            );
        }

        @Nested
        @DisplayName("Set valid userMin and userMax")
        class SetValidUserMinAndUserMax {

            static Stream<TimeFilter> provideTimeFilters(){
                return provideTimeFiltersProvider();
            }

            @ParameterizedTest(name = "{0}")
            @DisplayName("Set valid TimeFilter userMin")
            @MethodSource("provideTimeFilters")
            void setValidTimeFilterUserMin (TimeFilter filter) {
                String newDate = "2022-05-10T18:35:24.00Z";
                Assertions.assertDoesNotThrow(
                        () -> filter.setUserMin(Instant.parse(newDate))
                );
            }

            @ParameterizedTest(name = "{0}")
            @DisplayName("userMin changed after being set")
            @MethodSource("provideTimeFilters")
            void userMinChangedAfterBeingSet (TimeFilter filter) throws IllegalMinMaxException {
                Instant newDate = filter.getDbMinInstant().plusSeconds(1);
                filter.setUserMin(newDate);
                Assertions.assertEquals(newDate, filter.getUserMinInstant());
            }

            @ParameterizedTest(name = "{0}")
            @DisplayName("Set valid TimeFilter userMax")
            @MethodSource("provideTimeFilters")
            void setValidTimeFilterUserMax (TimeFilter filter) {
                Instant newUserMax = filter.getDbMaxInstant().minusSeconds(1);
                Assertions.assertDoesNotThrow(
                        ()-> filter.setUserMax(newUserMax)
                );
            }
            @ParameterizedTest(name = "{0}")
            @DisplayName("userMax changed after being set")
            @MethodSource("provideTimeFilters")
            void userMaxChangedAfterBeingSet (TimeFilter filter) throws IllegalMinMaxException {
                Instant newDate = filter.getDbMaxInstant().minusSeconds(1);
                filter.setUserMax(newDate);
                Assertions.assertEquals(newDate, filter.getUserMaxInstant());
            }
        }

        @Nested
        @DisplayName("Set invalid userMin and userMax")
        class SetInvalidUserMinAndUserMax {

            static Stream<TimeFilter> provideTimeFilters(){
                return provideTimeFiltersProvider();
            }

            @ParameterizedTest(name = "{0}")
            @DisplayName("Set invalid TimeFilter userMin less than dbMin")
            @MethodSource("provideTimeFilters")
            void setInvalidTimeFilterUserMin (TimeFilter filter) {
                Instant newUserMin = Instant.ofEpochMilli(filter.getDbMinInstant().toEpochMilli()- 10);
                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> filter.setUserMin(newUserMin)
                );
            }

            @ParameterizedTest(name = "{0}")
            @DisplayName("Set invalid TimeFilter userMax less than dbMin")
            @MethodSource("provideTimeFilters")
            void setInvalidTimeFilterUserMax (TimeFilter filter) {
                Instant newUserMax = Instant.ofEpochMilli(filter.getDbMinInstant().toEpochMilli()- 10);
                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> filter.setUserMax(newUserMax)
                );
            }

            @ParameterizedTest(name = "{0}")
            @DisplayName("Set invalid TimeFilter userMin higher than dbMin")
            @MethodSource("provideTimeFilters")
            void setInvalidTimeFilterUserMinHigherThanDBMin (TimeFilter filter) {
                Instant newUserMin = Instant.ofEpochMilli(filter.getDbMaxInstant().toEpochMilli() + 10);
                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> filter.setUserMin(newUserMin)
                );
            }

            @ParameterizedTest(name = "{0}")
            @DisplayName("Set invalid TimeFilter userMax higher than dbMin")
            @MethodSource("provideTimeFilters")
            void setInvalidTimeFilterUserMaxHigherThanDBMin (TimeFilter filter) {
                Instant newUserMax = Instant.ofEpochMilli(filter.getDbMaxInstant().toEpochMilli() + 10);
                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> filter.setUserMax(newUserMax)
                );
            }

            @ParameterizedTest(name = "{0}")
            @DisplayName("Set invalid TimeFilter userMax less than userMin")
            @MethodSource("provideTimeFilters")
            void setInvalidLessThanUserMaxLessThanUserMin (TimeFilter filter) throws IllegalMinMaxException {
                Instant newUserMin = Instant.ofEpochMilli(filter.getDbMaxInstant().toEpochMilli()- 10);
                Instant newUserMax = Instant.ofEpochMilli(filter.getDbMaxInstant().toEpochMilli()- 15);

                filter.setUserMin(newUserMin);

                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> filter.setUserMax(newUserMax)
                );
            }


            @ParameterizedTest(name = "{0}")
            @DisplayName("Set invalid TimeFilter userMin higher than userMax")
            @MethodSource("provideTimeFilters")
            void setInvalidUserMinHigherThanUserMax (TimeFilter filter) throws IllegalMinMaxException {
                Instant newUserMin = Instant.ofEpochMilli(filter.getDbMaxInstant().toEpochMilli()- 10);
                Instant newUserMax = Instant.ofEpochMilli(filter.getDbMaxInstant().toEpochMilli()- 100);

                filter.setUserMin(newUserMin);

                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> filter.setUserMax(newUserMax)
                );
            }


        }

    }

}
