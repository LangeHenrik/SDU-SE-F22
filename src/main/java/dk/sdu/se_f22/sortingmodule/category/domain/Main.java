package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sortingmodule.category.Category;
import dk.sdu.se_f22.sortingmodule.category.CategoryFilter;
import dk.sdu.se_f22.sortingmodule.category.CategoryFilterInterface;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        CategoryCRUDInterface categoryCRUD = new CategoryCRUD();
        CategoryFilterInterface categoryFilter = new CategoryFilter();

        List<Product> products = new ArrayList<Product>();
        products.add(new Product(UUID.randomUUID(), 1.0, new ArrayList<String>(), 1, 1.0, Instant.MAX, Instant.MIN, "Components/Processors", "Intel Xeon X3320 processor 2.5 GHz 6 MB L2", "Intel Xeon X3320, Intel Xeon, LGA 775"));

        SearchHits searchHit = new SearchHits();
        searchHit.setProducts(products);

        List<Category> categories = categoryCRUD.getAllCategories();

        List<Integer> ids = new ArrayList<Integer>();
        ids.add(3);
        ids.add(5);

        SearchHits filteredSearchHit = categoryFilter.filterProductsByCategory(searchHit, ids);
    }
}
