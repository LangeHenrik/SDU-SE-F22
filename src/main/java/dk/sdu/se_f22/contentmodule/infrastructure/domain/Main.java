package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;
import java.io.IOException;
import java.util.ArrayList;

//klasser i domain og data
public class Main {
    public static void main(String[] args) throws IOException {

        HTMLSite site1 = new HTMLSite(1, "This is the text of document one for test");
        HTMLSite site2 = new HTMLSite(2, "This is the text of document two !for test");

        ArrayList<HTMLSite> webSites = new ArrayList<>();
        webSites.add(site1);
        webSites.add(site2);

        Tokenizer.tokenizeHTMLBodyText(webSites);

        System.out.println(Tokenizer.tokenizeHTMLBodyText(webSites).toString());



        /*ArrayList<String> tokens = Tokenizer.tokenizeString(parsedHTML);

        Database database = new Database();
        database.setupDatabase();
        database.createTable("filtered_tokens");

        database.createTable("unfiltered_tokens");
        database.saveTokens("unfiltered_tokens", tokens);

        tokens = database.loadTokens("unfiltered_tokens");
        database.printTokens(tokens);*/
    }
}