package dk.sdu.se_f22.searchmodule.misspellings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Misspellings implements DatabaseOperator{
    private String wrongSpelling;
    private String correctSpelling;
    private final String url = "jdbc:postgresql://abul.db.elephantsql.com/hzajyqbo";
    private final String user = "hzajyqbo";
    private final String password = "K8664qtGojuBvQczzv66EhaqkUNbXLj0";
    private static final String QUERY = "SELECT wrong, correct FROM misspellings WHERE wrong =?";
    private static final String SELECT = "SELECT * FROM misspellings";
    private static final String INSERT_misspellings = "INSERT INTO misspellings (wrong, correct) VALUES (?,?);";

    /*
    Help found at:
    https://www.javaguides.net/2020/02/java-jdbc-postgresql-select-example.html
     */
    @Override
    public ArrayList<String> filter(ArrayList<String> tokens) throws SQLException {
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
    public void addMispellings() throws SQLException {
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

    public void deleteMispellings(String word){

    }

    public void updateMispellings(String word){

    }

    public static void main(String[] args) throws SQLException {
        Misspellings mis = new Misspellings();
        //mis.addMispellings();

        /*ArrayList<String> strings = new ArrayList<String>();
        strings.add("HEJ");
        strings.add("HAJ");
        strings.add("HEJ");
        System.out.println(strings);
        mis.filter(strings);
        System.out.println(strings);*/
    }

}
