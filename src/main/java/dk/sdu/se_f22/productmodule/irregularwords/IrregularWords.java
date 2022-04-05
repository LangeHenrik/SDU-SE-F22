package dk.sdu.se_f22.productmodule.irregularwords;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class IrregularWords implements IIrregularWords{
    public static IrregularWords irregularWords = new IrregularWords();

    private Connection connection = null;
    private String dbName = "Irregularwords";

    private IrregularWords (){}

    //Initialize method to create a connection to the local database (password might not be the same for all user).
    public void initialize(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbName,
                    "postgres",

                    "pgadmin");

            

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public boolean createIRWord(String tableWord, String insertionWord){
        try {
            //Get the ID from the first word
            int id = irregularWords.getID(tableWord);

            //Statement for inserting the insertionWord with the ID of the tableWord
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO irwords (id, word) VALUES (?,?)");
            stmt.setInt(1,id);
            stmt.setString(2,insertionWord);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method for deleting a specific word from the database table.
    @Override
    public boolean deleteIRWord(String theWord) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM irwords WHERE word = ?");
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
    public Boolean readIRWord() {

        try {
            PreparedStatement db = connection.prepareStatement("SELECT * FROM irwords");
            ResultSet list = db.executeQuery();
            System.out.println(" Id: Word");
            while(list.next()){
                System.out.println(" " + list.getInt("id") + " :  " + list.getString("word"));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
    public int getID(String word){
        int id = 0;
        try {
            PreparedStatement findID = connection.prepareStatement(("SELECT id FROM irwords WHERE word = ?"));
            findID.setString(1, word);
            ResultSet idHolder = findID.executeQuery();
            while(idHolder.next()){
                id = idHolder.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(id);
        return id;
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
      //returns the list
        return irwords;
    }

    // insertValues is a method used once per pc to get some values into the table to perform test from so all have same values,
    public void insertValues(){
        try (Scanner scan = new Scanner(new File
                ("src/main/resources/dk/sdu/se_f22/productmodule/irregularwords/bin/testInsert.txt"))) {
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                String[] data = line.split(",");
                createIRWord(Integer.parseInt(data[0]),data[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeValues(){
        try (Scanner scan = new Scanner(new File
                ("src/main/resources/dk/sdu/se_f22/productmodule/irregularwords/bin/testInsert.txt"))) {
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                String[] data = line.split(",");
                deleteIRWord((data[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getHelp(){
        return "Type the method you want to use:\n"
                +"- inservalues\n"
                +"- createirword or create\n"
                +"- deleteirword or delete\n"
                +"- updateirword or update\n"
                +"- readirword or read\n"
                +"- getirword or get\n"
                +"- getid\n"
                +"- searchforirregularwords or search\n"
                +"- exit\n"
                +"- help";
    }

    public static void main(String[] args) {
        System.out.println(
                "------------------------------------------\n"
                        + "WELCOME TO IrregularWords\n"
                        + "Please input your command or type \"help\"\n"
                        + "------------------------------------------\n"
        );

        boolean running = true;
        try (Scanner s = new Scanner(System.in)) {
            while (running) {
                irregularWords.initialize();
                switch (s.nextLine().toLowerCase()) {
                    case "createirword":
                    case "create":
                        System.out.println("First type the id, then type the word you want to add");
                        System.out.println("or you can type a word from the table, followed by the word you want to add");
                        if(s.hasNextInt()) {
                            irregularWords.createIRWord(s.nextInt(), s.next());
                        }else{
                            irregularWords.createIRWord(s.next(), s.next());
                        }
                        break;
                    case "deleteirword":
                    case "delete":
                        System.out.println("Which word do you want to delete");
                        irregularWords.deleteIRWord(s.next());
                        break;
                    case "updateirword":
                    case "update":
                        System.out.println("First type the word you want to edit, then the corrected word");
                        irregularWords.updateIRWord(s.next(),s.next());
                        break;
                    case "readirword":
                    case "read":
                        irregularWords.readIRWord();
                        break;
                    case "getirword":
                    case "get":
                        System.out.println("write the word");
                        System.out.println(irregularWords.getIRWord(s.next()));
                        break;
                    case "getid":
                        System.out.println("Write the word that you want to find the ID from");
                        irregularWords.getID(s.next());
                        break;
                    case "searchforirregularwords":
                    case "search":
                        System.out.println("Write the words you want to check seperated by, as follows \"word1,word2\"");
                        String string = s.nextLine();
                        String[] words = string.split(",");
                        System.out.println(irregularWords.searchForIrregularWords(new ArrayList<String>(List.of(words))));
                        break;
                    case "insertvalues":
                        System.out.println("You should only use this method one time");
                        irregularWords.insertValues();
                        break;
                    case "exit":
                        running=false;
                        break;
                    default:
                        System.out.println(irregularWords.getHelp());
                        break;
                }
            }
        }
        irregularWords.initialize();
    irregularWords.removeValues();

    }
}
