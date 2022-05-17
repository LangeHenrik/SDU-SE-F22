package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing.HTMLSite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//done2
class HTMLSiteTest {
    HTMLSite site;
    ArrayList<String> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList<String>();
        list.add("Test");
        site = new HTMLSite(3, "<body> A lot of html code<\\body>" ,"This is the text of three", list);


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

    @Test
    void getHtmlCode() {
        assertTrue(site.getHtmlCode()!= null);

    }

    @Test
    void setHtmlCode() {
        site.setHtmlCode("New");
        assertTrue(site.getHtmlCode().length() == 3);
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
    void getTokens() {
        assertTrue(site.getTokens()!= null);

    }

    @Test
    void setFilteredTokens() {
        site.setTokens(list);
        assertTrue(site.getTokens().size() == 1);
    }

    @Test
    void getFilteredTokens() {
        assertTrue(site.getTokens()!= null);

    }

    @Test
    void setTokens() {
        site.setTokens(list);
        assertTrue(site.getTokens().size() == 1);
    }


    @AfterEach
    void tearDown() {

    }
}