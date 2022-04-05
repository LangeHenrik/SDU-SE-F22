package dk.sdu.se_f22.contentmodule.infrastructure.domain;

public class Token {
    String documentText;
    int originID;

    public Token(String documentText, int originID) {
        this.documentText = documentText;
        this.originID = originID;
    }
}
