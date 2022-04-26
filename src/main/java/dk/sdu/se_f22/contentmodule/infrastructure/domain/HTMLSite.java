package dk.sdu.se_f22.contentmodule.infrastructure.domain;

public class HTMLSite implements IContentInfrastructre {
    private int id;
    private String documentText;


    public HTMLSite(int id, String documentText) {
        this.id = id;
        this.documentText = documentText;
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
