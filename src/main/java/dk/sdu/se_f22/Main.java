package dk.sdu.se_f22;

import dk.sdu.se_f22.dao.DatabaseConnectionManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class Main {
    /**
     * Helper method to print query results
     */
    public static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next())
        {

            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws SQLException {
        // Test database querying
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM logs");

        try (ResultSet resultSet = pstmt.executeQuery())
        {
            printResultSet(resultSet);
        }
        catch (Exception e)
        {
            connection.rollback();
            e.printStackTrace();
        }

        // Test database logging
        Logger logger = DatabaseConnectionManager.getInstance().getLogger(Main.class);
        logger.info("Hello world");
    }}
