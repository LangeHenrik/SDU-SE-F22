package dk.sdu.se_f22.SortingModule.Range;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// The class should filter correctly when using multiple ids.
// Note that the filtering has been tested elsewhere and is thus not the main focus of testing this class.
// The focus is therefore that multiple filters at the same time is handled correctly.
// It must also be able to handle an empty list of filter ids.
class RangeMainTest {
    private RangeMain rangeMain;
    private List<RangeSearchResultMock> mockResults;
    private List<RangeFilter> mockFilters;

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

        RangeFilter[] fm = new RangeFilter[mockFilters.size()];
        RangeSearchResultMock[] rm = new RangeSearchResultMock[mockResults.size()];

        for(int i = 0; i< rm.length; i++){
            rm[i] = mockResults.get(i);
        }

        for(int i = 0; i< fm.length; i++){
            fm[i] = mockFilters.get(i);
        }

        rangeMain.filterResults(rm, fm);
    }
}