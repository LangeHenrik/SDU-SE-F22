package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalMinMaxException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@Execution(ExecutionMode.CONCURRENT)
// Remember to check whether concurrent has a detrimental impact on performance
class RangeFilterFilterResultsTest {
    // Test that an exception is thrown if an illegal implementation is used
    // Use the list of filters and add one more that is illegally implemented

    /**
     * @return A list containing 3 filters. <br>
     * 0 DoubleFilter "price" 0 1000 <br>
     * 1 LongFilter "ean" 12345673 12345675 <br>
     * 2 TimeFilter "publishedDate" 2018-11-30T15:35:24.00Z 2022-11-30T15:35:24.00Z
     */
    private List<RangeFilter> getTestFilters() {
        List<RangeFilter> out = new ArrayList<>();

        out.add(DoubleFilterTest.getTestFilter("price"));
        out.add(LongFilterTest.getTestFilter());
        out.add(TimeFilterTest.getTestFilter("publishedDate"));

        return out;
    }

    private Brand[] getBrandArray() {
        return new Brand[]{
                new Brand("Gucci", "expensive", "early", "Somewhere"),
                new Brand("Lenovo", "computer brand", "sometime", "asia"),
                new Brand("Razer", "Shitty software", "Who knows, not me", "Who knows")
        };
    }

    private SearchHits getSearchHitsWithBrandAndContent() {
        //Prepare the searchHits object by setting elements for the 2 other types of content
        SearchHits emptyHits = new SearchHits();
        // Set brands:
        // Below is the real way to test this.
        // However it does not work because the Brand class does not contain an equals method
//        List<Brand> brandList = List.of(
//                new Brand("Gucci", "expensive", "early", "Somewhere"),
//                new Brand("Lenovo", "computer brand", "sometime", "asia"),
//                new Brand("Razer", "Shitty software", "Who knows, not me", "Who knows")
//        );
//        //stupid way to copy, but it ensures that there is no connection between the objects
//        List<Brand> expectedBrands = List.of(
//                new Brand("Gucci", "expensive", "early", "Somewhere"),
//                new Brand("Lenovo", "computer brand", "sometime", "asia"),
//                new Brand("Razer", "Shitty software", "Who knows, not me", "Who knows")
//        );

        // This is the compromise since we cannot have actually different brand objects
        Brand[] brands = getBrandArray();

        List<Brand> brandList = List.of(brands);

        emptyHits.setBrands(brandList);

        // Set contents
        // The content class has not been created by the corresponding group.
        // Thus we cannot make good copies, and such we have to simply use the same variable contentList.
        // This lowers the value of the test, but there is nothing we can do about that
        List<Content> contentList = List.of(new Content(), new Content(), new Content());

        emptyHits.setContents(contentList);

        return emptyHits;
    }

    @Test
    @DisplayName("Filter results wth empty result list")
    void filterResultsWithEmptyResultList() throws IllegalImplementationException {
        //Should return the empty list without changing the other elements in searchHits
        SearchHits emptyHits = getSearchHitsWithBrandAndContent();

        // This is not good but is necessary due to a lacking implementation of equals methods in brands and content classes
        Collection<Brand> expectedBrands = emptyHits.getBrands();
        Collection<Content> expectedContent = emptyHits.getContents();


        SearchHits result = RangeFilterFilterResults.filterResults(emptyHits, getTestFilters());

        // Check that the products are empty
        Assertions.assertEquals(new SearchHits().getProducts(), result.getProducts(), "Using the default searchHits constructor to set an empty product list");

        Assertions.assertEquals(expectedBrands, result.getBrands());
        Assertions.assertEquals(expectedContent, result.getContents());
    }

    @Test
    @DisplayName("Filter a result list with no filters should return an unmodified list")
    void filterAResultListWithNoFiltersShouldReturnAnUnmodifiedList() throws IllegalImplementationException {
        //Should return the list without changing any elements in searchHits
        SearchHits emptyHits = getSearchHitsWithBrandAndContent();

        // This is not good but is necessary due to a lacking implementation of equals methods in brands and content classes
        Collection<Brand> expectedBrands = emptyHits.getBrands();
        Collection<Content> expectedContent = emptyHits.getContents();

        List<Product> products = Helpers.readMockProductResultsFromFile("rangepublic/ProductsForDoubleFilterTest.csv", true);
        List<Product> expectedProducts = Helpers.readMockProductResultsFromFile("rangepublic/ProductsForDoubleFilterTest.csv", true);

        emptyHits.setProducts(products);

        SearchHits result = RangeFilterFilterResults.filterResults(emptyHits, new ArrayList<>());

        // We use toString, because the equals method has not been overridden in Product.
        // The toString comparison however will provide the exact same results as a properly implemented equals in this case
        Assertions.assertEquals(expectedProducts.toString(), result.getProducts().toString());

        Assertions.assertEquals(expectedBrands, result.getBrands());
        Assertions.assertEquals(expectedContent, result.getContents());
    }

    @ParameterizedTest(name = "Reverse filter order {0}")
    @ValueSource(booleans = {false, true})
    @DisplayName("Filter results with non empty lists")
    void filterResultsWithNonEmptyLists(boolean reverseFilterList) throws IllegalImplementationException {
        // Create one of each different RangeFilter type
        // Make csv file with products - make sure the products which is accepted by a filters min/max range also is accepted by the other filters min/max range
        // 2 products should be removed by each filter
        // For each filter there should be 2 safe products which are on the other side of the threshold and thus should not be removed
        // Assert the filtered list is equal to the expected results

        SearchHits hits = getSearchHitsWithBrandAndContent();

        // This is not good but is necessary due to a lacking implementation of equals methods in brands and content classes
        Collection<Brand> expectedBrands = hits.getBrands();
        Collection<Content> expectedContent = hits.getContents();

        List<Product> products = Helpers.readMockProductResultsFromFile("rangepublic/ProductsForRangeFilterFilterResultsTest.csv", true);
        List<Product> expectedProducts = Helpers.readMockProductResultsFromFile("rangepublic/ExpectedProductsForRangeFilterFilterResultsTest.csv", true);


        hits.setProducts(products);

        List<RangeFilter> rangeFilters = getTestFilters();

        if (reverseFilterList) {
            List<RangeFilter> reversed = new ArrayList<>();
            for (RangeFilter filter : rangeFilters) {
                reversed.add(0, filter);
            }

            rangeFilters = reversed;
        }

        SearchHits result = RangeFilterFilterResults.filterResults(hits, rangeFilters);

        // We use toString, because the equals method has not been overridden in Product.
        // The toString comparison however will provide the exact same results as a properly implemented equals in this case
        Assertions.assertEquals(expectedProducts.toString(), result.getProducts().toString());

        Assertions.assertEquals(expectedBrands, result.getBrands());
        Assertions.assertEquals(expectedContent, result.getContents());
    }

    @ParameterizedTest(name = "Reverse filter order {0}")
    @ValueSource(booleans = {false, true})
    @DisplayName("filterResultsWithFilterOnInvalidAttribute")
    void filterResultsWithFilterOnInvalidAttribute(boolean reverseFilterList) throws IllegalImplementationException {
        SearchHits hits = getSearchHitsWithBrandAndContent();

        // This is not good but is necessary due to a lacking implementation of equals methods in brands and content classes
        Collection<Brand> expectedBrands = hits.getBrands();
        Collection<Content> expectedContent = hits.getContents();

        List<Product> products = Helpers.readMockProductResultsFromFile("rangepublic/ProductsForRangeFilterFilterResultsTest.csv", true);
        List<Product> expectedProducts = Helpers.readMockProductResultsFromFile("rangepublic/ExpectedProductsForRangeFilterFilterResultsTest.csv", true);


        hits.setProducts(products);

        List<RangeFilter> rangeFilters = getTestFilters();

        // create the filter manually that has an invalid attribute
        rangeFilters.add(
                new DoubleFilter(100, "Invalid attribute double filter", "The double filter has an invalid attribute", "myPrice", 100, 101)
        );
        // If this filter is actually used, we would hopefully get a very small list, that would cause the test to fail

        if (reverseFilterList) {
            List<RangeFilter> reversed = new ArrayList<>();
            for (RangeFilter filter : rangeFilters) {
                reversed.add(0, filter);
            }

            rangeFilters = reversed;
        }

        SearchHits result = RangeFilterFilterResults.filterResults(hits, rangeFilters);

        // We use toString, because the equals method has not been overridden in Product.
        // The toString comparison however will provide the exact same results as a properly implemented equals in this case
        Assertions.assertEquals(expectedProducts.toString(), result.getProducts().toString());

        Assertions.assertEquals(expectedBrands, result.getBrands());
        Assertions.assertEquals(expectedContent, result.getContents());
    }

    @ParameterizedTest(name = "Reverse filter order {0}")
    @ValueSource(booleans = {false, true})
    @DisplayName("filtering with an illegally implemented filter throws exception")
    void filteringWithAnIllegallyImplementedFilterThrowsException(boolean reverseFilterList) {
        SearchHits hits = getSearchHitsWithBrandAndContent();

        // This is not good but is necessary due to a lacking implementation of equals methods in brands and content classes
        Collection<Brand> expectedBrands = hits.getBrands();
        Collection<Content> expectedContent = hits.getContents();

        List<Product> products = Helpers.readMockProductResultsFromFile("rangepublic/ProductsForRangeFilterFilterResultsTest.csv", true);
        List<Product> expectedProducts = Helpers.readMockProductResultsFromFile("rangepublic/ExpectedProductsForRangeFilterFilterResultsTest.csv", true);


        hits.setProducts(products);

        List<RangeFilter> rangeFilters = getTestFilters();

        // create the filter manually that has an invalid attribute
        // We create it as an implementation of RangeFilter interface, since it is the only public element (RangeFilterClass is default)
        rangeFilters.add(
                new RangeFilter() {
                    @Override
                    public int getId() {
                        return 0;
                    }

                    @Override
                    public String getName() {
                        return "illegal";
                    }

                    @Override
                    public String getDescription() {
                        return "illegal";
                    }

                    @Override
                    public String getProductAttribute() {
                        return "illegal";
                    }

                    @Override
                    public FilterTypes getType() {
                        return FilterTypes.DOUBLE; //Arbitrarily chosen
                    }

                    @Override
                    public double getDbMinDouble() {
                        return 0;
                    }

                    @Override
                    public Instant getDbMinInstant() {
                        return Instant.parse("2018-11-30T16:35:24.00Z");
                    }

                    @Override
                    public long getDbMinLong() {
                        return 0;
                    }

                    @Override
                    public double getDbMaxDouble() {
                        return 0;
                    }

                    @Override
                    public Instant getDbMaxInstant() {
                        return Instant.parse("2018-11-30T16:35:24.00Z");
                    }

                    @Override
                    public long getDbMaxLong() {
                        return 0;
                    }

                    @Override
                    public double getUserMinDouble() {
                        return 0;
                    }

                    @Override
                    public Instant getUserMinInstant() {
                        return Instant.parse("2018-11-30T16:35:24.00Z");
                    }

                    @Override
                    public long getUserMinLong() {
                        return 0;
                    }

                    @Override
                    public double getUserMaxDouble() {
                        return 0;
                    }

                    @Override
                    public Instant getUserMaxInstant() {
                        return Instant.parse("2018-11-30T16:35:24.00Z");
                    }

                    @Override
                    public long getUserMaxLong() {
                        return 0;
                    }

                    @Override
                    public double setUserMin(double userMin) {
                        return 0;
                    }

                    @Override
                    public Instant setUserMin(Instant userMin) {
                        return Instant.parse("2018-11-30T16:35:24.00Z");
                    }

                    @Override
                    public long setUserMin(long userMin) {
                        return 0;
                    }

                    @Override
                    public double setUserMax(double userMax) {
                        return 0;
                    }

                    @Override
                    public Instant setUserMax(Instant userMax) {
                        return Instant.parse("2018-11-30T16:35:24.00Z");
                    }

                    @Override
                    public long setUserMax(long userMax) {
                        return 0;
                    }
                }
        );
        //This is an illegal implementation, and should cause an exception when filtering the list

        if (reverseFilterList) {
            List<RangeFilter> reversed = new ArrayList<>();
            for (RangeFilter filter : rangeFilters) {
                reversed.add(0, filter);
            }

            rangeFilters = reversed;
        }

        List<RangeFilter> finalRangeFilters = rangeFilters;
        assertThrows(IllegalImplementationException.class, () -> RangeFilterFilterResults.filterResults(hits, finalRangeFilters));
    }
}