package dk.sdu.se_f22.searchmodule.misspellings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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


        try {
            assertEquals(listCorrect, misspelling.filter(listWrong));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addMisspellings() {
        

    }

    @Test
    void deleteMisspellings() {

    }

    @Test
    void updateMisspellings() {

    }

    @AfterEach
    void tearDown() {
    }
}