package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;
import java.io.IOException;
import java.util.ArrayList;

//klasser i domain og data
public class Main {
    public static void main(String[] args) throws IOException {

        String searchToken = "this";

        HTMLSite site2 = new HTMLSite(2,"This is the the text of two");
        HTMLSite site3 = new HTMLSite(3,"This is the text of three");


        ArrayList<HTMLSite> webSites = new ArrayList<>();
        webSites.add(site2);
        webSites.add(site3);

        for (Token token: Tokenizer.tokenizeHTMLBodyText(site2)){
            System.out.println(token.documentText);
        }


        


    }
}