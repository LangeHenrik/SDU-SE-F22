package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;
import java.io.IOException;
import java.util.ArrayList;

//klasser i domain og data
public class Main {
    public static void main(String[] args) throws IOException {

        HTMLSite site2 = new HTMLSite(2,"This is the the text of two");
        HTMLSite site3 = new HTMLSite(3,"This is the text of three");

        System.out.println(Tokenizer.tokenizeHTMLBodyText(site2).toString());
        System.out.println(Tokenizer.tokenizeHTMLBodyText(site3).toString());

    }
}