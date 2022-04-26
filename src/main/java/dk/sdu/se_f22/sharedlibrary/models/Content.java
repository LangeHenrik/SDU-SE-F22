package dk.sdu.se_f22.sharedlibrary.models;

/**
 * THIS IS A MOCK CONTENT OBJECT!
 */
public class Content {
    private int id;
    public Content(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return ""+id;
    }
}
