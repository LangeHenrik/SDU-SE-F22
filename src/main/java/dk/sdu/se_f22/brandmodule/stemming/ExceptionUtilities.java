package dk.sdu.se_f22.brandmodule.stemming;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ExceptionUtilities {
    public static void createException(String name) throws SQLException {
        Connection connection = DBConnection.getPooledConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO stemmingexceptions (exception) VALUES (?)");
        statement.setString(1, name);
        statement.executeUpdate();
        connection.close();
    }

    public static HashMap<Integer, String> getExceptions() throws SQLException {
        Connection connection = DBConnection.getPooledConnection();
        String query = "SELECT * FROM stemmingexceptions";
        ResultSet result = connection.prepareStatement(query).executeQuery();
        return handleResult(result);
    }

    public static HashMap<Integer, String> getException(String name) throws SQLException {
        Connection connection = DBConnection.getPooledConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM stemmingexceptions WHERE exception = ?");
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        return handleResult(result);

    }

    public static void updateException(String name, String newName) throws SQLException {
        Connection connection = DBConnection.getPooledConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE stemmingexceptions SET exception = ?" + "WHERE exception = ?");
        statement.setString(1, newName);
        statement.setString(2, name);
        statement.executeUpdate();
        connection.close();
    }

    public static void deleteException(String name) throws SQLException {
        Connection connection = DBConnection.getPooledConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM stemmingexceptions " + "WHERE exception =  ?");
        statement.setString(1, name);
        statement.executeUpdate();
        connection.close();
    }

    private static HashMap<Integer, String> handleResult(ResultSet result) throws SQLException {
        HashMap<Integer, String> map = new HashMap<>();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("exception");
            map.put(id, name);
        }
        result.close();
        return map;
    }
}
