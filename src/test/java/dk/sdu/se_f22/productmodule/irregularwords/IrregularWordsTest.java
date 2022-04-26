package dk.sdu.se_f22.productmodule.irregularwords;

import dk.sdu.se_f22.productmodule.irregularwords.Data.IrregularWords;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IrregularWordsTest {

    //Connecting the test class to the database before each test.
    @BeforeAll
    static void start() {
    IrregularWords.INSTANCE.initialize();
    IrregularWords.INSTANCE.insertValues();
    }

    @Test
    void createIRWord(){
        //Create ArrayList to hold the words that have to be check in assertEquals and to use the searchForIrregularWords method.
        ArrayList<String> list = new ArrayList<>();
        list.add("Hans");
        list.add("Hans2");

        //Create the two words in the database
        IrregularWords.INSTANCE.createIRWord(10055,"Hans");
        IrregularWords.INSTANCE.createIRWord("Hans", "Hans2");

        //Check that both words have been correctly added to the database
        assertEquals(list,IrregularWords.INSTANCE.searchForIrregularWords(list));

        //Delete the words from the database
        IrregularWords.INSTANCE.deleteIRWord("Hans");
        IrregularWords.INSTANCE.deleteIRWord("Hans2");
    }

    @Test
    void deleteIRWord(){
        // Word in the database created from insertValues() = Mathias2
        ArrayList<String> list = new ArrayList<>();
        list.add("Mathias2");

        // Deleting the word from the database
        IrregularWords.INSTANCE.deleteIRWord("Mathias2");

        assertEquals(0, IrregularWords.INSTANCE.getIndex("Mathias2"));

        //Reinserting the word for future use.
        IrregularWords.INSTANCE.createIRWord("Mathias1", "Mathias2");
    }

    @Test
    void updateIRWord() {

        //it creates a word and id in the table
        IrregularWords.INSTANCE.createIRWord(2000,"falseUpdateWord");
        //it updates the word to a different word in the table
        IrregularWords.INSTANCE.updateIRWord("falseUpdateWord","correctUpdateWord");

        //Finding the word in the table.
        ArrayList<String> result = IrregularWords.INSTANCE.getIRWord("correctUpdateWord");
        //the test function compare the words
        assertEquals("correctUpdateWord", result.get(0));
        //remove the change in table.
        IrregularWords.INSTANCE.deleteIRWord("correctUpdateWord");

    }

    @Test
    void readIRWord() {
        IrregularWords.INSTANCE.initialize();
        assertTrue(IrregularWords.INSTANCE.readIRWord());
    }

    @Test
    void getIRWordCorrectOutput() {
        ArrayList<String> result = IrregularWords.INSTANCE.getIRWord("Gustav1");
        String[] words = new String[3];
        for (int i = 0; i < 3; i++){
            words[i] = result.get(i);
        }
        assertArrayEquals( new String[] {"Gustav1","Gustav2","Gustav3"}, words, "One or more words do not match");
    }

    @Test
    void getIRWordWrongOutput(){
        ArrayList<String> result = IrregularWords.INSTANCE.getIRWord("Gustav2");
        String[] words = new String[3];
        for (int i = 0; i < 3; i++){
            words[i] = result.get(i);
        }
        assertFalse(Arrays.equals(new String[] {"Gustav1", "Gustav3", "Gustav2"}, words), "One or more words do not match");
    }

    @Test
    void getIDFromAllWordsWithSameIndex() {
        int[] words = new int[3];
        for (int i = 1; i <= 3 ; i++) {
            words[i - 1] = IrregularWords.INSTANCE.getIndex("Gustav" + i);
        }
        assertArrayEquals(new int[] {1990,1990,1990}, words, "One or more words do not have the same ID");
    }

    @Test
    void getIDFromWordsWithSameIndexIncorrect(){
        int[] words = new int[4];
        for (int i = 1; i <= 3 ; i++) {
            words[i - 1] = IrregularWords.INSTANCE.getIndex("Gustav" + i);
        }
        words[3] = IrregularWords.INSTANCE.getIndex("Mathias1");
        assertFalse(Arrays.equals(new int[] {1,1,1,1}, words), "One or more words do not have the same ID");
    }

    @Test
    void getIDSingleWordCorrect(){
        int word = IrregularWords.INSTANCE.getIndex("Mathias1");
        assertEquals(1991,word, "Wrong ID was retrieved");
    }

    @Test
    void getIDSingleWordIncorrect(){
        int word = IrregularWords.INSTANCE.getIndex("Mathias1");
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
        assertLinesMatch(answer,IrregularWords.INSTANCE.searchForIrregularWords(tester));

    }

    @AfterAll
    static void end(){
        IrregularWords.INSTANCE.removeValues();
    }
}