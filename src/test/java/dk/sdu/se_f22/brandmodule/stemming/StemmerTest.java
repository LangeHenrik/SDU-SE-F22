package dk.sdu.se_f22.brandmodule.stemming;

import dk.sdu.se_f22.sharedlibrary.db.DBMigration;
import dk.sdu.se_f22.sharedlibrary.db.DBMigrationFresh;
import dk.sdu.se_f22.sharedlibrary.db.MigrationException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StemmerTest {

    private Stemmer stemmer;

    @BeforeEach
    public void initStemmer() {
        stemmer = new Stemmer();
    }

    @BeforeAll
    public static void initDB() {
        try {
            new DBMigration().migrateFresh();
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Full Stemming Algorithm")
    public void testStem() {
        assertEquals(stemmer.stem("t"), "t");
    }

    @Test
    @DisplayName("Adding Exception")
    public void testAddException() {
        stemmer.addException("TEST");
        assertTrue(stemmer.exceptionExists("TEST"));
    }

    @Test
    @DisplayName("Updating Exceptions")
    public void testUpdateException() {
        stemmer.updateException("test", "updatedtest");
        assertTrue(stemmer.exceptionExists("updatedTest"));
    }

    @Test
    @DisplayName("Getting Exceptions")
    public void testGetExceptions() {
        assertTrue(stemmer.getExceptions().size() > 0);
    }

    @Test
    @DisplayName("Deleting Exceptions")
    public void testDeleteException() {
        stemmer.removeException("updatedtest");
        assertFalse(stemmer.exceptionExists("updatetest"));
    }
}