package dk.sdu.se_f22.contentmodule.infrastructure.domain;


import dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing.HTMLSite;
import dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing.Tokenizer;
import org.junit.internal.runners.statements.Fail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


//done2
class TokenizerTest {
    Tokenizer tk;
    HTMLSite site;

    @BeforeEach
    void setUp() {
        tk = new Tokenizer();
        site = new HTMLSite(500, "This is a sample text");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
     void tokenizeHTMLBodyText() {
        // Test metode
        try {
            assertEquals(5, tk.tokenizeHTMLBodyText(site).getTokens().size());
        } catch (IOException e) {
            fail();
        }
    }
}