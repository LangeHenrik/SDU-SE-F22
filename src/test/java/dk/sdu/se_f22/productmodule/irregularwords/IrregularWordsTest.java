package dk.sdu.se_f22.productmodule.irregularwords;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.ArrayList;
import org.junit.jupiter.api.*;

import java.util.Arrays;
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
    void getIRWordCorrectOutput() {
        ArrayList<String> result = IrregularWords.irregularWords.getIRWord("Gustav1");
        String[] words = new String[3];
        for (int i = 0; i < 3; i++){
            words[i] = result.get(i);
        }
        assertArrayEquals( new String[] {"Gustav1","Gustav2","Gustav3"}, words, "One or more words do not match");
    }

    @Test
    void getIRWordWrongOutput(){
        ArrayList<String> result = IrregularWords.irregularWords.getIRWord("Gustav2");
        String[] words = new String[3];
        for (int i = 0; i < 3; i++){
            words[i] = result.get(i);
        }
        assertFalse(Arrays.equals(new String[] {"Gustav1", "Gustav3", "Gustav2"}, words), "One or more words do not match");
    }



    @Test
    void getIDFromAllWordsWithSameID() {
        int[] words = new int[3];
        for (int i = 1; i <= 3 ; i++) {
            words[i - 1] = IrregularWords.irregularWords.getID("Gustav" + i);
        }
        assertArrayEquals(new int[] {1,1,1}, words, "One or more words do not have the same ID");
    }

    @Test
    void getIDFromWordsWithSameIDIncorrect(){
        int[] words = new int[4];
        for (int i = 1; i <= 3 ; i++) {
            words[i - 1] = IrregularWords.irregularWords.getID("Gustav" + i);
        }
        words[3] = IrregularWords.irregularWords.getID("Mathias1");
        assertFalse(Arrays.equals(new int[] {1,1,1,1}, words), "One or more words do not have the same ID");
    }

    @Test
    void getIDSingleWordCorrect(){
        int word = IrregularWords.irregularWords.getID("Mathias1");
        assertEquals(2,word, "Wrong ID was retrieved");
    }

    @Test
    void getIDSingleWordIncorrect(){
        int word = IrregularWords.irregularWords.getID("Mathias1");
        assertNotEquals(3, word);
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