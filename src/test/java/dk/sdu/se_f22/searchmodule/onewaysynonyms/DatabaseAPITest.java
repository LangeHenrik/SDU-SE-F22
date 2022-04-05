package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseAPITest {

    @BeforeEach
    void setUp() {
        try {
            Connection connection = DBConnection.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addItem() {

        String item = "Peters pæne Bukser";

        try {
            DatabaseAPI.addItem(item);
            DatabaseAPI.addItem(item,2);
        } catch (SQLException e) {
            fail("Create query couldn't be accomplished");
            e.printStackTrace();
        }
    }

    @Test
    void updateSuperId() {
        String item = "Peters grimme Bukser";

        try {
            DatabaseAPI.updateSuperId(item,2);
        } catch (Exception e) {
            fail("Update query couldn't be accomplished");
        }
    }

    @Test
    void updateName() {
        String item = "Peters pæne Bukser";
        try {
            DatabaseAPI.updateName(2,item);
        } catch (SQLException e) {
            fail("Update query couldn't be accomplished");
            e.printStackTrace();
        }
    }

    @Test
    void read() {
    }
}