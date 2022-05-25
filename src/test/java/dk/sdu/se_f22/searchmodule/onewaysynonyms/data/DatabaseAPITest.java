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
        DatabaseAPI.addItem("Vehicle",0);
    }

    @Test
    void initializeTable() {
        assertTrue(DatabaseAPI.initializeTable());
    }

    @Test
    void addItem() {
        assertTrue(DatabaseAPI.addItem("Object",0));
    }

    @Test
    void updateSuperId() {
        assertTrue(DatabaseAPI.updateSuperId(2,0));
    }

    @Test
    void updateName() {
        assertTrue(DatabaseAPI.updateName(2,"Cars"));
    }

    @Test
    void readEntireDB() {
        assertNotNull(DatabaseAPI.readEntireDB());
    }

    @Test
    void deleteItems() {
        assertTrue(DatabaseAPI.deleteItems(1));
    }

    @Test
    void searchBasedOnName() {
        assertEquals(0,DatabaseAPI.searchBasedOnName("root"));
        assertEquals(-1,DatabaseAPI.searchBasedOnName("1"));
    }
}