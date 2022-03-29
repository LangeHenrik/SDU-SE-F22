package dk.sdu.se_f22.sortingmodule.category.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDBDelete {
    public static CategoryDBDelete shared = new CategoryDBDelete();

    public int deleteCategory(int id) {
        String SQL = "DELETE FROM Categories WHERE category_id = ?";

        int affectedrows = 0;

        try (Connection conn = CategoryDBConnection.shared.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }
}

