package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class CategoryFilterTest {


    @Nested
    class TestReqExAdd {
        private List<Product> testProducts;
        private SearchHits searchHit;
        private CategoryFilter categoryFilter;
        @BeforeEach
        void setUp(){
            testProducts = new ArrayList<Product>();
            testProducts.add(new Product(UUID.randomUUID(), 1.0, new ArrayList<String>(), 1, 1.0, Instant.MAX, Instant.MIN, "Components/Processors", "Intel Xeon X3320 processor 2.5 GHz 6 MB L2", "Intel Xeon X3320, Intel Xeon, LGA 775"));

            searchHit = new SearchHits();
            categoryFilter = new CategoryFilter();
            searchHit.setProducts(testProducts);
        }

        @Test
        void FieldNameCategoryRegEx(){
            categoryFilter.filterProductsByCategory(searchHit, new ArrayList<>(3));

            assert
        }


    }

    @Test
    @DisplayName("Category Filter Contains Products")
    void categoryFilter() {

    }

    @Test
    @DisplayName("Category Filter Can ")
    void categoryFilterCheckIfContainsProducts() {

    }

    @AfterEach
    void tearDown() {

    }
}