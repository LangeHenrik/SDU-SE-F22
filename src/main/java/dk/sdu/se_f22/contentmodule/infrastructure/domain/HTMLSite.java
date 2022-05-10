package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;

public class HTMLSite implements IContentInfrastructre {
    private int id;
    private String documentText;
    Database database = Database.getInstance();


    public HTMLSite(int id, String documentText) {
        this.id = id;
        this.documentText = documentText;
        database.setupDatabase();
        database.executeQuery("INSERT INTO cms_htmlpages (html_id) VALUES ("+id+")");
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

    @Override
    public HTMLSite convertedFile() {
        return null;
    }
}
