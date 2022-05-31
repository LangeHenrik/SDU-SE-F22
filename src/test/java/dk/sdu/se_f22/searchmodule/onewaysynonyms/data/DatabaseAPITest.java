package dk.sdu.se_f22.searchmodule.onewaysynonyms.data;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseAPITest {

    @BeforeAll
    static void setUp(){
        DatabaseAPI.initializeTable();
    }

    @Test
    void initializeTable() {
        assertTrue(DatabaseAPI.initializeTable());
    }

    @Test
    void addItem() {
        assertFalse(DatabaseAPI.addItem("root",0));
    }

    @Test
    void updateSuperId() {
        assertFalse(DatabaseAPI.updateSuperId(0,0));
    }

    @Test
    void updateName() {
        assertFalse(DatabaseAPI.updateName(0,"Cars"));
    }

    @Test
    void readEntireDB() {
        assertNotNull(DatabaseAPI.readEntireDB());
    }

    @Test
    void deleteItems() {
        assertFalse(DatabaseAPI.deleteItems(0));
    }

    @Test
    void searchBasedOnName() {
        assertEquals(0,DatabaseAPI.searchBasedOnName("root"));
        assertEquals(-1,DatabaseAPI.searchBasedOnName("1"));
    }
}