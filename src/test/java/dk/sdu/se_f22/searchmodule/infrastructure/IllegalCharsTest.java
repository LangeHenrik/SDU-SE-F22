package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IllegalCharsTest {

    IllegalChars illegalChars = new IllegalChars();

    @BeforeEach
    void beforeEach() {
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement delete = connection.prepareStatement("DROP TABLE illegalChars");
            delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement create = connection.prepareStatement("CREATE TABLE illegalChars( id SERIAL PRIMARY KEY, " + "characters VARCHAR(50) NOT NULL)");
            create.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addChar() throws SQLException {
        illegalChars.addChar("a");
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM illegalChars where (id=1)");
            ResultSet queryResultSet = queryStatement.executeQuery();
            queryResultSet.next();
            assertEquals("a", queryResultSet.getString("characters"));
        }
    }

    @Test
    void illegalCharsFromDB() {
        illegalChars.addChar("a");
        illegalChars.addChar("b");
        illegalChars.addChar("c");
        List<String> expected = new ArrayList<String>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        assertEquals(expected, illegalChars.illegalCharsFromDB());
    }

    @Test
    void removeChar() throws SQLException {
        illegalChars.addChar("a");
        illegalChars.removeChar("a");
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM illegalChars where (id=1)");
            ResultSet queryResultSet = queryStatement.executeQuery();
            queryResultSet.next();
            assertThrows(PSQLException.class, () -> queryResultSet.getString("characters"));
        }
    }
}