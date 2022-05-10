package dk.sdu.se_f22.contentmodule.management;

import dk.sdu.se_f22.contentmodule.management.Data.*;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ManagementTest {

    @Test
    void create() {
        try {
            int id = Management.Create("This is a test HTML page");
            assertEquals("This is a test HTML page", Management.getPageString(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPageString() {
        try {
            int id = Management.Create("This is a test HTML page");
            assertEquals("This is a test HTML page", Management.getPageString(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPageDocument() {
        try {
            int id = Management.Create("do you get page document?");
            assert (Management.GetPageDocument(id) != null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update() {

        try {
            int id = Management.Create("Sucky sycky, four buddy");
            Management.Update(id, "Sucky sucky, five bucky");
            assertEquals(Management.getPageString(id), "Sucky sucky, five bucky");
            Management.Delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        try {
            int id = Management.Create("This should be deleted");
            Management.Delete(id);
            assertThrowsExactly(PSQLException, Management.getPageString(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}