package dk.sdu.se_f22.sharedlibrary.models;

public class Content {
    private int pageId;
    private String html;
    private String title;
    private String timestamp;

    public Content(int pageId, String html, String title, String timestamp) {

    }

    public Content(int pageId, Document html, String timestamp) {

    }
}
