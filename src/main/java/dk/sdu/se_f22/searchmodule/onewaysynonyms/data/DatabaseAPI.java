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

    public static boolean initializeTable(){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS onewaysynonyms (" +
                    "id serial primary key, " +
                    "name varchar, " +
                    "superid integer)");
            preparedStatement.execute();
            if (searchBasedOnName("root")==-1){
                preparedStatement = connection.prepareStatement("INSERT INTO onewaysynonyms (name, superid) values (?,?)");
                preparedStatement.setString(1,"root");
                preparedStatement.setInt(2,0);
                preparedStatement.execute();
                preparedStatement = connection.prepareStatement("UPDATE onewaysynonyms SET id=? WHERE name=?");
                preparedStatement.setInt(1,0);
                preparedStatement.setString(2,"root");
                preparedStatement.execute();
            }
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //for adding subItem
    public static boolean addItem(String itemName, int superId) {
        if (itemName.equalsIgnoreCase("root")) {
            return false;
        }
        PreparedStatement insertStatement;
        try {
            insertStatement = connection.prepareStatement("INSERT INTO onewaysynonyms (name,superId) VALUES (?,?)");
            insertStatement.setString(1, itemName);
            insertStatement.setInt(2, superId);
            insertStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateSuperId(int id, int superId) {
        if (id == 0) {
            return false;
        }
        if (id == superId) {
            return false;
        }
        PreparedStatement updateStatement;
        try {
            updateStatement = connection.prepareStatement("UPDATE onewaysynonyms SET superId=? WHERE id=?");
            updateStatement.setInt(1, superId);
            updateStatement.setInt(2, id);
            updateStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateName(int id, String name) {
        if (id == 0) {
            return false;
        }
        PreparedStatement updateStatement;
        try {
            updateStatement = connection.prepareStatement("UPDATE onewaysynonyms SET name=? WHERE id=?");
            updateStatement.setString(1, name);
            updateStatement.setInt(2, id);
            updateStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Item[] readEntireDB() {
        try {
            ArrayList<Item> items = new ArrayList<>();
            PreparedStatement quarryStatement = connection.prepareStatement("SELECT * FROM onewaysynonyms ORDER BY id");
            ResultSet quarryResultSet;
            quarryResultSet = quarryStatement.executeQuery();

            while (quarryResultSet.next()) {
                items.add(new Item(quarryResultSet.getInt(1), quarryResultSet.getString(2), quarryResultSet.getInt(3)));
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

            return items.toArray(new Item[items.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteItems(int id) {
        if (id == 0) return false;
        PreparedStatement deleteStatement;
        try {
            deleteStatement = connection.prepareStatement("DELETE FROM onewaysynonyms WHERE id=?");
            deleteStatement.setInt(1, id);
            deleteStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int searchBasedOnName(String name) {
        int id = -1;
        if (name == null) {
            return id;
        }
        PreparedStatement statement;
        ResultSet result;
        try {
            statement = connection.prepareStatement("SELECT id FROM onewaysynonyms WHERE name=?");
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
