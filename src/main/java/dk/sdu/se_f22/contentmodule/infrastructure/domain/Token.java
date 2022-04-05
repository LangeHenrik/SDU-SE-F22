package dk.sdu.se_f22.contentmodule.infrastructure.domain;

public class Token {
    String documentText;
    int originID;

    public Token(String documentText, int originID) {
        this.documentText = documentText;
        this.originID = originID;
    }

    public String getDocumentText() {
        return documentText;
    }

    public void setDocumentText(String documentText) {
        this.documentText = documentText;
    }

    public int getOriginID() {
        return originID;
    }

    public void setOriginID(int originID) {
        this.originID = originID;
    }


}
