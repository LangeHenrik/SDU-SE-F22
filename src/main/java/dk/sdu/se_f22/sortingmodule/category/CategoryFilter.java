package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.List;

public class CategoryFilter {

    protected static SearchHits filterProductsByCategory(SearchHits searchHits, List categoryIDs) {
        SearchHits newHits = new SearchHits();

        for (Object oldProduct : searchHits.getProducts()) {
            
        }

        return newHits;
    }
}
