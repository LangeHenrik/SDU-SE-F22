package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.*;

public class DatabaseAPI {

    static Connection connection = null;

    public DatabaseAPI(){
        this.connection = DBConnection.getConnection();
    }

    public static void addItem(String itemName){
        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
            insertStatement.setString(1,itemName);
            insertStatement.setString(2, "0");
            insertStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Item: " + itemName + " was added");
        } catch (SQLException e) {
            System.out.println("Could not add: " + itemName);
            e.printStackTrace();
        }
    }

    public static void addItem(String itemName, int superId){
        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
            insertStatement.setString(1,itemName);
            insertStatement.setString(2,String.valueOf(superId));
            insertStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Item: " + itemName + " with super ID: " + superId +  " was added");
        } catch (SQLException e) {
            System.out.println("Could not add " + itemName + " with super ID: " + superId);
            e.printStackTrace();
        }
    }

    public static void updateSuperId(String name,int superId) {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement("UPDATE items SET superId=? WHERE name=?");
            updateStatement.setString(1, String.valueOf(superId));
            updateStatement.setString(2, name);
            updateStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Updated Super ID: " + superId);
        } catch (SQLException e) {
            System.out.println("Could not update Super ID: " + superId);
            e.printStackTrace();
        }
    }

    public static void updateName(int id,String name) {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement("UPDATE items SET name=? WHERE id=?");
            updateStatement.setString(1, String.valueOf(name));
            updateStatement.setString(2, String.valueOf(id));
            updateStatement.execute();
            System.out.println("Transaction was a succes");
            System.out.println("Updated Name: " + name);
        } catch (SQLException e) {
            System.out.println("Unable to update name: " + name);
            e.printStackTrace();
        }
    }

    public static ResultSet read(){
        try {
            PreparedStatement quaryStatement = connection.prepareStatement("SELECT * FROM items");
            ResultSet quaryResultSet = null;
            quaryResultSet = quaryStatement.executeQuery();
            System.out.println("The read was a succes");
            return quaryResultSet;
        } catch (SQLException e) {
            System.out.println("Cant read :/");
            e.printStackTrace();
        }
        return null;
    }
}
