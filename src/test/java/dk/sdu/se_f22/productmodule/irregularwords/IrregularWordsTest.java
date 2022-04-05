package dk.sdu.se_f22.productmodule.irregularwords;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IrregularWordsTest {

    @Test
    void createIRWord() {
    }

    @Test
    void deleteIRWord() {
    }

    @Test
    void updateIRWord() {
    }

    @Test
    void readIRWord() {
        IrregularWords.irregularWords.initialize();
        assertTrue(IrregularWords.irregularWords.readIRWord());
    }

    @Test
    void getIRWord() {
    }

    @Test
    void getID() {
    }

    @Test
    void searchForIrregularWords() {
    }
}