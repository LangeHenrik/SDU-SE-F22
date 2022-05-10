package dk.sdu.se_f22.contentmodule.management;

import dk.sdu.se_f22.contentmodule.management.Data.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ManagementTest {
    private int test_id = 1;

    @Test
    void create() {
        try {
            Management.Create("This is a test HTML page");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assertEquals("This is a test HTML page", Management.getPageString(test_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPageString() {
        try {
            assertEquals("This is a test HTML page", Management.getPageString(test_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPageDocument() {
        try {
            assert (Management.GetPageDocument(test_id) != null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update() {
        Management.Update(test_id, "Sucky sucky, five bucky");
        try {
            assertEquals(Management.getPageString(test_id), "This is a test HTML page");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        Management.Delete(test_id);
        try {
            assertNull(Management.getPageString(test_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}