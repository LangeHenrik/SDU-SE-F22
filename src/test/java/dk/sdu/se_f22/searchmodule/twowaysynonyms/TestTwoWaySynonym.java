package dk.sdu.se_f22.searchmodule.twowaysynonyms;

<<<<<<< HEAD
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
=======
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
>>>>>>> 58b7352 (Added initial test class and singleton pattern to TwoWaySynonym)

import java.util.ArrayList;

public class TestTwoWaySynonym {
    static TwoWaySynonym operator = TwoWaySynonym.getInstance();

    @Test
    @DisplayName("Add new synonym to DB")
    public void testCreateSynonym() {
<<<<<<< HEAD
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
=======
        Assertions.assertNull(operator.create("Blah"));
>>>>>>> 58b7352 (Added initial test class and singleton pattern to TwoWaySynonym)
    }

    @Test
    @DisplayName("Update group id from an existing synonym")
    public void testUpdateGroupID(){
    }
    @Test
    public void testFilter(){
        ArrayList<String> tokends = new ArrayList<>();
        ArrayList<String> expectedOutput = new ArrayList<>();

        tokends.add("anger");
        tokends.add("sad");

        expectedOutput.add("anger");
        expectedOutput.add("acrimony");
        expectedOutput.add("annoyance");
        expectedOutput.add("sad");
        expectedOutput.add("bitter");
        expectedOutput.add("heartbroken");

        TwoWaySynonym.getInstance().filter(tokends);

        Assertions.assertEquals(expectedOutput,tokends);
    }
}
