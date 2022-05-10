package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoubleFilterTest {

    @Test
    void getType() {
        DoubleFilter doubleFilter = new DoubleFilter(0, "test name", "test description", "price", 0, 1000);
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

        @Test
        @DisplayName("filter a list of actual products")
        void useFilter() {
            // The reason for the test fail is known, go to the comment starting with HERE
            DoubleFilter internalFilter = new DoubleFilter(0, "test name", "test description", "price", 0, 1000);
            internalFilter.setUserMax(1000.0);
            internalFilter.setUserMin(0.0);
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
                StringBuilder out = new StringBuilder("Expected results:\n");
                for (Product product: expectedResults){
                    out.append(product).append("\n");
                }

                out.append("\nActual results:\n");
                for (Product product: filteredResults){
                    out.append(product).append("\n");
                }

                return out.toString();
            });
        }

        @Test
        @DisplayName("Filtering an empty list of results")
        void filteringAnEmptyListOfResults() {
            DoubleFilter internalFilter = new DoubleFilter(0, "test name", "test description", "price", 0, 1000);
            internalFilter.setUserMax(1000.0);
            internalFilter.setUserMin(0.0);
            Collection<Product> emptyResults = new ArrayList<>();

            Collection<Product> filteredResults = internalFilter.useFilter(emptyResults);

            assertEquals(emptyResults, filteredResults);
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