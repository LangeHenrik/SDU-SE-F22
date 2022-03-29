package dk.sdu.se_f22.productmodule.irregularwords;

import org.postgresql.util.PSQLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class IrregularWords implements IIrregularWords{

   static public IrregularWords irregularWords = new IrregularWords();

    private Connection connection = null;
    private boolean canConnect = false;
    private String dbName = "Irregularwords";

    //Initialize method to create a connection to the local database (password might not be the same for all user).
    public void initialize(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbName,
                    "postgres",
                    "aaaa1234");
            canConnect = true;
        } catch (SQLException e) {
            e.printStackTrace();
            canConnect = false;
        }
        /*if(!canConnect){
            createDB();
            createTable();
            canConnect = true;

        }*/
    }

    //Temporary method used for creating a database with the name "irregularwords".
    private void createDB(){
        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE DATABASE irregularwords");
            stmt.execute();
            dbName = "irregularwords";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Temporary method used for creating a table "irwords" with 2 attributes "id & word".
    public void createTable(){
        try {
            PreparedStatement tableStmt = connection.prepareStatement(
                    "CREATE TABLE irwords(id INTEGER NOT NULL, word VARCHAR PRIMARY KEY);");
            tableStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method used for inserting new words with an id into the database.
    @Override
    public boolean createIRWord(int ID, String Word) {
        try {
            //Preparing a statement with the needed SQL language, thereafter putting the method signature
            //into the statement and executing.
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO irwords (id, word) VALUES (?,?)");
            insertStatement.setInt(1,ID);
            insertStatement.setString(2,Word);
            return insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method for deleting a specific word from the database table.
    @Override
    public boolean deleteIRWord(String theWord) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DROP * FROM irwords WHERE word = ?");
            stmt.setString(1,theWord);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method for updating words in the database table.
    @Override
        public boolean updateIRWord(String originalWord, String updatedWord) {
            try{
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE irwords SET word = ? WHERE word = ?");
                stmt.setString(1, updatedWord);
                stmt.setString(2, originalWord);
                return stmt.execute();
            }catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

    //Method for printing out the database table.
    @Override
    public void readIRWord() {
        try {
            PreparedStatement db = connection.prepareStatement("SELECT * FROM irwords");
            ResultSet list = db.executeQuery();
            System.out.println(" Id: Word");
            while(list.next()){
                System.out.println(" " + list.getInt("id") + " :  " + list.getString("word"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //the getIRWord takes a word looks it up in the table and return the matching irregularwords in an arraylist
    @Override
    public ArrayList<String> getIRWord(String word) {
        ArrayList<String> words = new ArrayList<>();
        try {
            // first we make the arraylist it returns at the end
            PreparedStatement findIdStmt = connection.prepareStatement("SELECT id FROM irwords WHERE word = ?");
            findIdStmt.setString(1,word);
            ResultSet findIdStmtResult = findIdStmt.executeQuery();
            try {
                PreparedStatement findIdMatchStmt = connection.prepareStatement("SELECT word FROM irwords WHERE id =?");
                while (findIdStmtResult.next()) {
                    findIdMatchStmt.setInt(1, findIdStmtResult.getInt("id"));
                }

                ResultSet findIdMatchStmtResult = findIdMatchStmt.executeQuery();
                while (findIdMatchStmtResult.next()) {
                    words.add(findIdMatchStmtResult.getString("word"));
                }
                return words;
            }catch (SQLException w){
                words.clear();
                words.add(word);
                return words;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean createBackup() {
    return false;
    }

    @Override
    public boolean loadBackup() {
    return false;
    }

    //searchForIrregularWords takes a list and check and adds the matching missing irregular words to the list and returns it
    @Override
    public List<String> searchForIrregularWords(List<String> arrayList) {
        //first we initialise a new arraylist to add the irwords to
        List<String> irwords = new ArrayList<>();
        //we go through each string in the given list to check if they are in the irwords arraylist and if not then add all the irwords to the list
      for (String string : arrayList){
          if(!irwords.contains(string)) {
                  irwords.addAll(getIRWord(string));
          }
      }
      //print is only to check if the method works correctly
        System.out.println(irwords);
      //returns the list
        return irwords;
    }

    // insertValues is a method used once per pc to get some values into the table to perform test from so all have same values,
    private void insertValues(){
        try (Scanner scan = new Scanner(new File
                ("src/main/java/dk/sdu/se_f22/productmodule/irregularwords/bin/testInsert.txt"))) {
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                String[] data = line.split(",");
                createIRWord(Integer.parseInt(data[0]),data[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        irregularWords.initialize();
        List<String> test = new ArrayList<>();
        test.add("Gustav1");
        test.add("hej");
        test.add("Oliver3");
        test.add("Gustav2");
        irregularWords.searchForIrregularWords(test);

    }
}
