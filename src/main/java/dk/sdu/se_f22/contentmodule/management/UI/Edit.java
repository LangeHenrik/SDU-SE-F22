package dk.sdu.se_f22.contentmodule.management.UI;

public class Edit {
    private int id;
    private String string;
    private String string1;
    private String string2;

    Edit(int id,String s, String s1, String s2){
        this.id = id;
        string = s;
        string1 = s1;
        string2 = s2;
    }

    public int getId() {
        return id;
    }

    public String getString() {
        return string;
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }
}
