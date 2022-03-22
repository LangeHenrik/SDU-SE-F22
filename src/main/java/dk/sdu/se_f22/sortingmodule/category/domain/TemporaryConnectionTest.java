package dk.sdu.se_f22.sortingmodule.category.domain;

import dk.sdu.se_f22.sortingmodule.category.Category;
import java.util.List;

public class TemporaryConnectionTest {

    public static void main(String[] args) {

        CategoryDBConnection.shared.createCategory("Storageeee", "Beskrivelse", "Storageeee", 3);
        CategoryDBConnection.shared.createCategory("Laptopeee", "Beskrivelse", "Laptopeee", 1, 2);
        //CategoryDBConnection.createCategory("PC", "Beskrivelse", "PC", 2, 2);

        List<Category> categoryList = CategoryDBConnection.shared.getAllCategories();
        for (Category category : categoryList) {
            System.out.println(category);
        }

        CategoryDBConnection.shared.updateName(1, "Storagee");
        CategoryDBConnection.shared.updateDescription(2, "Beskrivelse 2");
        CategoryDBConnection.shared.updateParentID(3,1);

        System.out.println("AFTER UPDATE");
        List<Category> categoryList2 = CategoryDBConnection.shared.getAllCategories();
        for (Category category : categoryList2) {
            System.out.println(category);
        }

        System.out.println("AFTER DELETE");
        CategoryDBConnection.shared.deleteCategory(3);
        List<Category> categoryList3 = CategoryDBConnection.shared.getAllCategories();
        for (Category category : categoryList3) {
            System.out.println(category);
        }
    }
}
