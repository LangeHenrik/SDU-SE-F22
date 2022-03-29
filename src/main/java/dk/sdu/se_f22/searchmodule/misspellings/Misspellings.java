package dk.sdu.se_f22.searchmodule.misspellings;

import java.sql.*;
import java.util.ArrayList;

public class Misspellings implements DatabaseOperator {
    private static final String url = "jdbc:postgresql://abul.db.elephantsql.com/hzajyqbo";
    private static final String user = "hzajyqbo";
    private static final String password = "K8664qtGojuBvQczzv66EhaqkUNbXLj0";
    private static final String QUERY = "SELECT wrong, correct FROM misspellings WHERE wrong =?";
    private static final String INSERT_MISSPELLINGS = "INSERT INTO misspellings (wrong, correct) VALUES (?,?);";
    private static final String UPDATE_MISSPELLINGS = "UPDATE misspellings misspellings SET wrong=? WHERE wrong=?";
    private static final String DELETE_MISSPELLINGS = "DELETE FROM misspellings WHERE wrong = ?";


    /*
    Help found at:
    https://www.javaguides.net/2020/02/java-jdbc-postgresql-select-example.html
     */
    @Override
    public ArrayList<String> filter(ArrayList<String> tokens){
        ArrayList<String> corrected = tokens;
        for (int i = 0; i < tokens.size() ; i++) {
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(url, user, password);

                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(QUERY);) {
                preparedStatement.setString(1, tokens.get(i));
                System.out.println(preparedStatement);

                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();

                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    String correctWord = rs.getString("correct");
                    corrected.set(i, correctWord);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return corrected;
    }

    /*
    Help found at:
    https://www.javaguides.net/2020/02/java-jdbc-postgresql-insert-example.html
     */

    @Override
    public boolean addMisspelling(String wrong, String correct) {

        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MISSPELLINGS)) {
            preparedStatement.setString(1, wrong);
            preparedStatement.setString(2, correct);

            System.out.println(preparedStatement);

            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Could not add misspelling maybe it already exists");
            return false;
        }
    }

    @Override
    public boolean deleteMisspelling(String misspelling) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = con.prepareStatement(QUERY);
            statement.setString(1,misspelling);

            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                // Step 1: Establishing a Connection
                try (Connection connection = DriverManager.getConnection(url, user, password);

                     // Step 2:Create a statement using connection object
                     PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MISSPELLINGS)) {
                    preparedStatement.setString(1, misspelling);

                    System.out.println(preparedStatement);

                    // Step 3: Execute the query or update query
                    preparedStatement.execute();
                    return true;
                } catch (SQLException e) {
                    System.out.println("Could not delete misspelling");
                    return false;
                }
            } else {
                System.out.println("Misspelling does not exist");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateMisspelling(String oldMisspelling, String newMisspelling) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = con.prepareStatement(QUERY);
            statement.setString(1,oldMisspelling);

            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                System.out.println("Misspelling exists");

                // Step 1: Establishing a Connection
                try (Connection connection = DriverManager.getConnection(url, user, password);

                     // Step 2:Create a statement using connection object
                     PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MISSPELLINGS)) {
                    preparedStatement.setString(1, newMisspelling);
                    preparedStatement.setString(2, oldMisspelling);

                    System.out.println(preparedStatement);

                    // Step 3: Execute the query or update query
                    preparedStatement.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    System.out.println("Could not update misspelling");
                    return false;
                }
            } else {
                System.out.println("Misspelling does not exist");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }





}
