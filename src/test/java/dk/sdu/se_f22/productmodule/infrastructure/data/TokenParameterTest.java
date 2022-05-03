package dk.sdu.se_f22.productmodule.infrastructure.data;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenParameterTest {
    private TokenParameter tokenParameter;

    @BeforeEach
    void resetTokenParameter() {
        tokenParameter = new TokenParameter(TokenParameterStore.DELIMITER_DEFAULT, TokenParameterStore.IGNORED_CHARS_DEFAULT);
    }

    @Test
    void setIgnoredCharsAsList() {
        List<String> ignoredChars = new ArrayList<>(List.of("@", "=", "?"));
        List<String> ignoredCharsVerify = new ArrayList<>(List.of("@", "=", "?"));
        tokenParameter.setIgnoredChars(ignoredChars);

        // Sorting is only done here, since the IterableEquals also compared order, and since we don't care about the order in the actual
        // implementation, we just sort and verify they match element wise.
        Collections.sort(ignoredChars);
        Collections.sort(ignoredCharsVerify);

        assertIterableEquals(tokenParameter.getIgnoredChars(), ignoredCharsVerify);
    }

    @Test
    void setIgnoredCharsAsString() {
        String ignoredChars = "=?@";
        List<String> ignoredCharsVerify = new ArrayList<>(List.of("@", "=", "?"));
        tokenParameter.setIgnoredChars(ignoredChars);

        List<String> ignoredChars2 = tokenParameter.getIgnoredChars();

        // Sorting is only done here, since the IterableEquals also compared order, and since we don't care about the order in the actual
        // implementation, we just sort and verify they match element wise.
        Collections.sort(ignoredCharsVerify);
        Collections.sort(ignoredChars2);

        assertIterableEquals(ignoredCharsVerify, ignoredChars2);
    }

    @Test
    void setDelimiter() {
        String newDelimiter = "; ";
        tokenParameter.setDelimiter(newDelimiter);

        assertEquals(tokenParameter.getDelimiter(), newDelimiter);
    }

    @Test
    void save() {
        // Save the default token parameter.
        tokenParameter.save();
        // Now check load again, since we know it has been saved.
        load();
    }

    @Test
    void load() {
        TokenParameter loadedTokenParameter = TokenParameterStore.loadTokenParameter();

        try {
            PreparedStatement stmt = DBConnection.getPooledConnection().prepareStatement("SELECT delimiter, ignored_chars FROM token_parameters WHERE type = 'Product' ORDER BY id DESC LIMIT 1;");
            ResultSet result =  stmt.executeQuery();

            if (result.next()) {
                // If hit was found, compared the actual object returned by the load method to the DB values.
                assertEquals(loadedTokenParameter.getDelimiter(), result.getString("delimiter"));
                assertEquals(loadedTokenParameter.getIgnoredChars(), Arrays.asList(result.getString("ignored_chars").split("")));
            } else {
                // Else check that it properly returns a TokenParameter object with default values.
                assertEquals(loadedTokenParameter.getDelimiter(), TokenParameterStore.DELIMITER_DEFAULT); // Default value
                assertEquals(loadedTokenParameter.getIgnoredChars(), Arrays.asList(TokenParameterStore.IGNORED_CHARS_DEFAULT.split(""))); // Default value
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}