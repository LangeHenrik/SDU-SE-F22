package dk.sdu.se_f22.sharedlibrary;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

class SearchHitsTest {
    private SearchHits sh;
    private Collection<Product> products;
    private Collection<Brand> brands;
    private Collection<Content> contents;

    @BeforeEach
    void setUp() {
        // Test search hits start of empty
        this.sh = new SearchHits();

        this.products = this.sh.getProducts();
        this.sh.setProducts(new ArrayList<Product>());

        this.brands = this.sh.getBrands();
        this.sh.setBrands(new ArrayList<Brand>());

        this.contents = this.sh.getContents();
        this.sh.setContents(new ArrayList<Content>());
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Set empty collection")
    @Nested
    public class SetEmptySearchHitsTest {
        @DisplayName("Set empty products test")
        @Test
        void setEmptyProducts() {
            SearchHits currentSh = new SearchHits();
            currentSh.setProducts(new ArrayList<>());
            assertEquals(currentSh.getProducts(), new SearchHits().getProducts());
        }

        @DisplayName("Set empty brands test")
        @Test
        void setEmptyBrands() {
            SearchHits currentSh = new SearchHits();
            currentSh.setBrands(new ArrayList<>());
            assertEquals(new ArrayList<>(), new SearchHits().getBrands());
        }

        @DisplayName("Set empty content test")
        @Test
        void setEmptyContents() {
            SearchHits currentSh = new SearchHits();
            currentSh.setContents(new ArrayList<>());
            assertEquals(new ArrayList<>(), new SearchHits().getContents());
        }
    }

    @DisplayName("Get empty collection")
    @Nested
    public class GetEmptySearchHitsTest {
        @DisplayName("Get empty products test")
        @Test
        void getEmptyProducts() {
            assertEquals(new ArrayList<>(), new SearchHits().getProducts());
        }

        @DisplayName("Get empty brands test")
        @Test
        void getEmptyBrands() {
            assertEquals(new ArrayList<>(), new SearchHits().getBrands());
        }

        @DisplayName("Get empty content test")
        @Test
        void getEmptyContents() {
            assertEquals(new ArrayList<>(), new SearchHits().getContents());
        }
    }

    @DisplayName("Add elements through get methods")
    @Nested
    public class AddGetSearchHitsTest {
        @DisplayName("Add products through getProducts")
        @Test
        void addProductsGet() {
            SearchHits hits = new SearchHits();
            Collection<Product> productsHits = hits.getProducts();

            Product product1 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 1");
            productsHits.add(product1);

            Product product2 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 2");
            productsHits.add(product2);

            Product product3 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 3");
            productsHits.add(product3);

            assertNotEquals(productsHits, new SearchHits().getProducts());
        }

        @DisplayName("Add brands through getBrands")
        @Test
        void addBrandsGet() {
            SearchHits hits = new SearchHits();
            Collection<Brand> brandHits = hits.getBrands();

            brandHits.add(new Brand("Brand 1", "Demo", "1999", "Test city"));
            brandHits.add(new Brand("Brand 2", "Demo", "1999", "Test city"));
            brandHits.add(new Brand("Brand 3", "Demo", "1999", "Test city"));

            assertNotEquals(brandHits, new SearchHits().getBrands());
        }

        @DisplayName("Add contents through getContents")
        @Test
        void addContentsGet() {
            SearchHits hits = new SearchHits();
            Collection<Content> contentsHits = hits.getContents();

            contentsHits.add(new Content(1, "HTML!", "Finding the right laptop for school", "Current timestamp!"));
            contentsHits.add(new Content(2, "HTML!", "Finding the right laptop for school", "Current timestamp!"));
            contentsHits.add(new Content(3, "HTML!", "Finding the right laptop for school", "Current timestamp!"));

            assertNotEquals(contentsHits, new SearchHits().getContents());
        }
    }

    @DisplayName("Add elements through set methods")
    @Nested
    public class AddSetSearchHitsTest {
        @DisplayName("Add products through setProducts")
        @Test
        void addProductsSet() {
            SearchHits hits = new SearchHits();
            Collection<Product> productsHits = new ArrayList<>();

            Product product1 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 1");
            productsHits.add(product1);

            Product product2 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 2");
            productsHits.add(product2);

            Product product3 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 3");
            productsHits.add(product3);

            hits.setProducts(productsHits);

            assertEquals(productsHits, hits.getProducts());
        }

        @DisplayName("Add brands through setBrands")
        @Test
        void addBrandsSet() {
            SearchHits hits = new SearchHits();
            Collection<Brand> brandHits = new ArrayList<>();

            brandHits.add(new Brand("Brand 1", "Demo", "1999", "Test city"));
            brandHits.add(new Brand("Brand 2", "Demo", "1999", "Test city"));
            brandHits.add(new Brand("Brand 3", "Demo", "1999", "Test city"));

            hits.setBrands(brandHits);

            assertEquals(brandHits, hits.getBrands());
        }

        @DisplayName("Add contents through setContents")
        @Test
        void addContentsSet() {
            SearchHits hits = new SearchHits();
            Collection<Content> contentsHits = new ArrayList<>();

            contentsHits.add(new Content(1, "HTML!", "Finding the right laptop for school", "Current timestamp!"));
            contentsHits.add(new Content(2, "HTML!", "Finding the right laptop for school", "Current timestamp!"));
            contentsHits.add(new Content(3, "HTML!", "Finding the right laptop for school", "Current timestamp!"));

            hits.setContents(contentsHits);

            assertEquals(contentsHits, hits.getContents());
        }
    }

    @DisplayName("Add elements through add methods")
    @Nested
    public class AddSearchHitsTest {
        @DisplayName("Add products through addProduct")
        @Test
        void addProductsGet() {
            SearchHits hits = new SearchHits();
            Collection<Product> productsHits = new ArrayList<>();

            Product product1 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 1");
            productsHits.add(product1);
            hits.addProduct(product1);

            Product product2 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 2");
            productsHits.add(product2);
            hits.addProduct(product2);

            Product product3 = new Product(
                    new UUID(1, 1),
                    2.2,
                    new ArrayList<String>(),
                    1,
                    1.2,
                    Instant.now(),
                    Instant.now(),
                    "cat",
                    "name",
                    "description 3");
            productsHits.add(product3);
            hits.addProduct(product3);

            assertNotEquals(productsHits, new SearchHits().getProducts());
        }

        @DisplayName("Add brands through addBrand")
        @Test
        void addBrandsGet() {
            SearchHits hits = new SearchHits();
            Collection<Brand> brandHits = new ArrayList<>();

            Brand brand1 = new Brand("Brand 1", "Demo", "1999", "Test city");
            brandHits.add(brand1);
            hits.addBrand(brand1);
            Brand brand2 = new Brand("Brand 2", "Demo", "1999", "Test city");
            brandHits.add(brand2);
            hits.addBrand(brand1);
            Brand brand3 = new Brand("Brand 3", "Demo", "1999", "Test city");
            brandHits.add(brand3);
            hits.addBrand(brand1);

            assertNotEquals(brandHits, new SearchHits().getBrands());
        }

        @DisplayName("Add contents through addContent")
        @Test
        void addContentsGet() {
            SearchHits hits = new SearchHits();
            Collection<Content> contentsHits = new ArrayList<>();

            Content content1 = new Content(1, "HTML!", "Finding the right laptop for school", "Current timestamp!");
            contentsHits.add(content1);
            hits.addContent(content1);
            Content content2 = new Content(2, "HTML!", "Finding the right laptop for school", "Current timestamp!");
            contentsHits.add(content2);
            hits.addContent(content2);
            Content content3 = new Content(3, "HTML!", "Finding the right laptop for school", "Current timestamp!");
            contentsHits.add(content3);
            hits.addContent(content3);

            assertNotEquals(contentsHits, new SearchHits().getContents());
        }
    }

    @DisplayName("Null set prohibiting")
    @Nested
    public class NullSetSearchHitsTest {
        @Test
        void NullProductsSet() {
            SearchHits hits = new SearchHits();
            assertThrows(NullPointerException.class, () -> hits.setProducts(null));
        }

        @Test
        void NullBrandsSet() {
            SearchHits hits = new SearchHits();
            assertThrows(NullPointerException.class, () -> hits.setBrands(null));
        }

        @Test
        void NullContentsSet() {
            SearchHits hits = new SearchHits();
            assertThrows(NullPointerException.class, () -> hits.setContents(null));
        }
    }
}