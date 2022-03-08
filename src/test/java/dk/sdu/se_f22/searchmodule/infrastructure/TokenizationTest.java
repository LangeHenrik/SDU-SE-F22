package dk.sdu.se_f22.searchmodule.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizationTest {
    Tokenization t;
    List<String> s;

    @BeforeEach
    void setUp() {
        t = new Tokenization();
        s = new ArrayList<>();
        s.add("Hej");
        s.add("hje");
        s.add("hej");
    }

    @Test
    void tokenize() {
        assertEquals(s, t.tokenize("Hej. hje  !hej"));
    }
}