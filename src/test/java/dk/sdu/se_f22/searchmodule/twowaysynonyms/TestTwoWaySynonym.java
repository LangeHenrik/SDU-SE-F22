package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;

public class TestTwoWaySynonym {
    static TwoWaySynonym operator = TwoWaySynonym.getInstance();
    ArrayList<String> synonymList;

    @BeforeClass
    public void setupClass(){
        synonymList = new ArrayList<>();
    }

    @Test
    @DisplayName("Add new synonym to DB")
    public void testCreateSynonym() {
        //Creates synonym
        operator.create("pants");

        //Creates arraylists
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> expectedOutput = new ArrayList<>();
        ArrayList<String> methodOutput;

        //Adds synonyms to arraylists
        tokens.add("pants");
        expectedOutput.add("pants");
        methodOutput = operator.filter(tokens);

        //Runs test
        assertEquals(expectedOutput, methodOutput);
        assertNull(operator.create("Blah"));
        assertNotNull(operator.create("Power Supply"));

        synonymList.add("Power Supply");
    }

    @Test
    @DisplayName("Update group id from an existing synonym")
    public void testUpdateGroupID(){
    }

    @Test
    public void testFilter(){
        //Creates two new synonyms and synonym groups
        operator.create("anger");
        operator.create("sad");

        //adds all the other synosyms based on the two synonyms above
        operator.create("acrimony","anger");
        operator.create("annoyance","anger");
        operator.create("bitter","sad");
        operator.create("heartbroken","sad");

        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> expectedOutput = new ArrayList<>();
        ArrayList<String> methodOutput;

        tokens.add("anger");
        tokens.add("sad");

        expectedOutput.add("anger");
        expectedOutput.add("acrimony");
        expectedOutput.add("annoyance");
        expectedOutput.add("sad");
        expectedOutput.add("bitter");
        expectedOutput.add("heartbroken");
        methodOutput = operator.filter(tokens);
        Collections.addAll(synonymList, "anger", "sad", "acrimony", "annoyance", "bitter", "heartbroken");

        assertEquals(expectedOutput,methodOutput);
    }

    @Test
    public void testSynonymGroup(){
        UUID methodOutput = operator.create("pants");

        synonymList.add("pants");

        assertNotNull(methodOutput);
    }

    @AfterClass
    public void deleteAllData(){
        //delete all data added from testFilter
        operator.delete("anger");
        operator.delete("acrimony");
        operator.delete("annoyance");
        operator.delete("sad");
        operator.delete("bitter");
        operator.delete("heartbroken");
        operator.delete("pants");
        assertNull(operator.create("Blah"));
        //Deletes all the added data from the tests
        for(String synonym : synonymList){
            operator.delete(synonym);
        }
    }

}
