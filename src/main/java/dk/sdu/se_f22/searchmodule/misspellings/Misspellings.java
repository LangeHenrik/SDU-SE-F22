package dk.sdu.se_f22.searchmodule.misspellings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Misspellings implements DatabaseOperator{
    private String wrongSpelling; //skal måske slettes
    private String correctSpelling; //skal måske slettes
    private final String url = "jdbc:postgresql://abul.db.elephantsql.com/hzajyqbo";
    private final String user = "hzajyqbo";
    private final String password = "K8664qtGojuBvQczzv66EhaqkUNbXLj0";
    private static final String QUERY = "SELECT wrong, correct FROM misspellings WHERE wrong =?";
    private static final String INSERT_misspellings = "INSERT INTO misspellings (wrong, correct) VALUES (?,?);";
    private static final String UPDATE_misspellings = "UPDATE misspellings misspellings SET wrong=? WHERE wrong=?";

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
    public void addMisspelling() {
        //get the new misspelling
        Scanner scannerW = new Scanner(System.in);
        System.out.println("Write misspelling");
        String misspelling = scannerW.nextLine();
        //get the correct word
        Scanner scannerC = new Scanner(System.in);
        System.out.println("Write the correct spelling of the word");
        String correct = scannerC.nextLine();
        //add the new misspelling
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_misspellings)) {
            preparedStatement.setString(1, misspelling);
            preparedStatement.setString(2, correct);

            System.out.println(preparedStatement);
            //System.out.println("The misspelling: "+misspelling+" have been added."+" The correct spelling is "+correct);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not add misspelling maybe it already exists");
        }
    }

    @Override
    public void addMisspelling(String wrong, String correct) {
        //add the new misspelling
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_misspellings)) {
            preparedStatement.setString(1, wrong);
            preparedStatement.setString(2, correct);

            System.out.println(preparedStatement);
            //System.out.println("The misspelling: "+misspelling+" have been added."+" The correct spelling is "+correct);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not add misspelling maybe it already exists");
        }
    }

    @Override
    public void deleteMisspelling(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the word you wish to delete:");
        String word = scanner.nextLine();
        String SQL = "DELETE FROM misspellings WHERE wrong = ?";
        int rows = 0;
        scanner.close();

        try(Connection con = DriverManager.getConnection(url, user, password); PreparedStatement pst = con.prepareStatement(SQL)){
            pst.setString(1, word);
            rows = pst.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("The misspelling does not exist in the database.");
            throwables.printStackTrace();
        }
        System.out.println("The misspelling " + word + " was deleted.");
    }

    @Override
    public void deleteMisspelling(String missspelling) {

        String SQL = "DELETE FROM misspellings WHERE wrong = ?";
        int rows = 0;

        try(Connection con = DriverManager.getConnection(url, user, password); PreparedStatement pst = con.prepareStatement(SQL)){
            pst.setString(1, missspelling);
            rows = pst.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("The misspelling does not exist in the database.");
            throwables.printStackTrace();
        }
        System.out.println("The misspelling " + missspelling + " was deleted.");
    }


    @Override
    public void updateMisspelling(){
        Scanner scanner = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            String SQL = "SELECT wrong FROM misspellings WHERE wrong=?";
            PreparedStatement statement = con.prepareStatement(SQL);
            System.out.println("What misspelling do you want to update?");
            String misForUpdate = scanner.next();
            statement.setString(1,misForUpdate);


            ResultSet rs = statement.executeQuery(SQL);
            if (rs.next()){
                System.out.println("Misspelling exists");
                //get the update
                Scanner scannerCorrect = new Scanner(System.in);
                System.out.println("Write what the update for the misspelling");
                String corretMisForUpdate = scannerCorrect.nextLine();

                //update the misspelling
                // Step 1: Establishing a Connection
                try (Connection connection = DriverManager.getConnection(url, user, password);
                     // Step 2:Create a statement using connection object
                     PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_misspellings)) {
                    preparedStatement.setString(1, corretMisForUpdate);
                    preparedStatement.setString(2, misForUpdate);

                    System.out.println(preparedStatement);
                    // Step 3: Execute the query or update query
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Could not update misspelling");
                }
            } else {
                System.out.println("Misspelling does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMisspelling(String correction, String oldMisspelling) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            String SQL = "SELECT wrong FROM misspellings WHERE wrong=?";
            PreparedStatement statement = con.prepareStatement(SQL);
            statement.setString(1,oldMisspelling);

            ResultSet rs = statement.executeQuery(SQL);
            if (rs.next()){
                System.out.println("Misspelling exists");
                //get the update
                Scanner scannerCorrect = new Scanner(System.in);
                System.out.println("Write what the update for the misspelling");
                String corretMisForUpdate = scannerCorrect.nextLine();

                //update the misspelling
                // Step 1: Establishing a Connection
                try (Connection connection = DriverManager.getConnection(url, user, password);
                     // Step 2:Create a statement using connection object
                     PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_misspellings)) {
                    preparedStatement.setString(1, correction);
                    preparedStatement.setString(2, oldMisspelling);

                    System.out.println(preparedStatement);
                    // Step 3: Execute the query or update query
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Could not update misspelling");
                }
            } else {
                System.out.println("Misspelling does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
