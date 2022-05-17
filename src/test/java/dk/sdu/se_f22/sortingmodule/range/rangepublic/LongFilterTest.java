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

class LongFilterTest {

    @Test
    void getType() {
        LongFilter longFilter = new LongFilter(0, "test name", "test description", "ean", 0, 1000);
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

        @Test
        @DisplayName("filter a list of actual products")
        void useFilter() {
            // The cause for the fail is known.
            // See DoubleFilterTest of the same method for explanation
            LongFilter internalFilter = new LongFilter(0, "test name", "test description", "ean", 12345673, 12345675);
//            internalFilter.setUserMax(1000);
//            internalFilter.setUserMin(0);
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

        @Test
        @DisplayName("Filtering an empty list of results")
        void filteringAnEmptyListOfResults() {
            LongFilter internalFilter = new LongFilter(0, "test name", "test description", "price", 0, 1000);
            internalFilter.setUserMax(1000);
            internalFilter.setUserMin(0);
            Collection<Product> emptyResults = new ArrayList<>();

            Collection<Product> filteredResults = internalFilter.useFilter(emptyResults);

            assertEquals(emptyResults, filteredResults);
        }
    }
}
