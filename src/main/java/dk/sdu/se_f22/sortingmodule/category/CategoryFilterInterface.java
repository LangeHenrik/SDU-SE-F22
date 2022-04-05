package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import dk.sdu.se_f22.sortingmodule.category.domain.CategoryDBConnection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface CategoryFilterInterface {

    SearchHits filterProductsByCategory(SearchHits searchHits, List<Integer> categoryIDs);
}
