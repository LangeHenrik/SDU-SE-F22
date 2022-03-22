package dk.sdu.se_f22.searchmodule.misspellings;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MisspellingsTest {
    Misspellings misspelling = new Misspellings();

    @BeforeEach
    void setUp() {
    }

   @Test
    void filter() {
        ArrayList<String> listWrong = new ArrayList<String>();
        listWrong.add("HEJ");
        listWrong.add("HAJ");
        listWrong.add("HIJ");
        listWrong.add("HEJ");

        ArrayList<String> listCorrect = new ArrayList<String>();
        listCorrect.add("HEJ");
        listCorrect.add("HEJ");
        listCorrect.add("HEJ");
        listCorrect.add("HEJ");


       assertEquals(listCorrect, misspelling.filter(listWrong));
   }

    @DisplayName("Tests adding misspelling, " +
            "tests for duplicate, tests for deletion, tests for deletion of non existant misspelling.")
    @Test
    void addingMisspellingsAndRemovingMisspelling() {
        //Adds missepelling
        assertTrue(misspelling.addMisspelling("HRRJRJRJJ","HEJ"));
        //Tries adding same misspelling
        assertFalse(misspelling.addMisspelling("HRRJRJRJJ","HEJ"));
        //Deletes misspelling again
        assertTrue(misspelling.deleteMisspelling("HRRJRJRJJ"));
        //Attempts to delete the same misspelling again
        assertFalse(misspelling.deleteMisspelling("HRRJRJRJJ"));
    }

    @DisplayName("Adds misspelling, updates it, then deletes it.")
    @Test
    void updateMisspellings() {
        misspelling.addMisspelling("JAGA","JAVA");
        assertTrue(misspelling.updateMisspelling("JAGA","JARA"));
        assertFalse(misspelling.updateMisspelling("JAGA","JARA"));
        misspelling.deleteMisspelling("JARA");
    }



    @AfterEach
    void tearDown() {
    }
}