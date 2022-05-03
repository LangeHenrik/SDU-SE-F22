package dk.sdu.se_f22.productmodule.infrastructure.data;

import dk.sdu.se_f22.productmodule.infrastructure.ProductIndexInfrastructure;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
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
    void resetTokenParamter() {
        tokenParameter = new TokenParameter(" ", "('./?!')");
    }

    @Test
    void setIgnoredCharsAsList() {
        List<String> ignoredChars = new ArrayList<>(List.of("@", "=", "?"));
        List<String> ignoredCharsVerify = new ArrayList<>(List.of("@", "=", "?"));
        tokenParameter.setIgnoredChars(ignoredChars);
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
        tokenParameter.save();
        load();
    }

    @Test
    void load() {
        TokenParameter loadedTokenParamter = TokenParameterStore.loadTokenParameter();

        try {
            PreparedStatement stmt = DBConnection.getPooledConnection().prepareStatement("SELECT delimiter, ignored_chars FROM token_parameters WHERE type = 'Product' ORDER BY id DESC LIMIT 1;");
            ResultSet result =  stmt.executeQuery();

            if (result.next()) {
                assertEquals(loadedTokenParamter.getDelimiter(), result.getString("delimiter"));
                assertEquals(loadedTokenParamter.getIgnoredChars(), Arrays.asList(result.getString("ignored_chars").split("")));
            } else {
                assertEquals(loadedTokenParamter.getDelimiter(), " "); // Default value
                assertEquals(loadedTokenParamter.getIgnoredChars(), Arrays.asList("('./?!')".split(""))); // Default value
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}