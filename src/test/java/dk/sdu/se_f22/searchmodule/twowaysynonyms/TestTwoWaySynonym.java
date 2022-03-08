package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class TestTwoWaySynonym {
    static TwoWaySynonym operator = TwoWaySynonym.getInstance();

    @Test
    @DisplayName("Add new synonym to DB")
    public void testCreateSynonym() {
        Assertions.assertNull(operator.create("Blah"));
    }

    @Test
    @DisplayName("Update group id from an existing synonym")
    public void testUpdateGroupID(){
    }
    @Test
    public void testFilter(){
        //Creates two new synonyms and synonym groups
        TwoWaySynonym.getInstance().create("anger");
        TwoWaySynonym.getInstance().create("sad");

        //adds all the other synosyms based on the two synonyms above
        TwoWaySynonym.getInstance().create("acrimony","anger");
        TwoWaySynonym.getInstance().create("annoyance","anger");
        TwoWaySynonym.getInstance().create("bitter","sad");
        TwoWaySynonym.getInstance().create("heartbroken","sad");

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

        methodOutput = TwoWaySynonym.getInstance().filter(tokens);

        Assertions.assertEquals(expectedOutput,methodOutput);
    }

    @Test
    public void testSynonymGroup(){
        UUID methodOutput = TwoWaySynonym.getInstance().create("pants");

        Assertions.assertNotNull(methodOutput);
    }

    @AfterClass
    public void deleteAllData(){
        //delete all data added from testFilter
        TwoWaySynonym.getInstance().delete("anger");
        TwoWaySynonym.getInstance().delete("acrimony");
        TwoWaySynonym.getInstance().delete("annoyance");
        TwoWaySynonym.getInstance().delete("sad");
        TwoWaySynonym.getInstance().delete("bitter");
        TwoWaySynonym.getInstance().delete("heartbroken");
    }
}
