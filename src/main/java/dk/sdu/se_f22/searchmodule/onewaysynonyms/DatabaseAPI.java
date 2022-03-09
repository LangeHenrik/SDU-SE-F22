package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import java.sql.*;

public class DatabaseAPI {

    Connection connection = null;

    public DatabaseAPI(String url,String username,String password){
    }

    public static void create(String itemName){
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
        insertStatement.setString(1,itemName);
        insertStatement.setString(2,0;
    }

    public static void create(String itemName, int superId){
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO items (name,superId) VALUES (?,?)");
        insertStatement.setString(1,itemName);
        insertStatement.setString(2, String.valueOf(superId));
    }

    public static void updateSuperId(int id,int superId) {
        PreparedStatement updateStatement = connect.prepareStatement("UPDATE items SET superId=? WHERE name=?");
        updateStatement.setString(1,String.valueOf(superId));
        updateStatement.setString(2,String.valueOf(id));
    }

    public static void updateName(int id,String name) {
        PreparedStatement updateStatement = connect.prepareStatement("UPDATE items SET name=? WHERE name=?");
        updateStatement.setString(1,String.valueOf(name));
        updateStatement.setString(2,String.valueOf(id));
    }



}
