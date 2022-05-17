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

    public static void addItem(String itemName){
        PreparedStatement insertStatement = null;

        try {
            insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
            insertStatement.setString(1,itemName);
            insertStatement.setInt(2,0);
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
        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
            insertStatement.setString(1,itemName);
            insertStatement.setInt(2, superId);
            insertStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Item: " + itemName + " with super ID: " + superId +  " was added");
        } catch (SQLException e) {
            System.out.println("Could not add " + itemName + " with super ID: " + superId);
            e.printStackTrace();
        }
    }

    public static void updateSuperId(String name, int superId) {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement("UPDATE items SET superId=? WHERE name=?");
            updateStatement.setInt(1, superId);
            updateStatement.setString(2, name);
            updateStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Updated Super ID: " + superId);
        } catch (SQLException e) {
            System.out.println("Could not update Super ID: " + superId);
            e.printStackTrace();
        }
    }

    public static void updateName(int id, String name){
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
            System.out.println("The read was a succes");
            return item;
        } catch (SQLException e) {
            System.out.println("Cant read :/");
            e.printStackTrace();
        }
        return null;
    }

public static void deleteItems(boolean version, int id, String name) {
    PreparedStatement deleteStatement = null;
    if (version) {
        try {
            deleteStatement = connection.prepareStatement("DELETE FROM items WHERE name=? AND id=?");
            deleteStatement.setString(1, String.valueOf(name));
            deleteStatement.setInt(2, id);
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    else {
        try {
            deleteStatement = connection.prepareStatement("DELETE FROM items WHERE id=?");
            deleteStatement.setString(1, String.valueOf(id));
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    public static int searchBasedOnName(String name){
        int id = -1;
        if(name == null){
            return id;
        }
        PreparedStatement statement = null;
        ResultSet result;
        try {
            statement = connection.prepareStatement("SELECT id FROM items WHERE name=?");
            statement.setString(1,name);
            result = statement.executeQuery();
            while(result.next()){
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
