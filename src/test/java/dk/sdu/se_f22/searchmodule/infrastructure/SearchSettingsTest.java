package dk.sdu.se_f22.searchmodule.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchSettingsTest {
    SearchSettings ss;
    List<String> initialArray;

    @BeforeEach
    void setUp() {
        initialArray = new ArrayList<>();
        initialArray.add(" ");
        initialArray.add("p{Punct}");
        ss = new SearchSettings();
    }

    @Test
    void getDelimiters() {
        assertArrayEquals(initialArray.toArray(), ss.getDelimiters().toArray());
        ss.appendDelimiters("m");
        initialArray.add("m");
        assertArrayEquals(initialArray.toArray(), ss.getDelimiters().toArray());
    }

    @Test
    void appendDelimiters() {
        assertArrayEquals(initialArray.toArray(), ss.getDelimiters().toArray());
        ss.appendDelimiters("m");
        initialArray.add("m");
        assertArrayEquals(initialArray.toArray(), ss.getDelimiters().toArray());
    }
}