package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;


import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HTMLSite  {
    private int id;
    private String htmlCode;
    private String documentText;
    private List<String> tokens;
    private List<String> filteredTokensList;

    //Constructor for a created htmlpage
    public HTMLSite(int id, String documentText) {
        this.id = id;
        this.documentText = documentText;

        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO cms_htmlpages (html_id) VALUES (?)");
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Constructor for unit testing
    public HTMLSite(int id, String htmlCode, String documentText, List<String> tokens) {
        this.id = id;
        this.htmlCode = htmlCode;
        this.documentText = documentText;
        this.tokens = tokens;
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
        return filteredTokensList;
    }

    public void setFilteredTokens(List<String> filteredTokens) {

        this.filteredTokensList = filteredTokens;
    }

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
