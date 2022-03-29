package dk.sdu.se_f22.searchmodule.misspellings;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MisspellingsTest {
    Misspellings misspelling = new Misspellings();
    private static final String url = "jdbc:postgresql://abul.db.elephantsql.com/hzajyqbo";
    private static final String user = "hzajyqbo";
    private static final String password = "K8664qtGojuBvQczzv66EhaqkUNbXLj0";

    ArrayList<String> listWrong = new ArrayList<String>();
    ArrayList<String> listCorrect = new ArrayList<String>();



    @BeforeEach
    void setUp() {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
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
        //Wrong methods for filter method
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
    }


    @DisplayName("Tests the filter method my comparing two ArrayLists, to misspellings saved in the database during setup.")
    @Test
    void filter() {
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

    @DisplayName("Tries deleting the testMisspelling from the setUp.")
    @Test
    void deleteMisspelling() {
        assertTrue(misspelling.deleteMisspelling("TestMisspelling"));
    }

    @DisplayName("Tries to delete a non existant misspelling.")
    @Test
    void deleteNonExistantMisspelling() {
        assertFalse(misspelling.deleteMisspelling("HRRJRJRJJ"));
    }

    @DisplayName("Updates the testMisspelling from setUp.")
    @Test
    void updateMisspellings() {
        assertTrue(misspelling.updateMisspelling("TestMisspelling","TestOfMisspelling"));
    }

    @DisplayName("Tries updating a non existant misspelling.")
    @Test
    void updateNonExistantMisspelling() {
        assertFalse(misspelling.updateMisspelling("nonExistant","Test"));
    }


}