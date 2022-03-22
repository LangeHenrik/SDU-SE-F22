package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseAPITest {

    @BeforeEach
    void setUp() {
        Connection connection = DBConnection.getConnection();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addItem() {
        String item = "Peters grimme Bukser";




    }
    @Test
    void testAddItem() {
    }

    @Test
    void updateSuperId() {
    }

    @Test
    void updateName() {
    }

    @Test
    void read() {

    }
}