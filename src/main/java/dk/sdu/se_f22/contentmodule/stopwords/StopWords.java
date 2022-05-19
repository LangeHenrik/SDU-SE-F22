package dk.sdu.se_f22.contentmodule.stopwords;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.*;




public class StopWords implements IStopWordsDB {
    public static final String COLUMN_NAME_Eng = "name";
    public static final String TABLE_Eng_WORDS = "words_to_remove";
    public static final String REMOVE_Eng_WORDS = "DELETE FROM " + TABLE_Eng_WORDS + " WHERE " + COLUMN_NAME_Eng + " = ?";
    public static final String WORDS_FROM_EngTOKEN_REMOVE = "SELECT " + COLUMN_NAME_Eng + " FROM " + TABLE_Eng_WORDS + " WHERE " +
            COLUMN_NAME_Eng + " = ?";
    public static final String UPDATE_Eng_WORDS = "UPDATE " + TABLE_Eng_WORDS + "\n SET " + COLUMN_NAME_Eng + " =?" + " WHERE " + COLUMN_NAME_Eng + " = ?";

    public static final String INSERT_Eng_WORDS = "INSERT INTO " + TABLE_Eng_WORDS + '(' + COLUMN_NAME_Eng + ")" +
            " VALUES(?) ON CONFLICT DO NOTHING";
    public static final String QUERY_Eng_WORDS = "SELECT * FROM " + TABLE_Eng_WORDS;

    private PreparedStatement queryEngWordsPrep;
    private PreparedStatement deleteEngWordsPrep;
    private PreparedStatement insertEngWordsPrep;
    private PreparedStatement fromEngTokenPrep;
    private PreparedStatement updateEngWordPrep;
    public Connection connection;
    public Statement statement;
    private static Collection<String> useableStopwords = new ArrayList<>();
    private static final StopWords instance = new StopWords();


    private StopWords() {
    }

    public static StopWords getInstance() {
        return instance;
    }

    public void openConnection() {
        try {
            connection = DBConnection.getPooledConnection();
            fromEngTokenPrep = connection.prepareStatement(WORDS_FROM_EngTOKEN_REMOVE);
            insertEngWordsPrep = connection.prepareStatement(INSERT_Eng_WORDS);
            deleteEngWordsPrep = connection.prepareStatement(REMOVE_Eng_WORDS);
            queryEngWordsPrep = connection.prepareStatement(QUERY_Eng_WORDS);
            updateEngWordPrep = connection.prepareStatement(UPDATE_Eng_WORDS);

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database : " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public List<String> filter(List<String> unfilteredTokens) {
        openConnection();
        Collection<String> useableStopwords = new ArrayList<>();
        try {
            for (String i : unfilteredTokens) {
                fromEngTokenPrep.setString(1, i);
                ResultSet resultSet = fromEngTokenPrep.executeQuery();
                while (resultSet.next()) {
                    useableStopwords.add(resultSet.getString("name"));
                }
                resultSet.close();
            }
            unfilteredTokens.removeAll(useableStopwords);
            fromEngTokenPrep.close();
            connection.close();


        } catch (SQLException e) {
            System.out.println("Error connecting to database" + e.getMessage());
            e.printStackTrace();
        }
        return unfilteredTokens;
    }

    // Adding new stopWords to the list in the DB
    @Override
    public void addStopWord(String stopWord) {
        openConnection();
        try {
            insertEngWordsPrep.setString(1, stopWord);
            int rows = insertEngWordsPrep.executeUpdate();
            if (rows > 0) {
                System.out.println("Add command: A new stop-word has been added: " + stopWord + "");
            } else {
                System.out.println("Add command: The stop-word: " + stopWord + " already exist");
            }
            insertEngWordsPrep.close();
            connection.close();


        } catch (SQLException e) {
            System.out.println("Error connecting to database" + e.getMessage());
            e.printStackTrace();
        }
    }

    // Removes stopWords from the List in DB
    @Override
    public void removeStopWord(String stopWord) {
        openConnection();
        try {
            deleteEngWordsPrep.setString(1, stopWord);
            int rows = deleteEngWordsPrep.executeUpdate();
            if (rows > 0) {
                System.out.println("Remove command: The stop-word: " + stopWord + " Has been removed");
            } else {
                System.out.println("Remove command: The stop-word: " + stopWord + " Does not exist");
            }
            deleteEngWordsPrep.close();
            connection.close();


        } catch (SQLException e) {
            System.out.println("Error connecting to database" + e.getMessage());
            e.printStackTrace();
        }
    }

    //    Queries all defined stopWords in the DB
    @Override
    public List<String> queryStopWord() {
        openConnection();
        try {
            ResultSet results = queryEngWordsPrep.executeQuery();
            List<String> stopWord1 = new ArrayList<>();
            while (results.next()) {
                stopWord1.add(results.getString("name"));
            }
            results.close();
            queryEngWordsPrep.close();
            connection.close();
            return stopWord1;
        } catch (SQLException e) {
            System.out.println("Error connecting to database" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateStopWord(String oldStopWord, String newStopWord) {
        openConnection();
        try {
            updateEngWordPrep.setString(1, newStopWord);
            updateEngWordPrep.setString(2, oldStopWord);
            int effectedRows = updateEngWordPrep.executeUpdate();
            updateEngWordPrep.close();
            connection.close();
            return effectedRows == 1;
        } catch (SQLException e) {
            System.out.println("Update failed " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

