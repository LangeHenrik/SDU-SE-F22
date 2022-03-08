package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.searchmodule.infrastructure.mocks.MockIndexingData;
import dk.sdu.se_f22.searchmodule.infrastructure.mocks.MockIndexingModule;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestSearchModule {

    @Test
    public void testQueryIndexOfType() {
        // Create a mock indexing module with mock data
        MockIndexingModule indexingModule = new MockIndexingModule();
        indexingModule.indexingData.add(new MockIndexingData("Hello"));
        indexingModule.indexingData.add(new MockIndexingData("Foo"));
        indexingModule.indexingData.add(new MockIndexingData("Bar"));
        indexingModule.indexingData.add(new MockIndexingData("Foo"));


        SearchModuleImpl searchModule = new SearchModuleImpl();
        searchModule.addIndexingModule(indexingModule);

        // Query the indexing module
        var helloQuery = searchModule.queryIndexOfType(MockIndexingData.class, List.of("Hello"));
        var fooQuery = searchModule.queryIndexOfType(MockIndexingData.class, List.of("Foo"));

        // Test querying of data
        assertArrayEquals(
                helloQuery.toArray(),
                List.of(new MockIndexingData("Hello")).toArray()
        );

        assertArrayEquals(
                fooQuery.toArray(),
                List.of(new MockIndexingData("Foo"), new MockIndexingData("Foo")).toArray()
        );

        // Test throwing
        assertThrows(
                NoSuchElementException.class,
                () -> searchModule.queryIndexOfType(String.class, List.of(""))
        );

        // Test removal
        searchModule.removeIndexingModule(indexingModule);
        assertThrows(
                NoSuchElementException.class,
                () -> searchModule.queryIndexOfType(MockIndexingData.class, List.of(""))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSearch() {
        SearchModule searchModule = new SearchModuleImpl() {
            @Override
            public <T> List<T> queryIndexOfType(Class<T> clazz, List<String> tokens) {
                if(clazz == Product.class) {
                    List<Product> productPages = new ArrayList<>();
                    productPages.add(new Product());
                    return (List<T>) productPages;
                }
                else if(clazz == Brand.class) {
                    List<Brand> brandPages = new ArrayList<>();
                    Brand brand = new Brand();
                    brand.setName("Test brand");
                    brandPages.add(brand);
                    return (List<T>) brandPages;
                }
                else {
                    return null;
                }
            }
        };

        SearchResult searchResult = searchModule.search("Hello world");

        // We cannot use assertArrayEquals because brand doesn't implement the comparable operator, so we just match
        // the name on the first element to check whether the query method was called for brand pages
        if(searchResult.getBrandPages().stream().findFirst().isPresent()) {
            assertEquals(searchResult.getBrandPages().stream().findFirst().get().getName(), "Test brand");
        }

        // Same here, although we don't have any attribute fields to test against, so we just check that an object is in the list
        assertTrue(searchResult.getProductPages().stream().findFirst().isPresent());
    }
}
