package dk.sdu.se_f22.brandmodule.stemming;

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


    @Test
    @DisplayName("Full Stemming Algorithm")
    public void testStem() {
        assertEquals(stemmer.stem("t"), "t");
    }
}