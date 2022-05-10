package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing.HTMLSite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//done2
class HTMLSiteTest {
    HTMLSite site;

    @BeforeEach
    void setUp() {
        site = new HTMLSite(3,"This is the text of three");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getDocumentText() {
        assertTrue(site.getDocumentText()!= null);

    }

    @Test
    void setDocumentText() {
        site.setDocumentText("New");
        assertTrue(site.getDocumentText().length() == 3);
    }

    @Test
    void getId() {
        assertEquals(3, site.getId());
    }

    @Test
    void setId() {
        site.setId(95);
        assertEquals(95, site.getId());
    }
}