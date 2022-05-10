package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sortingmodule.category.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDBConnectionTest {

    @ParameterizedTest
    @DisplayName("Read valid category")
    @CsvFileSource(resources = "CategoriesInDatabase.csv", numLinesToSkip = 1)
    void getCategoryById(int id, String name, String description, int parentId, String value, String status) {
        Category categoryActual = CategoryDBConnection.shared.getCategoryById(id);
        Category categoryExpected = new Category(id, name, description, parentId, status, value);

        assertEquals(categoryExpected, categoryActual);

    }
}