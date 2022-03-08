package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

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
