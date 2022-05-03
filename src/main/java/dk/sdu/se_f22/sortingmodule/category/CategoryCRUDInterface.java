package dk.sdu.se_f22.sortingmodule.category;

import dk.sdu.se_f22.sortingmodule.category.domain.CategoryDBConnection;

import java.util.List;

public interface CategoryCRUDInterface {

    List<Category> getAllCategories();
}
