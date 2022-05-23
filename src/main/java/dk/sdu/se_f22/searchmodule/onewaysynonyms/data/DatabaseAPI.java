package dk.sdu.se_f22.searchmodule.onewaysynonyms.data;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.Item;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseAPI {

    static Connection connection;

    static {
        try {
            connection = DBConnection.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //for adding superItem
    public static void addItem(String itemName, String superItemName) {
        if (itemName.equalsIgnoreCase("root")) {
            System.out.println("Could not add " + itemName);
            return;
        }
        PreparedStatement insertStatement = null;

        try {
            insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
            insertStatement.setString(1, itemName);
            insertStatement.setInt(2, searchBasedOnName(superItemName));
            insertStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Item: " + itemName + " was added");
        } catch (SQLException e) {
            System.out.println("Could not add: " + itemName);
            e.printStackTrace();
        }
    }

    //for adding subItem
    public static void addItem(String itemName, int superId) {
        if (itemName.equalsIgnoreCase("root")) {
            System.out.println("Could not add " + itemName);
            return;
        }
        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
            insertStatement.setString(1, itemName);
            insertStatement.setInt(2, superId);
            insertStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Item: " + itemName + " with super ID: " + superId + " was added");
        } catch (SQLException e) {
            System.out.println("Could not add " + itemName + " with super ID: " + superId);
            e.printStackTrace();
        }
    }

    public static void updateSuperId(int id, int superId) {
        if (id == 0) {
            System.out.println("Could not update root");
            return;
        }
        if (id == superId) {
            System.out.println("Cannot update super id to be the same as the item id");
            return;
        }
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement("UPDATE items SET superId=? WHERE id=?");
            updateStatement.setInt(1, superId);
            updateStatement.setInt(2, id);
            updateStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Updated Super ID: " + superId);
        } catch (SQLException e) {
            System.out.println("Could not update Super ID: " + superId);
            e.printStackTrace();
        }
    }

    public static void updateName(int id, String name) {
        if (id == 0) {
            System.out.println("Could not update root");
            return;
        }
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement("UPDATE items SET name=? WHERE id=?");
            updateStatement.setString(1, name);
            updateStatement.setInt(2, id);
            updateStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Updated Name: " + name);
        } catch (SQLException e) {
            System.out.println("Unable to update name: " + name);
            e.printStackTrace();
        }
    }

    public static Item[] readEntireDB() {
        try {
            ArrayList<Item> items = new ArrayList<Item>();
            PreparedStatement quaryStatement = connection.prepareStatement("SELECT * FROM items ORDER BY id");
            ResultSet quaryResultSet = null;
            quaryResultSet = quaryStatement.executeQuery();

            while (quaryResultSet.next()) {
                items.add(new Item(quaryResultSet.getInt(1), quaryResultSet.getString(2), quaryResultSet.getInt(3)));
            }
            for (Item item : items) {
                if (!item.getName().equalsIgnoreCase("root")) {
                    for (Item item2 : items) {
                        if (item2.getId() == item.getSuperId()) {
                            item.setSuperItem(item2);
                            item2.addSubItem(item);
                        }
                    }
                }
            }
            Item[] item = items.toArray(new Item[items.size()]);
            System.out.println("The read was a succes");
            return item;
        } catch (SQLException e) {
            System.out.println("Cant read :/");
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteItems(int id, String name) {
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement("DELETE FROM items WHERE name=? AND id=?");
            deleteStatement.setString(1, name);
            deleteStatement.setInt(2, id);
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteItems(int id) {
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement("DELETE FROM items WHERE id=?");
            deleteStatement.setInt(1, id);
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int searchBasedOnName(String name) {
        int id = -1;
        if (name == null) {
            return id;
        }
        PreparedStatement statement = null;
        ResultSet result;
        try {
            statement = connection.prepareStatement("SELECT id FROM items WHERE name=?");
            statement.setString(1, name);
            result = statement.executeQuery();
            while (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
