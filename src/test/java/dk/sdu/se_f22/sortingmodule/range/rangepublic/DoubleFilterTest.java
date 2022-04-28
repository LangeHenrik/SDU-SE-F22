package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidAttributeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
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
            List<ProductHit> mockResults = Helpers.readMockResultsFromFile("MockResults.csv", true);
            // the last 3 attributes of the product (size, clockspeed and weight) should be set to "unavailable" if they are not applicable

            //crude check that the mockresults are what we expect, and have not been changed
            assertEquals(6, mockResults.size());



            List<String> expectedResultStrings = new ArrayList<>();
            // HERE:
            // The line below causes an out-of-bounds error,
            // because we expect the array to contain all the possible attributes.
            // However, at the moment the test is still a remnant of the mockResults which only contained doubles.

            // Besides that, the expected results should definitely be read from a csv file,
            // instead of being put in like this.

            // If it is to avoid accidental bricking of the test, then the other settings (max, and min)
            // could also be saved in a (separate) csv file
            expectedResultStrings.add("1000.0,100.0,2");
//        expectedResultStrings.add("2000.0,200.0,3");
//        expectedResultStrings.add("3000.0,10.0,4");
            expectedResultStrings.add("100.0,100.0,5");
            expectedResultStrings.add("200.0,200.0,6");
            expectedResultStrings.add("300.0,300.0,7");

            Collection<ProductHit> expectedResults = Helpers.createMockResultsFromStringList(expectedResultStrings);

            //crude check that the expectedResults are still the same as when the test was written
            assertEquals(4, expectedResults.size());

            Collection<ProductHit> filteredResults = internalFilter.useFilter(mockResults);

            assertEquals(expectedResults, filteredResults,  expectedResults.toString() + filteredResults);
        }


        

        @Test
        @DisplayName("Filtering an empty list of results")
        void filteringAnEmptyListOfResults() {
            DoubleFilter internalFilter = new DoubleFilter(0, "test name", "test description", "price", 0, 1000);
            internalFilter.setUserMax(1000.0);
            internalFilter.setUserMin(0.0);
            Collection<ProductHit> emptyResults = new ArrayList<>();

            Collection<ProductHit> filteredResults = internalFilter.useFilter(emptyResults);

            assertEquals(emptyResults, filteredResults);
        }
    }

    //todo test setters
}