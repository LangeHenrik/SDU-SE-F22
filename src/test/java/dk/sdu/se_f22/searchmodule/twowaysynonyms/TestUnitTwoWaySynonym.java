package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import static org.junit.jupiter.api.Assertions.*;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestUnitTwoWaySynonym {
    static Connection conn = DBConnection.getConnection();
    static TwoWaySynonym operator = TwoWaySynonym.getInstance();
    static String _defaultSynonym;
    static ArrayList<Synonym> _defaultRelatedSynonymCollection;

    @BeforeAll
    static void setUp() {
        TruncateDB();
        _defaultSynonym = "Computer";
        _defaultRelatedSynonymCollection = new ArrayList<Synonym>(){{
            add(new Synonym("sduconst-0000-0000-1000-000const0001", "PC", 1));
            add(new Synonym("sduconst-0000-0000-1000-000const0002", "Computer", 1));
        }};
    }

    @AfterEach
    public void tearDown() {
        TruncateDB();
    }

    @Test
    @DisplayName("Create new synonym")
    public void testCreateNewSynonym() {
        // ARRANGE
        // ACT
        String createdUUID = operator.create("Computer");
        // ASSERT
        assertNotNull(createdUUID);
    }

    @Test
    @DisplayName("Create already existing synonym")
    public void testCreateExistingSynonym() {
        // ARRANGE
        operator.create(_defaultSynonym);
        // ACT
        String createdUUID = operator.create(_defaultSynonym);
        // ASSERT
        assertNull(createdUUID);
    }

    @Test
    @DisplayName("Create synonym in existing group")
    public void testAddNewSynonymToExistingGroup() {
        // ARRANGE
        operator.create(_defaultSynonym);
        String newSynonym = "PC";
        // ACT
        operator.create(newSynonym, _defaultSynonym);
        // ASSERT
        assertEquals(operator.read(_defaultSynonym).groupId(), operator.read(newSynonym).groupId());
    }

    @Test
    @DisplayName("Read single synonym")
    public void testReadSingleSynonym() {
        // ARRANGE
        operator.create("Computer");
        String expectedResult = "Computer";
        // ACT
        Synonym actualResult = operator.read(_defaultSynonym);
        // ASSERT
        assertEquals(expectedResult, actualResult.synonym());
    }

    @Test
    @DisplayName("Read non-existing synonym")
    public void testReadNonExistingSynonym() {
        // ARRANGE
        // ACT
        Synonym actualResult = operator.read(_defaultSynonym);
        // ASSERT
        assertNull(actualResult);
    }

    @Test
    @DisplayName("Read all synonyms from group")
    public void testReadAllRelatedSynonyms() {
        // ARRANGE
        operator.create("Computer");
        operator.create("PC", "Computer");
        // ACT
        int groupId = operator.read(("PC")).groupId();
        ArrayList<Synonym> actualResult = operator.readAll(groupId);
        // ASSERT
        assertEquals(_defaultRelatedSynonymCollection.size(), actualResult.size());
    }

    @Test
    @DisplayName("Delete synonym")
    public void testDeleteSynonym() {
        // ARRANGE
        operator.create(_defaultSynonym);
        // ACT
        boolean status = operator.delete(_defaultSynonym);
        // ASSERT
        assertTrue(status);
    }

    @Test
    @DisplayName("Update synonym spelling")
    public void testUpdateSpelling() {
        // ARRANGE
        operator.create(_defaultSynonym);
        String expectedResult = "COMPUTER";
        // ACT
        operator.updateSpelling(_defaultSynonym, "COMPUTER");
        // ASSERT
        assertEquals(operator.read(expectedResult).synonym(), expectedResult);
    }

    @Test
    @DisplayName("Update group id of synonym")
    public void testUpdateGroupId(){
        // ARRANGE
        operator.create(_defaultSynonym);
        operator.create("PC");
        // ACT
        operator.updateGroupID("PC", _defaultSynonym);
        // ASSERT
        assertEquals(operator.read(_defaultSynonym).groupId(), operator.read("PC").groupId());
    }

    @Test
    @DisplayName("Update group id of synonym")
    public void testFilterMethod(){
        // ARRANGE
        ArrayList<String> tokenList = new ArrayList<>();
        operator.create(_defaultSynonym);
        operator.create("PC", _defaultSynonym);
        tokenList.add(_defaultSynonym);
        // ACT
        ArrayList<String> results = operator.filter(tokenList);
        // ASSERT
        assertTrue(results.contains("PC"));
    }

    private static void TruncateDB() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("TRUNCATE twoway_synonym RESTART IDENTITY");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
