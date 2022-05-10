package dk.sdu.se_f22.sortingmodule.category.domain;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class DeleteCategoryTest {


    @Test
    void testDeleteCategoryTest() {
        //CategoryDBConnection deleteMethod = new CategoryDBConnection();
        CategoryDBConnection.shared.createCategory("PC", "Description", "RValue",5);
        int del = CategoryDBConnection.shared.deleteCategory(1);
        //assertEquals(5,CategoryDBConnection.shared.deleteCategory(1));
        assertEquals(1, del);


    }
}
