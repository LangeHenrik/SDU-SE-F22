package Domain;

import org.jsoup.nodes.Document;

public class DocumentSorter {
    public String getHead(Document inputDoc) { //The method "under" this comment, and the 3 following are for getting the text version of the different parts of the document
        return inputDoc.head().text();
    }

    public String getHeadToString(Document inputDoc) {
        return inputDoc.head().toString();
    }

    public String getBody(Document inputDoc) {
        return inputDoc.body().text();
    }

    public String getBodyToString(Document inputDoc) {
        return inputDoc.body().toString();
    }

    public String selectById(Document inputDoc, String id, boolean isText) { //For selecting a document part, based on the ID it is given in the HTML file
        if (isText) {
            return inputDoc.getElementById(id).text();
        } else {
            return inputDoc.getElementById(id).toString();
        }
    }
}