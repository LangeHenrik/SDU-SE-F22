package dk.sdu.se_f22.searchmodule.misspellings;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class Misspellings implements DatabaseOperator {
    private static final String QUERY = "SELECT wrong, correct FROM misspellings WHERE wrong =?";
    private static final String INSERT_MISSPELLINGS = "INSERT INTO misspellings (wrong, correct) VALUES (?,?);";
    private static final String UPDATE_MISSPELLINGS = "UPDATE misspellings misspellings SET wrong=? WHERE wrong=?";
    private static final String DELETE_MISSPELLINGS = "DELETE FROM misspellings WHERE wrong = ?";


    @Override
    public ArrayList<String> filter(ArrayList<String> tokens){
        ArrayList<String> corrected = tokens;
        for (int i = 0; i < tokens.size() ; i++) {
            try (Connection connection = DBConnection.getPooledConnection()) {

                PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
                preparedStatement.setString(1, tokens.get(i));
                System.out.println(preparedStatement);

                ResultSet rs = preparedStatement.executeQuery();

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


    @Override
    public boolean addMisspelling(String wrong, String correct) {

        if(wrong.contains(" ") || correct.contains(" ")){
            System.out.println("The misspelling and correction must be one word.");
            return false;
        }

        if(wrong.isEmpty()||correct.isEmpty()) {
            System.out.println("The misspelling cannot be blank");
            return false;
        }

        try (Connection connection = DBConnection.getPooledConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MISSPELLINGS);
            preparedStatement.setString(1, wrong);
            preparedStatement.setString(2, correct);

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Could not add misspelling maybe it already exists");
            return false;
        }
    }

    @Override
    public boolean deleteMisspelling(String misspelling) {

        if(misspelling.isEmpty()){
            System.out.println("The misspelling to delete must not be blank.");
            return false;
        }

        try (Connection con = DBConnection.getPooledConnection()) {
            PreparedStatement statement = con.prepareStatement(QUERY);
            statement.setString(1,misspelling);

            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                try (Connection connection = DBConnection.getPooledConnection()) {

                     PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MISSPELLINGS);
                    preparedStatement.setString(1, misspelling);

                    System.out.println(preparedStatement);

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

        if(newMisspelling.contains(" ")){
            System.out.println("New misspelling must not contain spaces.");
            return false;
        }

        if(oldMisspelling.isEmpty() || newMisspelling.isEmpty()){
            System.out.println("The misspelling to delete must not be blank.");
            return false;
        }


        try (Connection con = DBConnection.getPooledConnection()) {
            PreparedStatement statement = con.prepareStatement(QUERY);
            statement.setString(1,oldMisspelling);

            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                System.out.println("Misspelling exists");

                try (Connection connection = DBConnection.getPooledConnection()) {

                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MISSPELLINGS);
                    preparedStatement.setString(1, newMisspelling);
                    preparedStatement.setString(2, oldMisspelling);

                    System.out.println(preparedStatement);

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


    public static void main(String[] args)  {
        Misspellings mis = new Misspellings();
        mis.addMisspelling("tjest","test");
        mis.deleteMisspelling("tjest");


        //mis.addMisspelling("JARA","JAVA");
        //mis.addMisspelling();

        //mis.addMisspelling();
        //mis.deleteMisspelling();
        //mis.updateMisspelling();
      
        /*ArrayList<String> strings = new ArrayList<String>();
        strings.add("HEJ");
        strings.add("HAJ");
        strings.add("HEJ");
        strings.add("HIJ");
        strings.add("KST");
        strings.add("FÆSK");
        strings.add("FESK");
        strings.add("FISK");
        System.out.println(strings);
        mis.filter(strings);
        System.out.println(strings);*/
    }

}
