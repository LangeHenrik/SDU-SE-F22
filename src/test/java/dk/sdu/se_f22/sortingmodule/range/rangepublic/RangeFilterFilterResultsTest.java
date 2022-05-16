package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.range.Helpers;
import dk.sdu.se_f22.sortingmodule.range.RangeSearchResultMock;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IllegalImplementationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

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

    private Brand[] getBrandArray(){
        return new Brand[]{
                new Brand("Gucci", "expensive", "early", "Somewhere"),
                new Brand("Lenovo", "computer brand", "sometime", "asia"),
                new Brand("Razer", "Shitty software", "Who knows, not me", "Who knows")
        };
    }

    private SearchHits getSearchHitsWithBrandAndContent(){
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