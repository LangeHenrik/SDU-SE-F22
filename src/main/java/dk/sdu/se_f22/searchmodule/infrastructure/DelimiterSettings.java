package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DelimiterSettings {
    private List<String> delimiters;

    public List<String> getDelimiters() {
        try {
            updateDelimitersToDatabaseState();
            return delimiters;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateDelimitersToDatabaseState() throws SQLException {
        ResultSet resultSet = getAllDelimitersFromDatabase();
        resetDelimiterAttribute();
        moveResultsSetToDelimitersAttribute(resultSet);
        resultSet.close();
    }

    private ResultSet getAllDelimitersFromDatabase() throws SQLException {
        PreparedStatement stmt = DBConnection.getConnection().prepareStatement("SELECT * FROM searchtokendelimiters");
        return stmt.executeQuery();
    }

    private void resetDelimiterAttribute() {
        delimiters = new ArrayList<>();
    }

    private void moveResultsSetToDelimitersAttribute(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            delimiters.add(resultSet.getString(1));
        }
    }

    public void addDelimiter(String delimiter) {
        try {
            insertDelimiterIntoDatabase(delimiter);
            System.out.println("Delimiter added.");
        } catch (PSQLException ex) {
            System.out.println("This delimiter already exist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertDelimiterIntoDatabase(String delimiter) throws SQLException {
        PreparedStatement stmt = prepareDeleteStatement("INSERT INTO searchtokendelimiters (delimiter) VALUES (?)", delimiter);
        stmt.execute();
    }

    public boolean removeDelimiter(String delim) {
        try {
            PreparedStatement stmt = prepareDeleteStatement("DELETE FROM searchtokendelimiters WHERE delimiter=?", delim);
            return executeStatementIfDelimIsInDelimiters(delim, stmt);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeStatementIfDelimIsInDelimiters(String delim, PreparedStatement stmt) throws SQLException {
        for (String s : getDelimiters()) {
            if (s.equals(delim)) {
                stmt.execute();
                return true;
            }
        }
        return false;
    }

    private PreparedStatement prepareDeleteStatement(String sql, String delim) throws SQLException {
        PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
        stmt.setString(1, delim);
        return stmt;
    }
}
