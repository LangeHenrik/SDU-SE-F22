package dk.sdu.se_f22.sortingmodule.category.domain;

<<<<<<< HEAD
public class TemporaryConnectionTest {

    public static void main(String[] args) {
        UpdateCategory updater = new UpdateCategory();


        System.out.println(updater.updateParentID(2,3));
        /*updater.updateName(31,"Kevin");
        updater.updateDescription(31,"Kevin spiller WoW");*/

=======
import dk.sdu.se_f22.sortingmodule.category.Category;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TemporaryConnectionTest {

    public static void main(String[] args) {
        /*
        CategoryDBConnection db = CategoryDBConnection.shared;
        CategoryDBConnection.shared.connect();

        try {
            PreparedStatement instertStatement = CategoryDBConnection.shared.getConnie().prepareStatement(
                    "INSERT INTO Categories(name, description) VALUES (?,?)"
            );
            instertStatement.setString(1, "test");
            instertStatement.setString(2, "En lang test da dette er en beskrivelse");
            instertStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        CategoryDBConnection.shared.closeConnection();
         */


        List<Category> categoryList = CategoryRead.shared.getAllCategories();
        for (Category category : categoryList) {
            System.out.println(category);
        }
        System.out.println(CategoryRead.shared.getCategoryById(10));
        System.out.println(CategoryRead.shared.getCategoryById(8));
>>>>>>> dev
    }
}
