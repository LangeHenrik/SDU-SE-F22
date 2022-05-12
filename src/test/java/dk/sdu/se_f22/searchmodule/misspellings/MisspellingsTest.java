package dk.sdu.se_f22.searchmodule.misspellings;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MisspellingsTest {
    Misspellings misspelling = new Misspellings();

    ArrayList<String> listWrong = new ArrayList<>();
    ArrayList<String> listCorrect = new ArrayList<>();

    @BeforeEach
    void setUp() {
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM misspellings");
            preparedStatement.execute();
            PreparedStatement filterTest1 =
                    connection.prepareStatement("INSERT INTO misspellings (wrong, correct) VALUES ('HAJ','HEJ') ");
            PreparedStatement filterTest2 =
                    connection.prepareStatement("INSERT INTO misspellings (wrong, correct) VALUES ('HIJ','HEJ') ");
            filterTest1.execute();
            filterTest2.execute();
            PreparedStatement testMisspelling =
                    connection.prepareStatement("INSERT INTO misspellings (wrong, correct) VALUES ('TestMisspelling','MisspellingTest') ");
            testMisspelling.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Wrong words for filter method
        listWrong.add("HEJ");
        listWrong.add("HAJ");
        listWrong.add("HIJ");
        listWrong.add("HEJ");
        //Correct words for filter method
        listCorrect.add("HEJ");
        listCorrect.add("HEJ");
        listCorrect.add("HEJ");
        listCorrect.add("HEJ");
    }

    @AfterEach
    void tearDown() {
        listWrong.clear();
        listCorrect.clear();
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM misspellings");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("Tests the filter method by comparing two ArrayLists, to misspellings saved in the database during setup.")
    @Test
    void filterTest() {
       assertEquals(listCorrect, misspelling.filter(listWrong));
   }

    @DisplayName("Tests adding misspelling.")
    @Test
    void addingMisspelling() {
        assertTrue(misspelling.addMisspelling("HRRJRJRJJ","HEJ"));
    }

    @DisplayName("Tries adding the testMisspelling from the setUp, creating a duplicate misspelling error.")
    @Test
    void addingDuplicateMisspelling() {
        assertFalse(misspelling.addMisspelling("TestMisspelling","MisspellingTest"));
    }

    @DisplayName("Tries adding misspeling with spaces")
    @Test
    void addMisspellingWithSpaces() {
        assertFalse(misspelling.addMisspelling("Two Words","OneWord"));
        assertFalse(misspelling.addMisspelling("OneWord","Two Words"));
    }

    @DisplayName("Tries adding a blank misspelling")
    @Test
    void addingBlankMisspellings() {
        assertFalse(misspelling.addMisspelling("blank",""));
        assertFalse(misspelling.addMisspelling("","blank"));
    }

    @DisplayName("Tries deleting the testMisspelling from the setUp.")
    @Test
    void deleteMisspelling() {
        assertTrue(misspelling.deleteMisspelling("TestMisspelling"));
    }

    @DisplayName("Tries to delete a non-existent misspelling.")
    @Test
    void deleteNonExistentMisspelling() {
        assertFalse(misspelling.deleteMisspelling("HRRJRJRJJ"));
    }

    @DisplayName("Attempts to delete a misspelling with spaces.")
    @Test
    void deleteMisspellingWithSpaces() {
        assertFalse(misspelling.deleteMisspelling(" "));
    }

    @DisplayName("Tries deleting with an empty string.")
    @Test
    void emptyDeleteString() {
        assertFalse(misspelling.deleteMisspelling(""));
    }

    @DisplayName("Tries updating the testMisspelling from setUp.")
    @Test
    void updateMisspellings() {
        assertTrue(misspelling.updateMisspelling("TestMisspelling","TestOfMisspelling"));
    }

    @DisplayName("Tries updating a non-existent misspelling.")
    @Test
    void updateNonExistentMisspelling() {
        assertFalse(misspelling.updateMisspelling("nonExistant","Test"));
    }

    @DisplayName("Tries updating a misspelling into a misspelling with spaces")
    @Test
    void updateWithSpaces() {
        assertFalse(misspelling.updateMisspelling("TestMisspelling","Two words"));
    }

    @DisplayName("Tries updating to and from a blank misspelling")
    @Test
    void updateWithBlanks() {
        assertFalse(misspelling.updateMisspelling("TestMisspelling",""));
        assertFalse(misspelling.updateMisspelling("","TestMisspelling"));
    }

}