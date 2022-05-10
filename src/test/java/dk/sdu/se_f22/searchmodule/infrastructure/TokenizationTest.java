package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.tokenization.DelimiterSettings;
import dk.sdu.se_f22.searchmodule.infrastructure.tokenization.Tokenizer;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizationTest {
    Tokenizer t;
    List<String> s;

    @BeforeEach
    void setUp() {
        DelimiterSettings ds = new DelimiterSettings();
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement("DELETE FROM searchtokendelimiters");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ds.addDelimiter(".");
        ds.addDelimiter("!");
        ds.addDelimiter(" ");
        t = new Tokenizer();
        s = new ArrayList<>();
        s.add("Hej");
        s.add("hje");
        s.add("hej");
    }

    @Test
    void tokenizeWithSettings() {
        assertEquals(s, t.tokenize("Hej. hje  !hej"));
    }

    @Test
    void tokenizeWithNoSettings(){
        try (Connection connection = DBConnection.getPooledConnection()){
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM searchtokendelimiters");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        t = new Tokenizer();
        assertEquals(List.of("Hej. hje  !hej"), t.tokenize("Hej. hje  !hej"));
    }

}