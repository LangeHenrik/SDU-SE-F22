package dk.sdu.se_f22.sortingmodule.category.domain;

import java.sql.*;

public class UpdateCategory {

    CategoryDBConnection db = CategoryDBConnection.shared;

    public int updateName(int idToChange, String changeTo) {
        String sql = "UPDATE Categories SET name = ? WHERE id = ?";
        try (Connection conn = CategoryDBConnection.shared.connect();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {
            if (changeTo.length() > 0) {
                try {
                    pStatement.setString(1,changeTo);
                    pStatement.setInt(2,idToChange);

                    int affectedRows = pStatement.executeUpdate();
                    if (affectedRows == 0) {
                        System.out.println("That ID does not exist");
                    }
                    return affectedRows;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (changeTo.length() == 0) {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public int updateDescription(int idToChange, String changeTo) {
        String sql = "UPDATE Categories SET description = ? WHERE id = ?";
        try (Connection conn = CategoryDBConnection.shared.connect();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {
            if (changeTo.length() > 0) {
                try {
                    pStatement.setString(1,changeTo);
                    pStatement.setInt(2,idToChange);

                    int affectedRows = pStatement.executeUpdate();
                    if (affectedRows == 0) {
                        System.out.println("That ID does not exist");
                    }
                    return affectedRows;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (changeTo.length() == 0) {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public int updateParentID(int idToChange, int changeTo) {
        String sql = "UPDATE Categories SET parent_id = ? WHERE id = ?";
        try (Connection conn = CategoryDBConnection.shared.connect();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {
            if (changeTo > 0) {
                try {
                    pStatement.setInt(1,changeTo);
                    pStatement.setInt(2,idToChange);

                    int affectedRows = pStatement.executeUpdate();
                    if (affectedRows == 0) {
                        System.out.println("That ID does not exist");
                    }
                    return affectedRows;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (changeTo == 0) {
                System.out.println("Parent ID must be an integer beyond 0");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
}

