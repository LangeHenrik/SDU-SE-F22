package dk.sdu.se_f22.sortingmodule.category.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryDBCreate {
    public static void createCategory(String name, String description, String requirementsValue, int parentID, int requirementsStatus) {
        CategoryDBConnection db = CategoryDBConnection.shared;
        try {
            PreparedStatement createStatement = db.connect().prepareStatement(
                    "INSERT INTO requirements(value, status_id) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            createStatement.setString(1, requirementsValue);
            createStatement.setInt(2, requirementsStatus);

            createStatement.execute();
            ResultSet rs = createStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()){
                generatedKey = rs.getInt(1);
                PreparedStatement createCategoryStatement = db.connect().prepareStatement(
                        "INSERT INTO categories(name, description, parent_id, requirements_id) VALUES (?,?,?,?)"
                );
                createCategoryStatement.setString(1,name);
                createCategoryStatement.setString(2,description);
                createCategoryStatement.setInt(3,parentID);
                createCategoryStatement.setInt(4,generatedKey);
                createCategoryStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CategoryDBConnection.shared.closeConnection();
    }

    public static void createCategory(String name, String description, String requirementsValue, int requirementsStatus) {
        CategoryDBConnection db = CategoryDBConnection.shared;
        try {
            PreparedStatement createStatement = db.connect().prepareStatement(
                    "INSERT INTO requirements(value, status_id) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            createStatement.setString(1, requirementsValue);
            createStatement.setInt(2, requirementsStatus);

            createStatement.execute();
            ResultSet rs = createStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()){
                generatedKey = rs.getInt(1);
                PreparedStatement createCategoryStatement = db.connect().prepareStatement(
                        "INSERT INTO categories(name, description, requirements_id) VALUES (?,?,?)"
                );
                createCategoryStatement.setString(1,name);
                createCategoryStatement.setString(2,description);
                createCategoryStatement.setInt(3,generatedKey);
                createCategoryStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CategoryDBConnection.shared.closeConnection();
    }
}

