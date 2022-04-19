package Test;

import Data.Management;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ManagementTest {

    @Test
    void create() {
        try {
            Management.Create("This is a test HTML page");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assertEquals("This is a test HTML page", Management.getPageString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPageString() {
        try {
            assertEquals("This is a test HTML page", Management.getPageString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPageDocument() {
        try {
            assert (Management.GetPageDocument(1) != null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update() {
        Management.Update(2, "Sucky sucky, five bucky");
        try {
            assertEquals(Management.getPageString(1), "Speciellægepraktiskplanlægningsstabiliseringsperiode");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        Management.Delete(1);
        try {
            assertNull(Management.getPageString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}