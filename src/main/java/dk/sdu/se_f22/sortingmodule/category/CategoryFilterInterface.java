package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;

import java.util.List;

public interface CategoryFilterInterface {

    SearchHits filterProductsByCategory(SearchHits searchHits, List<Integer> categoryIDs);
}
