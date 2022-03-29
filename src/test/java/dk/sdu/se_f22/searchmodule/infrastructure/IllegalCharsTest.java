package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IllegalCharsTest {

    IllegalChars illegalChars = new IllegalChars();

    @BeforeEach
    void beforeEach()  {
        try {
            PreparedStatement delete = DBConnection.getConnection().prepareStatement("DROP TABLE illegalChars");
            delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement create = DBConnection.getConnection().prepareStatement("CREATE TABLE illegalChars( id SERIAL PRIMARY KEY, " +
                    "characters VARCHAR(50) NOT NULL)");
            create.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Test
    void addChar() throws SQLException {
        illegalChars.addChar("a");
        PreparedStatement queryStatement = DBConnection.getConnection().prepareStatement(
                "SELECT * FROM illegalChars where (id=1)");
        ResultSet queryResultSet = queryStatement.executeQuery();
        queryResultSet.next();
        assertEquals("a", queryResultSet.getString("characters"));

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
        assertEquals(expected,illegalChars.illegalCharsFromDB());
    }

    @Test
    void removeChar() {
    }
}