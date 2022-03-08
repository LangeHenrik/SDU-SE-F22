package dk.sdu.se_f22.searchmodule.infrastructure;

public class TestMain {
    public static void main(String[] args) {
        Tokenization t = new Tokenization();
        System.out.println(t.tokenize("Hej. hje"));
    }
}
