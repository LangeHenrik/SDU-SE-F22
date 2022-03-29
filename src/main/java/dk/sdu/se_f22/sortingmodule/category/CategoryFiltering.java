package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.category.domain.CategoryDBConnection;

import java.util.List;

public class CategoryFiltering {

    public static List<Category> getAllCategories() {
        return CategoryDBConnection.shared.getAllCategories();
    }

    public static SearchHits filterProductsByCategory(SearchHits searchHits, List<Integer> categoryIDs) {
        return CategoryFilter.filterProductsByCategory(searchHits, categoryIDs);
    }
}
