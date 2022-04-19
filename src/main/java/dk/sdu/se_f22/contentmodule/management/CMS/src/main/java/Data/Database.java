package Data;

import java.sql.*;

public class Database {
    private Connection connection = null;
    static protected Database database = null;

    private Database() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","______");
    }

    protected ResultSet Execute(String str) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(str);
        return insertStatement.executeQuery();
    }

    protected void executeVoidReturn(String str) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement(str);
        insertStatement.execute();
    }

    public static Database getDatabase() throws SQLException {
        if (database == null)
            database = new Database();
        return database;
    }
}
