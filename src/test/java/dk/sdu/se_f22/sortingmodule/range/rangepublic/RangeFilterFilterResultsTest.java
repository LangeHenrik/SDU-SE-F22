package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import org.junit.jupiter.api.*;

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
    @Disabled("Not yet written")
    @DisplayName("Filter results with non empty lists")
    void filterResultsWithNonEmptyLists() {
    }
}