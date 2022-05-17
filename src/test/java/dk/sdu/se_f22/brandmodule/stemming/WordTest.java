package dk.sdu.se_f22.brandmodule.stemming;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordTest {

    @Test
    @DisplayName("Test getMeasure method")
    public void testGetMeasure() {
        assertEquals(0, new Word("tr").getMeasure());
        assertEquals(0, new Word("ee").getMeasure());
        assertEquals(0, new Word("tree").getMeasure());
        assertEquals(0, new Word("y").getMeasure());
        assertEquals(0, new Word("by").getMeasure());

        assertEquals(1, new Word("trouble").getMeasure());
        assertEquals(1, new Word("oats").getMeasure());
        assertEquals(1, new Word("trees").getMeasure());
        assertEquals(1, new Word("ivy").getMeasure());

        assertEquals(2, new Word("troubles").getMeasure());
        assertEquals(2, new Word("private").getMeasure());
        assertEquals(2, new Word("oaten").getMeasure());
        assertEquals(2, new Word("orrery").getMeasure());
    }

    @Test
    @DisplayName("Test that the isCons method works properly")
    public void testIsCons() {
        Word testWord = new Word("Calculatedy");
        assertTrue(testWord.isCons(2));
        assertFalse(testWord.isCons(1));
        assertFalse(testWord.isCons(10));
    }

    @Test
    @DisplayName("Test that the endsWithDoubleCons returns the proper value")
    public void testEndsWithDoubleCons() {

    }

    @Test
    @DisplayName("Test ends with CVC")
    public void testEndsWithCVC() {
        assertFalse(new Word("on").endsWithCVC());
        assertTrue(new Word("cac").endsWithCVC());
    }
}
