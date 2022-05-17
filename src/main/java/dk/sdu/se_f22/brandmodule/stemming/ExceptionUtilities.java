package dk.sdu.se_f22.brandmodule.stemming;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ExceptionUtilities {
    public static void createException(String name) throws SQLException {
        String query = String.format("INSERT INTO StemmingException (name) VALUES ('%s')", name);
        ResultSet result = DBConnection.getConnection().createStatement().executeQuery(query);
        result.close();
    }
    public static HashMap<Integer, String> getExceptions() throws SQLException {
        String query = String.format("SELECT * FROM StemmingException");
        ResultSet result = DBConnection.getConnection().createStatement().executeQuery(query);
        result.close();
        return handleResult(result);

    }
    public static HashMap<Integer, String> findException(String name) throws SQLException {
        String query = String.format("SELECT * FROM StemmingException WHERE name = '%s';", name);
        ResultSet result = DBConnection.getConnection().createStatement().executeQuery(query);
        result.close();
        return handleResult(result);
    }

    private static HashMap<Integer, String> handleResult(ResultSet result) throws SQLException {
        HashMap<Integer, String> map = new HashMap<>();
        while(result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            map.put(id, name);
        }
        return map;
    }
}
