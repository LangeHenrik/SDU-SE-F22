package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangefilter.UserInputtedRangeFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

// The class should filter correctly when using multiple ids.
// Note that the filtering has been tested elsewhere and is thus not the main focus of testing this class.
// The focus is therefore that multiple filters at the same time is handled correctly.
// It must also be able to handle an empty list of filter ids.
class RangeMainTest {
    private RangeMain rangeMain;
    private List<RangeSearchResultMock> mockResults;
    private List<UserInputtedRangeFilter> mockFilters;

    @BeforeEach
    void setup() {
        rangeMain = new RangeMain();
        mockResults = new ArrayList<>();
        mockFilters = new ArrayList<>();
    }

    @Test
    @DisplayName("Filter results with non empty lists")
    void filterResultsWithNonEmptyLists() {
        //readFromCSV
        mockResults.addAll(Helpers.readMockResultsFromFile("MockResults.csv"));

        SearchHits hits = new SearchHits();

        hits.setProducts(mockResults);


        try {
            rangeMain.filterResults(hits, mockFilters);
        } catch (InvalidFilterIdException e) {
            e.printStackTrace();
        }
    }
}