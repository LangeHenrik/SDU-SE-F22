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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet read(){
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