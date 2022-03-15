package dk.sdu.se_f22.sortingmodule.category.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TemporaryConnectionTest {

    public static void main(String[] args) {
        CategoryDBConnection db = CategoryDBConnection.shared;
        CategoryDBConnection.shared.connect();

        try {
            PreparedStatement instertStatement = CategoryDBConnection.shared.getConnie().prepareStatement(
                    "INSERT INTO Categories(name, description) VALUES (?,?) "
            );
            instertStatement.setString(1, "test");
            instertStatement.setString(2, "En lang test da dette er en beskrivelse");
            instertStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        CategoryDBConnection.shared.closeConnection();
    }
}
