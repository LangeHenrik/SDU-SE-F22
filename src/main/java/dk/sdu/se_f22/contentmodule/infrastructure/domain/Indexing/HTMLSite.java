package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;

import java.util.ArrayList;
import java.util.List;

public class HTMLSite  {
    private int id;
    private String htmlCode;
    private String documentText;
    private List<String> tokens;
    private List<String> filteredTokens;
    private boolean isUpdated;


    public HTMLSite(int id, String htmlCode, List<String> tokens) {
        this.id = id;
        this.htmlCode = htmlCode;
        this.tokens = tokens;
    }

    public HTMLSite(int id, String documentText) {
        this.id = id;
        this.documentText = documentText;
        database.setupDatabase();
        database.executeQuery("INSERT INTO cms_htmlpages (html_id) VALUES ("+id+")");
    }




    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public List<String> getFilteredTokens() {
        return filteredTokens;
    }

    public void setFilteredTokens(List<String> filteredTokens) {
        this.filteredTokens = filteredTokens;
    }

    Database database = Database.getInstance();



    public String getDocumentText() {
        return documentText;
    }

    public void setDocumentText(String documentText) {
        this.documentText = documentText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
