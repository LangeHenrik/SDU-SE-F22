package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import static org.junit.jupiter.api.Assertions.*;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class TestUnitTwoWaySynonym {
    Connection conn = DBConnection.getConnection();
    static TwoWaySynonym operator = TwoWaySynonym.getInstance();
    static String _defaultSynonym;
    static ArrayList<Synonym> _defaultRelatedSynonymCollection;

    @BeforeAll
    static void setUp() {
        _defaultSynonym = "Computer";
        _defaultRelatedSynonymCollection = new ArrayList<Synonym>(){{
            add(new Synonym(UUID.fromString("sduconst-0000-0000-1000-000const0001"), "PC", 1));
            add(new Synonym(UUID.fromString("sduconst-0000-0000-1000-000const0002"), "Computer", 1));
        }};
    }

    @AfterEach
    public void tearDown() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("TRUNCATE twoway_synonym RESTART IDENTITY ");
            System.out.println("Temp database has been wiped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Create new synonym")
    public void testCreateNewSynonym() {
        // ARRANGE
        // ACT
        UUID createdUUID = operator.create("Computer");
        // ASSERT
        assertNotNull(createdUUID);
    }

    @Test
    @DisplayName("Create already existing synonym")
    public void testCreateExistingSynonym() {
        // ARRANGE
        operator.create(_defaultSynonym);
        // ACT
        UUID createdUUID = operator.create(_defaultSynonym);
        // ASSERT
        assertNull(createdUUID);
    }

    @Test
    @Disabled
    @DisplayName("Create synonym in existing group")
    public void testAddNewSynonymToExistingGroup() {
        // ARRANGE
        String newSynonym = "PC";
        // ACT
        UUID createdUUID = operator.create(newSynonym, _defaultSynonym);
        // ASSERT
        assertNotNull(createdUUID);
    }

    @Test
    @DisplayName("Read single synonym")
    public void testReadSingleSynonym() {
        // ARRANGE
        String expectedResult = "Computer";
        // ACT
        Synonym actualResult = operator.read(_defaultSynonym);
        // ASSERT
        assertEquals(expectedResult, actualResult.synonym());
    }

    @Test
    @DisplayName("Read all synonym")
    public void testReadAllRelatedSynonyms() {
        // ARRANGE
        operator.create("Computer");
        operator.create("PC", "Computer");
        // ACT
        ArrayList<Synonym> actualResult = operator.readAll(_defaultSynonym);
        // ASSERT
        assertArrayEquals(_defaultRelatedSynonymCollection.toArray(), actualResult.toArray());
    }
}
