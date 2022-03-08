package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class TestTwoWaySynonym {
    static TwoWaySynonym operator = TwoWaySynonym.getInstance();

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

        assertEquals(expectedOutput,methodOutput);
    }

    @Test
    public void testSynonymGroup(){
        UUID methodOutput = operator.create("pants");

        assertNotNull(methodOutput);
    }

    @Test
    public void testSynonymGroup(){
        UUID methodOutput = TwoWaySynonym.getInstance().create("pants");

        Assertions.assertNotNull(methodOutput);
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
    }

}
