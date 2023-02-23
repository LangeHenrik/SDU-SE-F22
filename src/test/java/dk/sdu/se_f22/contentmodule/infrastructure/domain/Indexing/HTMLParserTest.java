package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                "  <body><div>This is a page!</div></body></html>");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parseHTML() {
        HTMLParser htmlParser = new HTMLParser();
        htmlParser.parseHTML(site);
        assertEquals("This is a page!",  site.getDocumentText());
    }
}
