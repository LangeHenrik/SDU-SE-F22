package dk.sdu.se_f22.brandmodule.stemming;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ExceptionUtilities {
    public static void createException(String name) throws SQLException {
        String query = String.format("INSERT INTO stemmingexceptions (exception) VALUES ('%s')", name);
        DBConnection.getPooledConnection().createStatement().executeUpdate(query);
    }
    public static HashMap<Integer, String> getExceptions() throws SQLException {
        String query = String.format("SELECT * FROM stemmingexceptions");
        ResultSet result = DBConnection.getPooledConnection().createStatement().executeQuery(query);
        return handleResult(result);
    }
    public static HashMap<Integer, String> getException(String name) throws SQLException {
        String query = String.format("SELECT * FROM stemmingexceptions WHERE exception = '%s'", name);
        ResultSet result = DBConnection.getPooledConnection().createStatement().executeQuery(query);
        return handleResult(result);

    }
    public static void updateException(String name, String newName) throws SQLException {
        String query = String.format("UPDATE stemmingexceptions SET exception = '%s' WHERE exception = '%s'", newName, name);
        DBConnection.getPooledConnection().createStatement().executeUpdate(query);
    }
    public static void deleteException(String name) throws SQLException {
        String query = String.format("DELETE FROM stemmingexceptions WHERE exception = '%s'", name);
        DBConnection.getPooledConnection().createStatement().executeUpdate(query);
    }


    private static HashMap<Integer, String> handleResult(ResultSet result) throws SQLException {
        HashMap<Integer, String> map = new HashMap<>();
        while(result.next()) {
            int id = result.getInt("id");
            String name = result.getString("exception");
            map.put(id, name);
        }
        result.close();
        return map;
    }
}
