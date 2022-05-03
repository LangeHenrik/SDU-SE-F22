import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class StopWords implements IStopWords {
    public static final String COLUMN_NAME ="name";
    public static final String TABLE_Eng_WORDS ="words_to_removed";
    public static final String WORDS_FROM_TOKEN_REMOVE="SELECT "+ COLUMN_NAME +" FROM "+ TABLE_Eng_WORDS +" WHERE "+
            COLUMN_NAME +" = ?";
    public static final String INSERT_DA_WORDS= "INSERT INTO "+ TABLE_Eng_WORDS +'('+COLUMN_NAME+")" +
            " VALUES(?) ON CONFLICT DO NOTHING";
    public static final String QUERY_DA_WORDS="SELECT * FROM " + TABLE_Eng_WORDS;
    public static final String REMOVE_DA_WORDS="DELETE FROM " + TABLE_Eng_WORDS +" WHERE "+ COLUMN_NAME + " = ?";
    private PreparedStatement queryWordsPrep;
    private PreparedStatement deleteWordsPrep;
    private PreparedStatement insertWordsPrep;
    private PreparedStatement fromEngTokenPrep;
    public static final String DB_NAME = "stop_words_Eng";
    public static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/" + DB_NAME;
    public static final String DB_PASSWORD = "";
    public static final String DB_USER = "postgres";
    protected Statement statement ;
    protected Connection connection ;
    private static Scanner input = new Scanner(System.in);
    private static List<String> tokenTester = new ArrayList<>();

   public StopWords() {}

//Note: the method below is defined for testing purposes, and not a part of the CMS-4
    public static List<String> unfiltered() {
        if (tokenTester.isEmpty()) {
            System.out.println("Type a token ");
            String token = input.nextLine();
            String[] split = token.split(" ", 0);

            for (String s : split) {
                 s = s.replaceAll("\\s+","-").replaceAll("'", "");
                tokenTester.add(s);
            }
        }
        return tokenTester;
    }

// open connect to the DB
    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, DB_USER, DB_PASSWORD);
            fromEngTokenPrep =connection.prepareStatement(WORDS_FROM_TOKEN_REMOVE);
            insertWordsPrep = connection.prepareStatement(INSERT_DA_WORDS);
            deleteWordsPrep =connection.prepareStatement(REMOVE_DA_WORDS);
            queryWordsPrep =connection.prepareStatement(QUERY_DA_WORDS);
            return true;

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection : " + e.getMessage());
        }
    }
// filters a string token and removes words that match the list of stopWords in the DB
    @Override
    public Collection<String> filter(List<String> unfilteredTokens) {
       open();
        System.out.println(fromEngTokenPrep);
               Collection<String> useableStopwords = new ArrayList<>();
        try {
            for (String i : unfilteredTokens) {
                fromEngTokenPrep.setString(1,i);
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
       open();
        try {
            insertWordsPrep.setString(1,stopWord);
            int rows = insertWordsPrep.executeUpdate();
            if (rows > 0) {
                System.out.println("Add command: A new stop-word has been added: " + stopWord + "");
            } else {
                System.out.println("Add command: The stop-word: " + stopWord + " already exist");
            }
            insertWordsPrep.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error connecting to database" + e.getMessage());
            e.printStackTrace();
        }
    }

// Removes stopWords from the List in DB
    @Override
    public void removeStopWord(String stopWord) {
       open();
        try {
            deleteWordsPrep.setString(1,stopWord);
            int rows = deleteWordsPrep.executeUpdate();

            if (rows > 0) {
                System.out.println("Remove command: The stop-word: " + stopWord + " Has been removed");
            } else {
                System.out.println("Remove command: The stop-word: " + stopWord + " Does not exist");
            }
            deleteWordsPrep.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error connecting to database" + e.getMessage());
            e.printStackTrace();
        }
    }
 //Queries all defined stopWords in the DB
    @Override
    public void queryStopWord() {
       open();
        try {
            ResultSet results = queryWordsPrep.executeQuery();
            while (results.next()) {
                System.out.println(results.getInt("nr") + ":" +
                        results.getString("name"));
            }
            results.close();
            queryWordsPrep.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error connecting to database" + e.getMessage());
            e.printStackTrace();
        }
    }
}

