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
        Connection dbConnection = DBConnection.getPooledConnection();
        PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM searchtokendelimiters");
        ResultSet resultSet = stmt.executeQuery();
        resetDelimiterAttribute();
        moveResultsSetToDelimitersAttribute(resultSet);
        resultSet.close();
        dbConnection.close();
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
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")){
                System.out.println("Delimiter already exists");
                return;
            }
            e.printStackTrace();
        }
    }

    private void insertDelimiterIntoDatabase(String delimiter) throws SQLException {
        Connection dbConnection = DBConnection.getPooledConnection();
        PreparedStatement stmt = dbConnection.prepareStatement("INSERT INTO searchtokendelimiters (delimiter) VALUES (?)");
        stmt.setString(1, delimiter);
        stmt.execute();
        stmt.close();
        dbConnection.close();
    }

    public boolean removeDelimiter(String delim) {
        try {
            Connection dbConnection = DBConnection.getPooledConnection();
            PreparedStatement stmt = dbConnection.prepareStatement("DELETE FROM searchtokendelimiters WHERE delimiter=?");
            stmt.setString(1, delim);
            stmt.execute();
            System.out.println("Removed delimiter: " + delim);
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeStatementIfDelimIsInDelimiters(String delim, PreparedStatement stmt) throws SQLException {
        for (String s : getDelimiters()) {
            if (s.equals(delim)) {
                stmt.execute();
                stmt.close();
                return true;
            }
        }
        stmt.close();
        return false;
    }
}
