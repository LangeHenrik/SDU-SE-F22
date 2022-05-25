package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


class HTMLParser {


    public HTMLParser() {
    }

    void parseHTML(HTMLSite newSite){

        Document doc = Jsoup.parse(newSite.getHtmlCode(), "UTF-8");
        newSite.setDocumentText(doc.body().text());


    }
}
