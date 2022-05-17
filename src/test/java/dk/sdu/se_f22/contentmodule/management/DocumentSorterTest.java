package dk.sdu.se_f22.contentmodule.management;

import dk.sdu.se_f22.contentmodule.management.Domain.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DocumentSorterTest {
    HTMLParser htmlParser = new HTMLParser("src/main/resources/dk/sdu/se_f22/contentmodule/management/Test.html");
    DocumentSorter documentSorter = new DocumentSorter();

    DocumentSorterTest() throws IOException {
    }

    @Test
    void getHead() {
        assertEquals("Test", documentSorter.getHead(htmlParser.getParsedHTML()));
    }

   @Test
    void getHeadToString() {
        assertEquals("<head> \n" +
                " <meta charset=\"UTF-8\"> \n" +
                " <title>Test</title> \n" +
                "</head>", documentSorter.getHeadToString(htmlParser.getParsedHTML()));
    }

    @Test
    void getBody() {
        assertEquals("Thesadsasdasdasdasdasdasdasdasdasdasd-wword Hello my name is Lorem Ipsum This is also a test",documentSorter.getBody(htmlParser.getParsedHTML()));
    }

    @Test
    void getBodyToString() {
        assertEquals("<body> \n" +
                " <h1 id=\"test1\">Thesadsasdasdasdasdasdasdasdasdasdasd-wword</h1> \n" +
                " <p id=\"testtest1\">Hello my name is Lorem Ipsum</p> \n" +
                " <h2 id=\"test2\">This is also a test</h2>  \n" +
                "</body>",documentSorter.getBodyToString(htmlParser.getParsedHTML()));
    }

    @Test
    void selectById() {
        assertEquals("<h1 id=\"test1\">Thesadsasdasdasdasdasdasdasdasdasdasd-wword</h1>",documentSorter.selectById(htmlParser.getParsedHTML(),"test1",false));
    }
}