package dk.sdu.se_f22.searchmodule.infrastructure.tokenization;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.db.LoggingProvider;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DelimiterSettings {
    private List<String> delimiters;
    private Connection dbConnection;
    private PreparedStatement stmt;

    public List<String> getDelimiters() {
        try {
            updateDelimitersToDatabaseState();
            return delimiters;
        } catch (SQLException e) {
            LoggingProvider.getLogger(DelimiterSettings.class).error("A critical error happened when getting search delimiters: " + e.getMessage());
            return null;
        }
    }

    private void updateDelimitersToDatabaseState() throws SQLException {
        PreparedStatement stmt = makePreparedStatement("SELECT * FROM searchtokendelimiters");
        ResultSet resultSet = stmt.executeQuery();
        resetDelimiterAttribute();
        moveResultsSetToDelimitersAttribute(resultSet);
        closeAll();
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
            LoggingProvider.getLogger(this.getClass()).info("Delimiter added.");
        } catch (PSQLException ex) {
            if (ex.getSQLState().equals("23505")){
                LoggingProvider.getLogger(this.getClass()).warn("This delimiter already exist (" + delimiter + ")");
                return;
            }
        } catch (SQLException e) {
            LoggingProvider.getLogger(DelimiterSettings.class).error("A critical error happened when add a search delimiter: " + e.getMessage());
        }
    }

    private void insertDelimiterIntoDatabase(String delimiter) throws SQLException {
        PreparedStatement stmt = makePreparedStatement("INSERT INTO searchtokendelimiters (delimiter) VALUES (?)");
        stmt.setString(1, delimiter);
        stmt.execute();
        closeAll();
    }

    public boolean removeDelimiter(String delim) {
        try {
            if (delimiterNotFound(delim)){
                customPrinter("Delimiter not found: " + delim);
                return false;
            }
            makePreparedStatement("DELETE FROM searchtokendelimiters WHERE delimiter=?");
            stmt.setString(1, delim);
            this.stmt.execute();
            closeAll();
            customPrinter("Removed delimiter: " + delim);
            return true;
        } catch (SQLException e) {
            LoggingProvider.getLogger(DelimiterSettings.class).error("A critical error happened when removing a search delimiter: " + e.getMessage());
            return false;
        }
    }

    private boolean delimiterNotFound(String delim) throws SQLException {
        updateDelimitersToDatabaseState();
        return !this.delimiters.contains(delim);
    }

    private Connection getDbConnection() throws SQLException {
        if (this.dbConnection == null || dbConnection.isClosed()) {
            this.dbConnection = DBConnection.getPooledConnection();
            return dbConnection;
        }
        return dbConnection;
    }

    private PreparedStatement makePreparedStatement(String SQLStatement) throws SQLException {
        this.stmt = getDbConnection().prepareStatement(SQLStatement);
        return this.stmt;
    }

    private void closeAll(){
        try {
            if (this.stmt != null && !this.stmt.isClosed()) {
                this.stmt.close();
            }
            if (this.dbConnection != null && !this.dbConnection.isClosed()){
                this.dbConnection.close();
            }
        } catch (SQLException e) {
            customPrinter(" Could not close connection and statement");
            e.printStackTrace();
        }
    }

    private void customPrinter(String s){
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "] [SEM-Infra, DelimiterSettings] " + s);
    }

}
