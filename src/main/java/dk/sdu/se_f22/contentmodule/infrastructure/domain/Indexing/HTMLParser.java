package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

class HTMLParser {


    public HTMLParser() {

    }

    HTMLSite parseHTML(HTMLSite newSite) throws IOException {

        Document doc = Jsoup.parse(newSite.getHtmlCode(), "UTF-8");
        newSite.setDocumentText(doc.body().text());

        return newSite;
    }
}
