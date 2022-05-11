package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TimeFilterTest {

    @Test
    void getType() {
        Instant dbMin = Instant.parse("2018-11-30T15:35:24.00Z");
        Instant dbMax = Instant.parse("2022-11-30T15:35:24.00Z");
        TimeFilter timeFilter = new TimeFilter(0, "test name", "test description", "publishedDate", dbMin, dbMax);
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
        //
        //It must also be able to take an empty list.

        @Test
        @DisplayName("filter a list of actual products")
        void useFilter() {
            // preparing the filter
            Instant dbMin = Instant.parse("2018-11-30T15:35:24.00Z");
            Instant dbMax = Instant.parse("2022-11-30T15:35:24.00Z");
            Instant userMin = Instant.parse("2019-11-30T15:35:24.00Z");
            Instant userMax = Instant.parse("2021-11-30T15:35:24.00Z");
            TimeFilter internalFilter = new TimeFilter(0, "test name", "test description", "publishedDate", dbMin, dbMax);
//            internalFilter.setUserMin(userMin);
//            internalFilter.setUserMax(userMax);

            // preparing the input list
            // Should be tested in a separate test.
            List<Product> mockResults = Helpers.readMockProductResultsFromFile("MockResults.csv", true);

            //crude check that the mockresults are what we expect, and have not been changed
            // Not up to the standard of our unit tests
            assertEquals(7, mockResults.size());

            // preparing the expected result list
            List<Product> copy = List.copyOf(mockResults);
            ArrayList<Product> expectedResults = new ArrayList<>(copy);
            expectedResults.remove(6);
//            expectedResults.remove(3);
//            expectedResults.remove(2);
//            expectedResults.remove(1);
            expectedResults.remove(0);
            // remember that remove, moves the index of subsequent elements

            // Executing the action
            Collection<Product> filteredResults = internalFilter.useFilter(mockResults);

            // verifying the result, and formatting it in a readable way in case the test fails
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
            // preparing the filter
            Instant dbMin = Instant.parse("2018-11-30T15:35:24.00Z");
            Instant dbMax = Instant.parse("2022-11-30T15:35:24.00Z");
            Instant userMin = Instant.parse("2019-11-30T15:35:24.00Z");
            Instant userMax = Instant.parse("2021-11-30T15:35:24.00Z");
            TimeFilter internalFilter = new TimeFilter(0, "test name", "test description", "publishedDate", dbMin, dbMax);
            internalFilter.setUserMin(userMin);
            internalFilter.setUserMax(userMax);

            // preparing the input list
            Collection<Product> emptyResults = new ArrayList<>();

            // Executing the action
            Collection<Product> filteredResults = internalFilter.useFilter(emptyResults);

            // Verifying the result
            assertEquals(new ArrayList<>(), filteredResults);
        }
    }
}
