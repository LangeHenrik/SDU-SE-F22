package dk.sdu.se_f22.SortingModule.Range;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InternalFilterTest {
    @Nested
    @DisplayName("Use filter method")
    class useFilterMethod {
        //The filter method should be tested such that it will only filter out the results that do not match.
        //
        //It must also be able to take an empty list.

        @Test
        @DisplayName("filter a list of actual products")
        void useFilter() {
            InternalFilter internalFilter = new InternalFilter(0, 1000, "price");
            List<RangeSearchResultMock> tempResults = new ArrayList<>();
            tempResults.addAll(Helpers.readMockResultsFromFile("MockResults.csv"));

            RangeSearchResultMock[] mockResults = InternalFilter.convertListToArray(tempResults);

            //crude check that the mockresults are what we expect, and have not been changed
            assertEquals(6, mockResults.length);


            String[] attributeNames = {"price", "height", "stock"};

            List<String> expectedResultStrings = new ArrayList<>();
            expectedResultStrings.add("1000.0,100.0,2");
//        expectedResultStrings.add("2000.0,200.0,3");
//        expectedResultStrings.add("3000.0,10.0,4");
            expectedResultStrings.add("100.0,100.0,5");
            expectedResultStrings.add("200.0,200.0,6");
            expectedResultStrings.add("300.0,300.0,7");

            List<RangeSearchResultMock> tempExpected = Helpers.createMockResultsFromStringList(expectedResultStrings, attributeNames);

            RangeSearchResultMock[] expectedResults = InternalFilter.convertListToArray(tempExpected);

            //crude check that the expectedResults are still the same as when the test was written
            assertEquals(4, expectedResults.length);

            RangeSearchResultMock[] filteredResults = internalFilter.useFilter(mockResults);

            String message = Helpers.formatArrays(expectedResults, filteredResults);

            assertArrayEquals(expectedResults, filteredResults, message);
        }

        @Test
        @DisplayName("Filtering an empty list of results")
        void filteringAnEmptyListOfResults() {
            InternalFilter internalFilter = new InternalFilter(0, 1000, "price");
            RangeSearchResultMock[] emptyResults = new RangeSearchResultMock[0];

            RangeSearchResultMock[] filteredResults = internalFilter.useFilter(emptyResults);

            assertArrayEquals(emptyResults, filteredResults);
        }

    }


    @Nested
    @DisplayName("convert result list to result array ")
    class convertResultListToResultArray {
        @Test
        @DisplayName("Testing an empty list")
        void testingAnEmptyList() {
            List<RangeSearchResultMock> inputList = new ArrayList<>();

            RangeSearchResultMock[] generatedResults = InternalFilter.convertListToArray(inputList);

            RangeSearchResultMock[] expected = new RangeSearchResultMock[0];

            assertArrayEquals(expected, generatedResults);
        }

        @Test
        @DisplayName("Testing a non empty list")
        void testingANonEmptyList() {
            // should we get the information from a csv file, or simply
            String[] attributeNames = {"price", "height", "stock"};
            String[] testAttributes = {
                    "1000.0,100.0,2",
                    "2000.0,200.0,3",
                    "3000.0,10.0,4",
                    "100.0,100.0,5",
                    "200.0,200.0,6",
                    "300.0,300.0,7"
            };

            RangeSearchResultMock[] expectedResults = new RangeSearchResultMock[testAttributes.length];
            List<RangeSearchResultMock> inputs = new ArrayList<>();

            for (int i = 0; i < testAttributes.length; i++) {
                RangeSearchResultMock resultMock = new RangeSearchResultMock(
                        Helpers.createAttributeMapForMockResults(attributeNames, testAttributes[i])
                );

                inputs.add(resultMock);
                expectedResults[i] = resultMock;
            }

            RangeSearchResultMock[] generatedResults = InternalFilter.convertListToArray(inputs);

            assertArrayEquals(expectedResults, generatedResults);
        }
    }


}