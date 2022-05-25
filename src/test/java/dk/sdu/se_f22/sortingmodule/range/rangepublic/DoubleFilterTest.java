package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalMinMaxException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.RangeFilterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DoubleFilterTest {
    /**
     * The returned DoubleFilter has a min of 0 and a max of 1000
     */
    static DoubleFilter getTestFilter(String productAttribute) {
        return new DoubleFilter(0, "test name", "test description", productAttribute, 0.0, 1000.0);
    }

    @Test
    @DisplayName("getType returns FilterTypes.Double")
    void getType() {
        DoubleFilter doubleFilter = getTestFilter("price");
        assertEquals(FilterTypes.DOUBLE, doubleFilter.getType());
    }


    @Nested
    @DisplayName("Use filter method")
    class useFilterMethod {
        //The filter method should be tested such that it will only filter out the results that do not match.
        //
        //It must also be able to take an empty list.

        @ParameterizedTest
        @DisplayName("Changing product attribute actually changes attribute used for filtering")
        @ValueSource(strings = {"price", "averageUserReview", "clockspeed", "weight"})
        void changingProductAttributeActuallyChangesBeingFiltered(String productAttribute) throws RangeFilterException {
            //Creating filter through getTestFilter method
            DoubleFilter filter = getTestFilter(productAttribute);
            double userMin = 10.0;
            double userMax = 100.0;

            filter.setUserMin(userMin);
            filter.setUserMax(userMax);


            //Preparing input list
            // Note: Helpers.readMockProductResultsFromFile is expected to be tested in a separate test.
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("rangepublic/ProductsForDoubleFilterTest.csv", true);

            // preparing the expected result list
            // Inserting the product, that is within the range of usermin and usermax
            ArrayList<Product> expectedResults = new ArrayList<>();
            if (productAttribute.equals("price")) {
                expectedResults.add(mockResults.get(0));
            }
            if (productAttribute.equals("averageUserReview")) {
                expectedResults.add(mockResults.get(1));
            }
            if (productAttribute.equals("clockspeed")) {
                expectedResults.add(mockResults.get(2));
            }
            if (productAttribute.equals("weight")) {
                expectedResults.add(mockResults.get(3));
            }

            Collection<Product> filteredResults = filter.useFilter(mockResults);

            Assertions.assertEquals(expectedResults, filteredResults);
        }

        @DisplayName("filter a list of actual products")
        @ParameterizedTest(name = "{0}")
        @MethodSource("useFilterArguments")
        void useFilter(DoubleFilter internalFilter) {
            // Note: Helpers.readMockProductResultsFromFile is expected to be tested in a separate test.
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("MockResults.csv", true);
            // the last 3 attributes of the product (size, clockspeed and weight) should be set to "unavailable" if they are not applicable

            //crude check that the mockresults are what we expect, and have not been changed
            assertEquals(7, mockResults.size());

            List<Product> copy = List.copyOf(mockResults);
            ArrayList<Product> expectedResults = new ArrayList<>(copy);
            // Remember that the list indexes change when items are removed
            expectedResults.remove(6);
            expectedResults.remove(3);

            Collection<Product> filteredResults = internalFilter.useFilter(mockResults);

            assertEquals(expectedResults, filteredResults, () -> {
                String initialInfo = "\n" + internalFilter.getProductAttribute() + "\n";
                StringBuilder out = new StringBuilder(initialInfo + "Expected results: length=" + expectedResults.size() + "\n");
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

        static Stream<Arguments> useFilterArguments() throws IllegalMinMaxException {
            // Could be refactored to simply return DoubleFilters.
            // However it is like this such that the test can easily be refactored to take more arguments if needed.

            // The values chosen ensure that if something is off, and a value is used when filtering, when it is not supposed to
            // then the test will fail
            List<Arguments> out = new ArrayList<>();

            // create the combination of desired inputs
            String[] strings = {"price", "weight"};

            // Add filters for purely using db settings
            for (String attribute : strings) {
                out.add(arguments(getTestFilter(attribute)));
            }

            // Add filters using a single user setting set
            double userMin = 10.0;
            double userMax = 10.0;
            for (String attribute : strings) {
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
            for (String attribute : strings) {
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
        void filteringAnEmptyListOfResults(String productAttribute, boolean setUserMinAndMax, Collection<Product> inputList) throws IllegalMinMaxException {
            DoubleFilter internalFilter = new DoubleFilter(0, "test name", "test description", productAttribute, 0, 1000);
            if (setUserMinAndMax) {
                internalFilter.setUserMax(100.0);
                internalFilter.setUserMin(10.0);
            }

            // preparing the expected result
            // Note: we create a new variable that doesn't reference inputlist since it might be modified during the action
            Collection<Product> expectedResult;

            if (inputList == null) {
                expectedResult = null;
            } else {
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
                        if (nullInsteadOfList) {
                            out.add(arguments(attribute, setUserMinAndMax, null));
                        } else {
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

        static Stream<DoubleFilter> provideDoubleFiltersProvider() {
            return Stream.of(
                    new DoubleFilter("Double filter 1", "Desc double filter 1", "price", 0f, 1000f),
                    new DoubleFilter("Double filter 2", "Desc double filter 2", "price", 0f, 30f)
            );
        }

        @Nested
        @DisplayName("Set valid userMin and userMax")
        class SetValidUserMinAndUserMax {

            static Stream<DoubleFilter> doubleFilterProvider() {
                return provideDoubleFiltersProvider();
            }

            @ParameterizedTest
            @MethodSource("doubleFilterProvider")
            @DisplayName("Set DoubleFilter userMin does not throw")
            void setValidDoubleFilterUserMin(DoubleFilter filter) {
                double newValue = filter.getUserMinDouble() + 1;
                Assertions.assertDoesNotThrow(() -> filter.setUserMin(newValue));
            }

            @ParameterizedTest
            @MethodSource("doubleFilterProvider")
            @DisplayName("Set DoubleFilter userMax does not throw")
            void setValidDoubleFilterUserMax(DoubleFilter filter) {
                double newValue = filter.getDbMaxDouble() - 1;
                Assertions.assertDoesNotThrow(() -> filter.setUserMax(newValue));
            }

            @ParameterizedTest
            @MethodSource("doubleFilterProvider")
            @DisplayName("Set DoubleFilter userMax and then userMin does not throw")
            void setValidDoubleFilterUserMaxAndThenUserMin(DoubleFilter filter) {
                double inputMin = filter.getDbMinDouble() + 1;
                double inputMax = filter.getDbMaxDouble() - 1;

                Assertions.assertDoesNotThrow(
                        () -> filter.setUserMax(inputMax)
                );

                Assertions.assertDoesNotThrow(
                        () -> filter.setUserMin(inputMin));
            }

            @ParameterizedTest
            @MethodSource("doubleFilterProvider")
            @DisplayName("Set DoubleFilter userMin and then userMax does not throw")
            void setValidDoubleFilterUserMinAndThenUserMax(DoubleFilter filter) {
                double inputMin = filter.getDbMinDouble() + 1;
                double inputMax = filter.getDbMaxDouble() - 1;
                Assertions.assertDoesNotThrow(
                        () -> filter.setUserMin(inputMin)
                );

                Assertions.assertDoesNotThrow(
                        () -> filter.setUserMax(inputMax)
                );
            }

            @ParameterizedTest
            @MethodSource("doubleFilterProvider")
            @DisplayName("Set userMin and it has changed")
            void userMinHasChanged(DoubleFilter filter) throws IllegalMinMaxException {
                double newMin = filter.getDbMinDouble() + 1f;
                filter.setUserMin(newMin);
                Assertions.assertEquals(filter.getUserMinDouble(), newMin);
            }

            @ParameterizedTest
            @MethodSource("doubleFilterProvider")
            @DisplayName("Set userMax and it has changed")
            void userMaxHasChanged(DoubleFilter filter) throws IllegalMinMaxException {
                double newMax = filter.getDbMaxDouble() - 1f;
                filter.setUserMax(newMax);
                Assertions.assertEquals(filter.getUserMaxDouble(), newMax);
            }

            @ParameterizedTest
            @MethodSource("doubleFilterProvider")
            @DisplayName("Set userMax and it has changed")
            void setUserMinAndUserMaxHasChanged(DoubleFilter filter){
                double newMin = filter.getUserMinDouble() + 1f;
                double newMax = filter.getUserMaxDouble() - 1f;
                Assertions.assertAll("userMin and userMax changed",
                        () -> Assertions.assertEquals(filter.getUserMinDouble(), newMin),
                        () -> Assertions.assertEquals(filter.getUserMaxDouble(), newMax)
                );
            }
        }

        @Nested
        @DisplayName("Set invalid userMin and userMax throws IllegalMinMaxException")
        class SetInvalidUserMinAndUserMax {

            static Stream<DoubleFilter> doubleFilterProvider() {
                return provideDoubleFiltersProvider();
            }

            @ParameterizedTest
            @ValueSource(doubles = {Double.MIN_VALUE, -1.0, 0.0})
            @DisplayName("Set invalid DoubleFilter userMin")
            void setValidDoubleFilterUserMin(double input) {
                DoubleFilter doubleFilter = new DoubleFilter(
                        "Test Name",
                        "Test Discription",
                        "Test product Attribute",
                        1, 1000);

                Assertions.assertThrows(
                        IllegalMinMaxException.class,
                        () -> doubleFilter.setUserMin(input));
            }

            @ParameterizedTest
            @ValueSource(doubles = {1000.1, 10000, Double.MAX_VALUE})
            @DisplayName("Set invalid DoubleFilter userMax")
            void setValidDoubleFilterUserMax(double input) {
                DoubleFilter doubleFilter = new DoubleFilter(
                        "Test Name",
                        "Test Discription",
                        "Test product Attribute",
                        1, 1000);

                Assertions.assertThrows(
                        IllegalMinMaxException.class,
                        () -> doubleFilter.setUserMax(input));
            }

            @ParameterizedTest
            @MethodSource("provideParameters")
            @DisplayName("Set invalid DoubleFilter userMin and then userMax")
            void setValidDoubleFilterUserMinAndThenUserMax(double inputMin, double inputMax) throws IllegalMinMaxException {
                DoubleFilter doubleFilter = new DoubleFilter(
                        "Test Name",
                        "Test Discription",
                        "Test product Attribute",
                        1, 1000);

                doubleFilter.setUserMin(inputMin);

                Assertions.assertThrows(
                        IllegalMinMaxException.class,
                        () -> doubleFilter.setUserMax(inputMax));
            }

            @ParameterizedTest
            @MethodSource("provideParameters")
            @DisplayName("Set invalid DoubleFilter userMax and then userMin")
            void setValidDoubleFilterUserMaxAndThenUserMin(double inputMin, double inputMax) throws IllegalMinMaxException {
                DoubleFilter doubleFilter = new DoubleFilter(
                        "Test Name",
                        "Test Discription",
                        "Test product Attribute",
                        1, 1000);

                doubleFilter.setUserMax(inputMax);

                Assertions.assertThrows(
                        IllegalMinMaxException.class,
                        () -> doubleFilter.setUserMin(inputMin));
            }

            private static Stream<Arguments> provideParameters() {
                return Stream.of(
                        Arguments.of(2.0, 1.0),
                        Arguments.of(5.5, 3.5),
                        Arguments.of(1000.0, 999.0)
                );
            }
        }

        @Nested
        @DisplayName("Testing set userMin and userMax with incorrect types")
        class testUserValuesWithIncorrectTypes {

            static Stream<DoubleFilter> doubleFilterProvider() {
                return provideDoubleFiltersProvider();
            }

            @ParameterizedTest
            @DisplayName("Setting userMin with incorrect type throws exception")
            @MethodSource("doubleFilterProvider")
            void setUserMinWithIncorrectType(DoubleFilter filter) {
                long newValue = 100;

                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> filter.setUserMin(newValue)
                );
            }

            @ParameterizedTest
            @DisplayName("Setting userMax with incorrect type throws exception")
            @MethodSource("doubleFilterProvider")
            void setUserMaxWithIncorrectType(DoubleFilter filter) {
                long newValue = 100;
                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> filter.setUserMax(newValue)
                );
            }
        }
    }
}