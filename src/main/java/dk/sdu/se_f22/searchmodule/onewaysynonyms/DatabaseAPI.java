package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;

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

    public static Item[] readEntireDB() {
        try {
            ArrayList<Item> items = new ArrayList<Item>();
            PreparedStatement quaryStatement = connection.prepareStatement("SELECT * FROM items");
            ResultSet quaryResultSet = null;
            quaryResultSet = quaryStatement.executeQuery();
            while (quaryResultSet.next()) {
                items.add(new Item(quaryResultSet.getInt(1), quaryResultSet.getString(2), quaryResultSet.getInt(3)));
            }
            for (Item item : items) {
                if (item.getSuperId() != 0) {
                    for (Item item2 : items) {
                        if (item2.getId() == item.getSuperId())
                            item.setSuperItem(item2);
                    }
                }
            }
            Item[] item = items.toArray(new Item[items.size()]);
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    public static ResultSet readDB(String name) {
        try {
            PreparedStatement quaryStatement = connection.prepareStatement("SELECT * FROM items WHERE name = ?");
            quaryStatement.setString(1, name);
            ResultSet quaryResultSet = quaryStatement.executeQuery();
            while (quaryResultSet.next()) {
                if (quaryResultSet.getInt(1) != 0) {

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
    public static void deleteItem(int id, String name) {
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
