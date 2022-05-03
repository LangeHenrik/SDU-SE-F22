package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.util.RegexUtils;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IllegalChars {

    public String removeForbiddenChars(String toSort) {
        toSort = toSort.replaceAll(RegexUtils.convertStringListToRegexString(illegalCharsFromDB()), "");
        return toSort;
    }

    public void addChar(String character) {
        //Adds illegal characters to database
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO illegalChars (characters) VALUES (?)");
            insertStatement.setString(1, character);
            insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> illegalCharsFromDB() {
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM illegalChars");
            ResultSet queryResultSet = queryStatement.executeQuery();
            List<String> strings = new ArrayList<>();
            while (queryResultSet.next()) {
                String em = queryResultSet.getString("characters");
                strings.add(em);
            }
            return strings;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeChar(String character) {
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM illegalChars WHERE characters=(?)");
            deleteStatement.setString(1, character);
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}