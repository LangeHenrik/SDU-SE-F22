package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sortingmodule.category.Category;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class DeleteCategoryTest {

    @Test
    void testDeleteCategoryTest() {
        //CategoryDBConnection deleteMethod = new CategoryDBConnection();
        CategoryDBConnection.shared.createCategory("PC", "Description", "RValue",1);
        CategoryDBConnection.shared.createCategory("PC", "Description1", "RValue1",2);

        Category cs = CategoryDBConnection.shared.getCategoryById(1);
        System.out.println(cs);
        int del = CategoryDBConnection.shared.deleteCategory(1);
        assertEquals(1,CategoryDBConnection.shared.deleteCategory(1));
        assertEquals(1, del);
    }
}
