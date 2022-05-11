package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;

@Execution(ExecutionMode.CONCURRENT)
// Remember to check whether concurrent has a detrimental impact on performance
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
    @Disabled("Not yet written")
    @DisplayName("Filter results with non empty lists")
    void filterResultsWithNonEmptyLists() {
        // Create one of each different RangeFilter type
        // Make csv file with products - make sure the products which is accepted by a filters min/max range also is accepted by the other filters min/max range
        // 2 products should be removed by each filter
        // Assert the filtered list is equal to the expected results
    }
}