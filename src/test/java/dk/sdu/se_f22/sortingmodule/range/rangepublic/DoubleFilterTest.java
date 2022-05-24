package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalMinMaxException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.RangeFilterException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DoubleFilterTest {
    /**
     * The returned DoubleFilter has a min of 0 and a max of 1000
     */
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

        @ParameterizedTest
        @DisplayName("Changing product attribute actually changes attribute used for filtering")
        @ValueSource(strings = {"price", "averageUserReview", "clockspeed","weight" })
        void changingProductAttributeActuallyChangesBeingFiltered(String productAttribute) throws RangeFilterException {
            //Creating filter through getTestFilter method
            DoubleFilter filter = getTestFilter(productAttribute);
            double userMin = 10.0;
            double userMax = 100.0;

            filter.setUserMin(userMin);
            filter.setUserMax(userMax);


            //Preparing input list
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("rangepublic/ProductsForDoubleFilterTest.csv", true);

            // preparing the expected result list
            // Inserting the product, that is within the range of usermin and usermax
            ArrayList<Product> expectedResults = new ArrayList<>();
            if(productAttribute.equals("price")) {
                expectedResults.add(mockResults.get(0));
            }
            if(productAttribute.equals("averageUserReview")) {
                expectedResults.add(mockResults.get(1));
            }
            if(productAttribute.equals("clockspeed")) {
                expectedResults.add(mockResults.get(2));
            }
            if(productAttribute.equals("weight")) {
                expectedResults.add(mockResults.get(3));
            }

            Collection<Product> filteredResults = filter.useFilter(mockResults);

            Assertions.assertEquals(expectedResults,filteredResults);
        }

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
                String initialInfo = "\n" + internalFilter.getProductAttribute() + "\n";
                StringBuilder out = new StringBuilder(initialInfo + "Expected results: length=" + expectedResults.size() + "\n");
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

        static Stream<Arguments> useFilterArguments() throws IllegalMinMaxException {
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
        void filteringAnEmptyListOfResults(String productAttribute, boolean setUserMinAndMax, Collection<Product> inputList) throws IllegalMinMaxException {
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
        class SetUserMinAndMax {

            @Nested
            @DisplayName("Set valid userMin and userMax")
            class SetValidUserMinAndUserMax {

                @ParameterizedTest
                @ValueSource(doubles = {1.0, 3.5, 999.0})
                @DisplayName("Set valid DoubleFilter userMin")
                void setValidDoubleFilterUserMin (double input) throws RangeFilterException {
                    DoubleFilter doubleFilter = new DoubleFilter(
                            "Test Name",
                            "Test Discription",
                            "Test product Attribute",
                            1, 1000);

                    Assertions.assertEquals(doubleFilter.setUserMin(input), doubleFilter.getUserMinDouble());

                }

                @ParameterizedTest
                @ValueSource(doubles = {2.0, 3.5, 1000.0})
                @DisplayName("Set valid DoubleFilter userMax")
                void setValidDoubleFilterUserMax (double input) throws IllegalMinMaxException {
                    DoubleFilter doubleFilter = new DoubleFilter(
                            "Test Name",
                            "Test Discription",
                            "Test product Attribute",
                            1, 1000);

                    doubleFilter.setUserMax(input);
                    Assertions.assertEquals(input, doubleFilter.getUserMaxDouble());
                }

                @ParameterizedTest
                @MethodSource("provideParameters")
                @DisplayName("Set valid DoubleFilter userMax And userMax")
                void setValidDoubleFilterUserMinAndUserMax (double inputMin, double inputMax) {
                    DoubleFilter doubleFilter = new DoubleFilter(
                            "Test Name",
                            "Test Discription",
                            "Test product Attribute",
                            1, 1000);

                    Assertions.assertDoesNotThrow(
                            () -> {
                                doubleFilter.setUserMax(inputMax);
                                doubleFilter.setUserMin(inputMin);
                            });
                }

                private static Stream<Arguments> provideParameters() {
                    return Stream.of(
                            Arguments.of(1.0, 2.0),
                            Arguments.of(3.5, 5.5),
                            Arguments.of(999.0, 1000.0)
                    );
                }
            }

            @Nested
            @DisplayName("Set invalid userMin and userMax")
            class SetInvalidUserMinAndUserMax {

                @ParameterizedTest
                @ValueSource(doubles = {Double.MIN_VALUE, -1.0, 0.0})
                @DisplayName("Set invalid DoubleFilter userMin")
                void setValidDoubleFilterUserMin (double input) {
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
                void setValidDoubleFilterUserMax (double input) {
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
                @DisplayName("Set invalid DoubleFilter userMax And userMax")
                void setValidDoubleFilterUserMinAndUserMax (double inputMin, double inputMax) {
                    DoubleFilter doubleFilter = new DoubleFilter(
                            "Test Name",
                            "Test Discription",
                            "Test product Attribute",
                            1, 1000);

                    Assertions.assertThrows(
                            IllegalMinMaxException.class,
                            () -> {
                                doubleFilter.setUserMin(inputMin);
                                doubleFilter.setUserMax(inputMax);
                            });
                }

                private static Stream<Arguments> provideParameters() {
                    return Stream.of(
                            Arguments.of(2.0, 1.0),
                            Arguments.of(5.5, 3.5),
                            Arguments.of(1000.0, 999.0)
                    );
                }
            }

            @Test
            @DisplayName("Set userMin with incorrect type")
            void setUserMinWithIncorrectType () {
                long newValue = 100;
                DoubleFilter doubleFilter = new DoubleFilter(
                        "Test Name",
                        "Test Discription",
                        "Test product Attribute",
                        1, 1000);

                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> doubleFilter.setUserMin(newValue)
                );
            }

            @Test
            @DisplayName("Set userMax with incorrect type")
            void setUserMaxWithIncorrectType () {
                long newValue = 100;
                DoubleFilter doubleFilter = new DoubleFilter(
                        "Test Name",
                        "Test Discription",
                        "Test product Attribute",
                        1, 1000);

                Assertions.assertThrows(IllegalMinMaxException.class,
                        () -> doubleFilter.setUserMax(newValue)
                );
            }
        }
    }
}