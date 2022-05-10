package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sortingmodule.category.Category;

import java.util.List;

public interface CategoryCRUDInterface {

    void createCategory(String name, String description, String requirementsValue, int parentID, int requirementsFieldName);

    void createCategory(String name, String description, String requirementsValue, int requirementsFieldName);

    List<Category> getAllCategories();

    Category getCategoryById(int id);

    void updateCategory(String fieldName, String updatedString, int id);

    void updateCategory(String fieldName, int updatedInt, int id);

    int deleteCategory(int id);
}
