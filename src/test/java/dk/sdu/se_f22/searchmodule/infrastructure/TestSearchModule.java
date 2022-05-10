package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.productmodule.management.BaseProduct;
import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.searchmodule.infrastructure.mocks.MockFilteringModule;
import dk.sdu.se_f22.searchmodule.infrastructure.mocks.MockIndexingData;
import dk.sdu.se_f22.searchmodule.infrastructure.mocks.MockIndexingModule;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestSearchModule {
    private SearchModule searchModule;

    @BeforeEach
    public void beforeEach(){
        this.searchModule = new SearchModuleImpl();
    }


    @Test
    public void testFilterTokens() {
        SearchModuleImpl searchModule = new SearchModuleImpl();
        searchModule.addFilteringModule(MockFilteringModule.getInstance());

        List<String> testTokens = new ArrayList<>(List.of("My", "Search", "Tokens"));
        List<String> filteredTokens = searchModule.filterTokens(testTokens);
        assertArrayEquals(List.of("My", "Search").toArray(), filteredTokens.toArray());

        searchModule.removeFilteringModule(MockFilteringModule.getInstance());
        testTokens = new ArrayList<>(List.of("My", "Search", "Tokens"));
        filteredTokens = searchModule.filterTokens(testTokens);
        assertArrayEquals(List.of("My", "Search", "Tokens").toArray(), filteredTokens.toArray());
    }

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
                List.of(new MockIndexingData("Hello")).toArray());

        assertArrayEquals(
                fooQuery.toArray(),
                List.of(new MockIndexingData("Foo"), new MockIndexingData("Foo")).toArray());

        // Test non-existing module for datatype
        assertEquals(
                0,
                searchModule.queryIndexOfType(String.class, List.of("")).size()
        );

        // Test removal
        searchModule.removeIndexingModule(MockIndexingData.class);
        assertEquals(
                0,
                searchModule.queryIndexOfType(MockIndexingData.class, List.of("")).size()
        );
    }

    public static BaseProduct createExampleBaseproduct() {
        var product = new BaseProduct();
        product.set(ProductAttribute.ID, UUID.randomUUID().toString());
        product.set(ProductAttribute.AVERAGE_USER_REVIEW, "123.0");
        product.set(ProductAttribute.IN_STOCK, "hello");
        product.set(ProductAttribute.EAN, "123");
        product.set(ProductAttribute.PRICE, "123");
        product.set(ProductAttribute.PUBLISHED_DATE, "2022-05-10T07:51:31.631793829");
        product.set(ProductAttribute.EXPIRATION_DATE, "2022-05-10T07:51:31.631793829");
        product.set(ProductAttribute.CATEGORY, "hello");
        product.set(ProductAttribute.NAME, "hello");
        product.set(ProductAttribute.DESCRIPTION, "hello");
        product.set(ProductAttribute.SIZE, "unavailable");
        product.set(ProductAttribute.CLOCKSPEED, "unavailable");
        product.set(ProductAttribute.WEIGHT, "unavailable");
        return product;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSearch() {
        SearchModule searchModule = new SearchModuleImpl() {
            @Override
            public <T> List<T> queryIndexOfType(Class<T> clazz, List<String> tokens) {
                if (clazz == BaseProduct.class) {
                    List<BaseProduct> baseProductPages = new ArrayList<>();
                    baseProductPages.add(createExampleBaseproduct());
                    return (List<T>) baseProductPages;
                } else if (clazz == Brand.class) {
                    List<Brand> brandPages = new ArrayList<>();
                    Brand brand = new Brand();
                    brand.setId(123);
                    brand.setName("Test brand");
                    brandPages.add(brand);
                    return (List<T>) brandPages;
                } else {
                    return null;
                }
            }
        };

        SearchHits searchResult = searchModule.search("Hello world");

        // We cannot use assertArrayEquals because brand doesn't implement the
        // comparable operator, so we just match
        // the name on the first element to check whether the query method was called
        // for brand pages

        List<Brand> brands = searchResult.getBrands().stream().toList();
        if (brands.stream().findFirst().isPresent()) {
            assertEquals(brands.stream().findFirst().get().getName(), "Test brand");
        }

        // Same here, although we don't have any attribute fields to test against, so we
        // just check that an object is in the list
        List<Product> baseProducts = searchResult.getProducts().stream().toList();
        assertTrue(baseProducts.stream().findFirst().isPresent());
    }

    void getDelimitersTest() {
        try {
            Connection connection = DBConnection.getPooledConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM searchtokendelimiters");
            stmt.execute();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SearchModuleImpl searchModule = new SearchModuleImpl();
        assertArrayEquals(new String[0], searchModule.getDelimiters().toArray());

        searchModule.addDelimiter("hello");

        var delimiters = searchModule.getDelimiters();
        var expectedDelimiters = List.of("hello");

        assertArrayEquals(expectedDelimiters.toArray(), delimiters.toArray());
        searchModule.addDelimiter("hello");

        delimiters = searchModule.getDelimiters();
        assertArrayEquals(expectedDelimiters.toArray(), delimiters.toArray());
    }

    @Test
    void addDelimitersTest(){
        try {
            Connection connection = DBConnection.getPooledConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM searchtokendelimiters");
            stmt.execute();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SearchModuleImpl searchModule = new SearchModuleImpl();

        assertArrayEquals(List.of(" ").toArray(), searchModule.getDelimiters().toArray());

        searchModule.addDelimiter("hello");

        var delimiters = searchModule.getDelimiters();
        var expectedDelimiters = List.of(" ", "hello");

        assertArrayEquals(expectedDelimiters.toArray(), delimiters.toArray());
        searchModule.addDelimiter("hello");

        delimiters = searchModule.getDelimiters();
        assertArrayEquals(expectedDelimiters.toArray(), delimiters.toArray());
    }

    @Test
    void removeDelimiterTest(){
        try {
            Connection connection = DBConnection.getPooledConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM searchtokendelimiters");
            stmt.execute();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SearchModuleImpl searchModule = new SearchModuleImpl();

        searchModule.addDelimiter("hello");
        var expectedDelimiters = List.of(" ", "hello");
        System.out.println(Arrays.toString(searchModule.getDelimiters().toArray()));
        assertArrayEquals(expectedDelimiters.toArray(), searchModule.getDelimiters().toArray());

        searchModule.removeDelimiter("hello");
        assertArrayEquals(List.of(" ").toArray(), searchModule.getDelimiters().toArray());
    }
}
