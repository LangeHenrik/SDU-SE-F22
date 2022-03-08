package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

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
}
