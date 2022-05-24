package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.HTML;

import static org.junit.jupiter.api.Assertions.*;

class HTMLParserTest {
    HTMLSite site;

    @BeforeEach
    void setUp() {
        site = new HTMLSite(0, null);
        site.setHtmlCode("<!doctype html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>This is the title of the webpage!</title>\n" +
                "  </head>\n" +
                "  <body>");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parseHTML(HTMLSite site) {
        assertNotNull(site.getDocumentText());
    }
}