package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.category.domain.CategoryCRUD;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryFilterTest {

    @Nested
    @DisplayName("Test RegEx Add on category")
    class TestReqExAddOnCategory {
        private List<Product> testProducts;
        private SearchHits searchHit;
        private CategoryFilter categoryFilter;

        @BeforeEach
        void setUp() {
            testProducts = new ArrayList<Product>();
            testProducts.add(new Product(UUID.randomUUID(), 1.0, new ArrayList<String>(), 1, 1.0, Instant.MAX, Instant.MIN, "Components/Processors", "Intel Xeon X3320 processor 2.5 GHz 6 MB L2", "Intel Xeon X3320, Intel Xeon, LGA 775"));

            searchHit = new SearchHits();
            categoryFilter = new CategoryFilter();
            searchHit.setProducts(testProducts);
        }

        @Test
        @DisplayName("Read all, check object type")
        void fieldNameCategoryRegEx() {
            ArrayList<Integer> testCategoryIDs = new ArrayList<Integer>();
            testCategoryIDs.add(3);

            categoryFilter.filterProductsByCategory(searchHit, testCategoryIDs);

            assertTrue(searchHit.getProducts().size() == 1);
        }

        @AfterEach
        void tearDown() {
            searchHit = null;
        }
    }

    @Nested
    @DisplayName("Test RegEx Add on name")
    class TestReqExAddOnName {
        private List<Product> testProducts;
        private SearchHits searchHit;
        private CategoryFilter categoryFilter;
        private int testCategoryID;

        @BeforeEach
        void setUp() {
            testCategoryID = new CategoryCRUD().createCategory("Intel", "Category with Intel products", "Intel", 1);

            testProducts = new ArrayList<Product>();
            testProducts.add(new Product(UUID.randomUUID(), 1.0, new ArrayList<String>(), 1, 1.0, Instant.MAX, Instant.MIN, "Components/Processors", "Intel Xeon X3320 processor 2.5 GHz 6 MB L2", "Intel Xeon X3320, Intel Xeon, LGA 775"));

            searchHit = new SearchHits();
            categoryFilter = new CategoryFilter();
            searchHit.setProducts(testProducts);
        }

        @Test
        @DisplayName("Read all, check object type")
        void fieldNameCategoryRegEx() {
            ArrayList<Integer> testCategoryIDs = new ArrayList<Integer>();
            testCategoryIDs.add(testCategoryID);

            categoryFilter.filterProductsByCategory(searchHit, testCategoryIDs);

            assertTrue(searchHit.getProducts().size() == 1);
        }

        @AfterEach
        void tearDown() {
            new CategoryCRUD().deleteCategory(testCategoryID);

            searchHit = null;
        }
    }
}