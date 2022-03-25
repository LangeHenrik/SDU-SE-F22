package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class RangeFilterFilterResultsTest {
    private List<RangeSearchResultMock> mockResults;
    private List<RangeFilter> mockFilters;

    @BeforeEach
    void setup() {
        mockResults = new ArrayList<>();
        mockFilters = new ArrayList<>();
        mockFilters.add(new DoubleFilter("test name", "test description", "price", 1, 2));
    }

    @Test
    @DisplayName("Filter results with non empty lists")
    void filterResultsWithNonEmptyLists() {
        //This test is horrible, the purpose is unclear, and has been lost to time. I'm sorry, it will be improved in the future.

        //readFromCSV
        mockResults.addAll(Helpers.readMockResultsFromFile("MockResults.csv"));

        SearchHits hits = new SearchHits();

        hits.setProducts(mockResults);


        try {
            RangeFilterFilterResults.filterResults(hits, mockFilters);
        } catch (IllegalImplementationException e) {
            Assertions.fail("One or more of the filters in the list was not one of the ones we made");
        }
    }
}