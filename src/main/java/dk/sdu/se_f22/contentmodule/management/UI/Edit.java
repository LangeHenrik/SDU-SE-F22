package dk.sdu.se_f22.contentmodule.management.UI;

public class Edit {
    private int id;
    private String html;
    private String timestamp;
    private int articleNr;

    Edit(int id, String html, String timestamp, int articleNr){
        this.id = id;
        this.html = html;
        this.timestamp = timestamp;
        this.articleNr = articleNr;
    }

    public int getId() {
        return id;
    }

    public String getHtml() {
        return html;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getArticleNr() {
        return articleNr;
    }
}
