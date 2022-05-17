package dk.sdu.se_f22.searchmodule.infrastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReplaceForbiddenCharsTest {
        @Test
        public void testRemoveForbiddenChars() {
            ReplaceForbiddenChars test = new ReplaceForbiddenChars();
            assertEquals("Hej med dig123 Test", test.removeForbiddenChars("H@ej med dig123,.! Test(:;)&%#"));
        }
}