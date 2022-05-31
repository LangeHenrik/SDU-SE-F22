package dk.sdu.se_f22.contentmodule.management.UI;

public class Edit {
    private int id;
    private String html;
    private String timestamp;
    private String articleNr;

    Edit(int id,String html, String timestamp, String articleNr){
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

    public String getArticleNr() {
        return articleNr;
    }
}
