package dk.sdu.se_f22.productmodule.irregularwords.Data;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class IrregularWords implements IIrregularWords {
    public static IrregularWords INSTANCE = new IrregularWords();
    private IrregularWords (){}

    //Initialize method to create a connection to the local database (password might not be the same for all user).
    public void initialize(){
        try {
            DBConnection.getPooledConnection();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }


    //Method used for inserting new words with an id into the database.
    @Override
    public boolean createIRWord(int Index, String Word) {
        try {
            //Preparing a statement with the needed SQL language, thereafter putting the method signature
            //into the statement and executing.
            PreparedStatement insertStatement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO irregularwords (index, word) VALUES (?,?)");
            insertStatement.setInt(1,Index);
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
            int index = INSTANCE.getIndex(tableWord);
            //Statement for inserting the insertionWord with the ID of the tableWord
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement("INSERT INTO irregularwords (index, word) VALUES (?,?)");
            stmt.setInt(1,index);
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
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement("DELETE FROM irregularwords WHERE word = ?");
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
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
                    "UPDATE irregularwords SET word = ? WHERE word = ?");
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
            PreparedStatement db = DBConnection.getConnection().prepareStatement("SELECT * FROM irregularwords");
            ResultSet list = db.executeQuery();
            System.out.println("Word : Index");
            while(list.next()){
                System.out.println(" " + list.getString("word") + " :  " + list.getInt("index"));
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
            PreparedStatement findIndexStmt = DBConnection.getConnection().prepareStatement("SELECT index FROM irregularwords WHERE word = ?");
            findIndexStmt.setString(1,word);
            ResultSet findIndexStmtResult = findIndexStmt.executeQuery();
            try {
                PreparedStatement findIndexMatchStmt = DBConnection.getConnection().prepareStatement("SELECT word FROM irregularwords WHERE index =?");
                while (findIndexStmtResult.next()) {
                    findIndexMatchStmt.setInt(1, findIndexStmtResult.getInt("index"));
                }
                ResultSet findIndexMatchStmtResult = findIndexMatchStmt.executeQuery();
                while (findIndexMatchStmtResult.next()) {
                    words.add(findIndexMatchStmtResult.getString("word"));
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
    public int getIndex(String word){
        int index = 0;
        try {
            PreparedStatement findIndex = DBConnection.getConnection().prepareStatement(("SELECT index FROM irregularwords WHERE word = ?"));
            findIndex.setString(1, word);
            ResultSet indexHolder = findIndex.executeQuery();
            while(indexHolder.next()){
                index = indexHolder.getInt("index");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(index);
        return index;
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
                INSTANCE.initialize();
                switch (s.nextLine().toLowerCase()) {
                    case "createirword":
                    case "create":
                        System.out.println("First type the id, then type the word you want to add");
                        System.out.println("or you can type a word from the table, followed by the word you want to add");
                        if(s.hasNextInt()) {
                            INSTANCE.createIRWord(s.nextInt(), s.next());
                        }else{
                            INSTANCE.createIRWord(s.next(), s.next());
                        }
                        break;
                    case "deleteirword":
                    case "delete":
                        System.out.println("Which word do you want to delete");
                        INSTANCE.deleteIRWord(s.next());
                        break;
                    case "updateirword":
                    case "update":
                        System.out.println("First type the word you want to edit, then the corrected word");
                        INSTANCE.updateIRWord(s.next(),s.next());
                        break;
                    case "readirword":
                    case "read":
                        INSTANCE.readIRWord();
                        break;
                    case "getirword":
                    case "get":
                        System.out.println("write the word");
                        System.out.println(INSTANCE.getIRWord(s.next()));
                        break;
                    case "getid":
                        System.out.println("Write the word that you want to find the ID from");
                        INSTANCE.getIndex(s.next());
                        break;
                    case "searchforirregularwords":
                    case "search":
                        System.out.println("Write the words you want to check seperated by, as follows \"word1,word2\"");
                        String string = s.nextLine();
                        String[] words = string.split(",");
                        System.out.println(INSTANCE.searchForIrregularWords(new ArrayList<String>(List.of(words))));
                        break;
                    case "insertvalues":
                        System.out.println("You should only use this method one time");
                        INSTANCE.insertValues();
                        break;
                    case "exit":
                        running=false;
                        break;
                    default:
                        System.out.println(INSTANCE.getHelp());
                        break;
                }
            }
        }
    }
}
