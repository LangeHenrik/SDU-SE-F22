package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;
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
    @Disabled
    void getType() {
        fail("test not yet written");
    }

    @Test
    @Disabled
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
            LongFilter internalFilter = new LongFilter(0, "test name", "test description", "price", 0, 1000);
            internalFilter.setUserMax(1000);
            internalFilter.setUserMin(0);
            List<RangeSearchResultMock> mockResults = Helpers.readMockResultsFromFile("MockResults.csv");

            //crude check that the mockresults are what we expect, and have not been changed
            assertEquals(6, mockResults.size());


            String[] attributeNames = {"price", "height", "stock"};

            List<String> expectedResultStrings = new ArrayList<>();
            expectedResultStrings.add("1000.0,100.0,2");
//        expectedResultStrings.add("2000.0,200.0,3");
//        expectedResultStrings.add("3000.0,10.0,4");
            expectedResultStrings.add("100.0,100.0,5");
            expectedResultStrings.add("200.0,200.0,6");
            expectedResultStrings.add("300.0,300.0,7");

            Collection<RangeSearchResultMock> expectedResults = Helpers.createMockResultsFromStringList(expectedResultStrings, attributeNames);

            //crude check that the expectedResults are still the same as when the test was written
            assertEquals(4, expectedResults.size());

            Collection<RangeSearchResultMock> filteredResults = internalFilter.useFilter(mockResults);

            assertEquals(expectedResults, filteredResults,  expectedResults.toString() + filteredResults);
        }

        @Test
        @DisplayName("Filtering an empty list of results")
        void filteringAnEmptyListOfResults() {
            LongFilter internalFilter = new LongFilter(0, "test name", "test description", "price", 0, 1000);
            internalFilter.setUserMax(1000);
            internalFilter.setUserMin(0);
            Collection<RangeSearchResultMock> emptyResults = new ArrayList<>();

            Collection<RangeSearchResultMock> filteredResults = internalFilter.useFilter(emptyResults);

            assertEquals(emptyResults, filteredResults);
        }
    }
}