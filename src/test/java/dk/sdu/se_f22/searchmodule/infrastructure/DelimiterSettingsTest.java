package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DelimiterSettingsTest {
    DelimiterSettings ss;
    List<String> initialArray;

    @BeforeEach
    void setUp() {
        ss = new DelimiterSettings();
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement("DELETE FROM searchtokendelimiters");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDelimiters() {
        assertArrayEquals(new String[0], ss.getDelimiters().toArray());

        ss.addDelimiter("hello");

        var delimiters = ss.getDelimiters();
        var expectedDelimiters = List.of("hello");

        assertArrayEquals(expectedDelimiters.toArray(), delimiters.toArray());
        ss.addDelimiter("hello");

        delimiters = ss.getDelimiters();
        assertArrayEquals(expectedDelimiters.toArray(), delimiters.toArray());
    }

    @Test
    void addDelimiters(){
        assertArrayEquals(new String[0], ss.getDelimiters().toArray());

        ss.addDelimiter("hello");

        var delimiters = ss.getDelimiters();
        var expectedDelimiters = List.of("hello");

        assertArrayEquals(expectedDelimiters.toArray(), delimiters.toArray());
        ss.addDelimiter("hello");

        delimiters = ss.getDelimiters();
        assertArrayEquals(expectedDelimiters.toArray(), delimiters.toArray());
    }

    @Test
    void removeDelimiterCanRemove(){
        ss.addDelimiter("hello");
        var expectedDelimiters = List.of("hello");

        assertArrayEquals(expectedDelimiters.toArray(), ss.getDelimiters().toArray());

        assertTrue(ss.removeDelimiter("hello"));
        assertArrayEquals(new String[0], ss.getDelimiters().toArray());
    }
    @Test
    void removeDelimiterCantRemove(){
        assertArrayEquals(new String[0], ss.getDelimiters().toArray());

        assertFalse(ss.removeDelimiter("hello"));

        assertArrayEquals(new String[0], ss.getDelimiters().toArray());
    }
}