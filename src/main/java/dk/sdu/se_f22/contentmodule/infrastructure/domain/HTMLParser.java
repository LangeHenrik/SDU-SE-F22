package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class HTMLParser {

    private File input;

    public HTMLParser(String fileName) {
        this.input = new File(fileName);
    }

    public String parseHTML() throws IOException {
        Document doc = Jsoup.parse(input, "UTF-8");
        return doc.body().text();
    }
}


