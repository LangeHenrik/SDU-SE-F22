package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.*;

public class DatabaseAPI {

    static Connection connection = DBConnection.getConnection();
    //for adding superItem

        public static void addItem(String itemName) throws SQLException {
        PreparedStatement insertStatement = null;
        insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
        insertStatement.setString(1, itemName);
        insertStatement.setString(2, "0");
        insertStatement.execute();
    }

    public static void addItem(String itemName, int superId) throws SQLException {
        PreparedStatement insertStatement = null;
        insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
        insertStatement.setString(1, itemName);
        insertStatement.setString(2, String.valueOf(superId));
        insertStatement.execute();
    }

    public static void updateSuperId(String name, int superId) throws SQLException {
        PreparedStatement updateStatement = null;
        updateStatement = connection.prepareStatement("UPDATE items SET superId=? WHERE name=?");
        updateStatement.setString(1, String.valueOf(superId));
        updateStatement.setString(2, name);
        updateStatement.execute();
    }

    public static void updateName(int id, String name) throws SQLException {
        PreparedStatement updateStatement = null;
        updateStatement = connection.prepareStatement("UPDATE items SET name=? WHERE id=?");
        updateStatement.setString(1, String.valueOf(name));
        updateStatement.setInt(2, id);
        updateStatement.execute();
    }

    public static ResultSet read() {
        try {
            PreparedStatement quaryStatement = connection.prepareStatement("SELECT * FROM items");
            ResultSet quaryResultSet = null;
            quaryResultSet = quaryStatement.executeQuery();

            return quaryResultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteItem(int id, String name){
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement("DELETE FROM items WHERE name=? AND id=?");
            deleteStatement.setString(1, String.valueOf(name));
            deleteStatement.setString(2, String.valueOf(id));
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
