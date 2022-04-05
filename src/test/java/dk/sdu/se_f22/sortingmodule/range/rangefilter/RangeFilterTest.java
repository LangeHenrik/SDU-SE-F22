package dk.sdu.se_f22.sortingmodule.range.rangefilter;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RangeFilterTest {
    private RangeFilterInterface rangeFilterInterface;
    private List<RangeSearchResultMock> mockResults;
    private List<RangeFilter> mockFilters;

    @BeforeEach
    void setup() {
        rangeFilterInterface = new RangeFilterCreator();
        mockResults = new ArrayList<>();
        mockFilters = new ArrayList<>();
    }

    @Test
    @DisplayName("Filter results with non empty lists")
    void filterResultsWithNonEmptyLists() {
        //readFromCSV
        /*
        mockResults.addAll(Helpers.readMockResultsFromFile("MockResults.csv"));

        SearchHits hits = new SearchHits();

        hits.setProducts(mockResults);


        try {
            rangeFilterInterface.filterResults(hits, mockFilters);
        } catch (InvalidFilterIdException e) {
            e.printStackTrace();
        }
         */
    }
}