package dk.sdu.se_f22.BrandModule.Stemming;

import dk.sdu.se_f22.BrandModule.Stemming.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordTest {

    @Test
    @DisplayName("Test that the isCons method works properly")
    public void testIsCons() {
        Word testWord = new Word("Calculatedy");

        assertTrue(testWord.isCons(2));

        assertFalse(testWord.isCons(1));

        assertFalse(testWord.isCons(10));
    }
}
