package dk.sdu.se_f22.sortingmodule.infrastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

class SearchHitsTest {
    private SearchHits sh;
    private Collection products;
    private Collection brands;
    private Collection contents;

    @BeforeEach
    void setUp() {
        // Test search hits start of empty
        this.sh = SearchHits.getInstance();

        this.products = this.sh.getProducts();
        this.sh.setProducts(new ArrayList());

        this.brands = this.sh.getBrands();
        this.sh.setBrands(new ArrayList());

        this.contents = this.sh.getContents();
        this.sh.setContents(new ArrayList());
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Instance test")
    @Test
    void instance() {
        // This will ensure that it is a singleton class
        assertEquals(this.sh, SearchHits.getInstance());
    }

    @DisplayName("Set empty collection")
    @Nested
    public class SetEmptySearchHitsTest {
        @DisplayName("Set empty products test")
        @Test
        void setEmptyProducts() {
            SearchHits.getInstance().setProducts(new ArrayList<>());
            assertEquals(new ArrayList<>(), SearchHits.getInstance().getProducts());
        }

        @DisplayName("Set empty brands test")
        @Test
        void setEmptyBrands() {
            SearchHits.getInstance().setBrands(new ArrayList<>());
            assertEquals(new ArrayList<>(), SearchHits.getInstance().getBrands());
        }

        @DisplayName("Set empty content test")
        @Test
        void setEmptyContents() {
            SearchHits.getInstance().setContents(new ArrayList<>());
            assertEquals(new ArrayList<>(), SearchHits.getInstance().getContents());
        }
    }

    @DisplayName("Get empty collection")
    @Nested
    public class GetEmptySearchHitsTest {
        @DisplayName("Get empty products test")
        @Test
        void getEmptyProducts() {
            assertEquals(new ArrayList<>(), SearchHits.getInstance().getProducts());
        }

        @DisplayName("Get empty brands test")
        @Test
        void getEmptyBrands() {
            assertEquals(new ArrayList<>(), SearchHits.getInstance().getBrands());
        }

        @DisplayName("Get empty content test")
        @Test
        void getEmptyContents() {
            assertEquals(new ArrayList<>(), SearchHits.getInstance().getContents());
        }
    }

    @DisplayName("Add elements through get methods")
    @Nested
    public class AddGetSearchHitsTest {
        @DisplayName("Add products through getProducts")
        @Test
        void addProductsGet() {
            SearchHits hits = SearchHits.getInstance();
            Collection productsHits = hits.getProducts();

            productsHits.add("Product 1");
            productsHits.add("Product 2");
            productsHits.add("Product 3");

            assertEquals(productsHits, SearchHits.getInstance().getProducts());
        }

        @DisplayName("Add brands through getBrands")
        @Test
        void addBrandsGet() {
            SearchHits hits = SearchHits.getInstance();
            Collection brandHits = hits.getBrands();

            brandHits.add("Brand 1");
            brandHits.add("Brand 2");
            brandHits.add("Brand 3");

            assertEquals(brandHits, SearchHits.getInstance().getBrands());
        }

        @DisplayName("Add contents through getContents")
        @Test
        void addContentsGet() {
            SearchHits hits = SearchHits.getInstance();
            Collection contentsHits = hits.getContents();

            contentsHits.add("Page 1");
            contentsHits.add("Page 2");
            contentsHits.add("Page 3");

            assertEquals(contentsHits, SearchHits.getInstance().getContents());
        }
    }

    @DisplayName("Add elements through set methods")
    @Nested
    public class AddSetSearchHitsTest {
        @DisplayName("Add products through setProducts")
        @Test
        void addProductsSet() {
            SearchHits hits = SearchHits.getInstance();
            Collection productsHits = new ArrayList<>();

            productsHits.add("Product 1");
            productsHits.add("Product 2");
            productsHits.add("Product 3");

            hits.setProducts(productsHits);

            assertEquals(productsHits, SearchHits.getInstance().getProducts());
        }

        @DisplayName("Add brands through setBrands")
        @Test
        void addBrandsSet() {
            SearchHits hits = SearchHits.getInstance();
            Collection brandHits = new ArrayList<>();

            brandHits.add("Brand 1");
            brandHits.add("Brand 2");
            brandHits.add("Brand 3");

            hits.setBrands(brandHits);

            assertEquals(brandHits, SearchHits.getInstance().getBrands());
        }

        @DisplayName("Add contents through setContents")
        @Test
        void addContentsSet() {
            SearchHits hits = SearchHits.getInstance();
            Collection contentsHits = new ArrayList<>();

            contentsHits.add("Page 1");
            contentsHits.add("Page 2");
            contentsHits.add("Page 3");

            hits.setContents(contentsHits);

            assertEquals(contentsHits, SearchHits.getInstance().getContents());
        }
    }

    @DisplayName("Null set prohibiting")
    @Nested
    public class NullSetSearchHitsTest {
        @Test
        void NullProductsSet() {
            SearchHits hits = SearchHits.getInstance();
            assertThrows(NullPointerException.class, () -> hits.setProducts(null));
        }

        @Test
        void NullBrandsSet() {
            SearchHits hits = SearchHits.getInstance();
            assertThrows(NullPointerException.class, () -> hits.setBrands(null));
        }

        @Test
        void NullContentsSet() {
            SearchHits hits = SearchHits.getInstance();
            assertThrows(NullPointerException.class, () -> hits.setContents(null));
        }
    }
}