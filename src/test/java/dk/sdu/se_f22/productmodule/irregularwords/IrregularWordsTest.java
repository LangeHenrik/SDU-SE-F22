package dk.sdu.se_f22.productmodule.irregularwords;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IrregularWordsTest {
    //Connecting the test class to the database before each test.
    @BeforeAll
    static void start() {
    IrregularWords.irregularWords.initialize();
    IrregularWords.irregularWords.insertValues();
    }
    @Test
    void createIRWord() {
        IrregularWords wordCreator = new IrregularWords();
        ArrayList<String> list = new ArrayList<>();
        wordCreator.initialize();

        try {
            //Create words in the table
            wordCreator.createIRWord(10, "Hans");

            //Statement to confirm that word was added to the table
            PreparedStatement stmt = connection.prepareStatement("SELECT word FROM irwords WHERE word = ?");
            stmt.setString(1, "Hans");
            ResultSet result = stmt.executeQuery();

            //Get metadata from ResultSet to get ColumnCount, so While loop can be made that adds ResultSet data to ArrayList
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(result.next()){
                int i = 1;
                while(i <= columnCount) {
                    list.add(result.getString(i++));
                }
            }
            //Assert statement to confirm Hans was added
            assertTrue(list.contains("Hans"));

            //Move to delete test
            wordCreator.deleteIRWord("Hans");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteIRWord() {
        IrregularWords wordDeleter = new IrregularWords();
        wordDeleter.initialize();
        ArrayList<String> list = new ArrayList<>();

        wordDeleter.deleteIRWord("Hans");

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT word FROM irwords WHERE word = ?");
            stmt.setString(1,"Hans");
            ResultSet result = stmt.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while(result.next()){
                int i = 0;
                while(i <= columnCount){
                    list.add(result.getString(i++));
                }
            }
            assertFalse(list.contains("Hans"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateIRWord() {
    }

    @Test
    void readIRWord() {
        IrregularWords.irregularWords.initialize();
        assertTrue(IrregularWords.irregularWords.readIRWord());
    }

    @Test
    void getIRWord() {
    }

    @Test
    void getID() {

    }

    @Test
    void searchForIrregularWords() {
        List<String> tester = new ArrayList<>();
        tester.add("Gustav1");
        tester.add("Hassan1");
        tester.add("tester");
        tester.add("Gustav2");
        tester.add("Hassan2");
        List<String> answer = new ArrayList<>();
        answer.add("Gustav1");
        answer.add("Gustav2");
        answer.add("Gustav3");
        answer.add("Hassan1");
        answer.add("Hassan2");
        answer.add("Hassan3");
        answer.add("tester");
        assertLinesMatch(answer,IrregularWords.irregularWords.searchForIrregularWords(tester));

    }
    @AfterAll
    static void end(){
        IrregularWords.irregularWords.removeValues();

    }
    
}