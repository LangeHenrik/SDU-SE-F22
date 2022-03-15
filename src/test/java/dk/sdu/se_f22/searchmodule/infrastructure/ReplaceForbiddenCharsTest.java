package dk.sdu.se_f22.searchmodule.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReplaceForbiddenCharsTest {
    ReplaceForbiddenChars replaceForbiddenChars;

    @BeforeEach
    void setUp() {
        this.replaceForbiddenChars = new ReplaceForbiddenChars();

    }

    @Test
    public void testRemoveForbiddenChars() {
        assertEquals("Hej med dig123 Test", this.replaceForbiddenChars.removeForbiddenChars("H@ej m´ed dig|123 Tes*t"));
    }

    @Test
    void testGetIllegalChars() {
        String[] s = {"@", "´", "|", "*"};
        String[] s2 = {"@", "´", "|", "*", "#"};

        assertEquals(List.of(s), replaceForbiddenChars.getIllegalChars());
        replaceForbiddenChars.addIllegalChars("#");
        assertEquals(List.of(s2), replaceForbiddenChars.getIllegalChars());
    }

    @Test
    void testAddIllegalChars() {
        String[] s = {"@", "´", "|", "*"};
        String[] s2 = {"@", "´", "|", "*", "#"};

        assertEquals(List.of(s), replaceForbiddenChars.getIllegalChars());
        replaceForbiddenChars.addIllegalChars("#");
        assertEquals(List.of(s2), replaceForbiddenChars.getIllegalChars());
    }

    @Test
    void testSetIllegalChars() {
        String[] s = {"@", "´", "|", "*"};
        String[] s2 = {"@", "´", "|", "*", "#"};

        assertEquals(List.of(s), replaceForbiddenChars.getIllegalChars());
        replaceForbiddenChars.setIllegalChars(new ArrayList<>(List.of(s2)));
        assertEquals(List.of(s2), replaceForbiddenChars.getIllegalChars());
    }
}