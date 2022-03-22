package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;
import java.io.IOException;
import java.util.ArrayList;

//klasser i domain og data
public class Main {
    public static void main(String[] args) throws IOException {
        HTMLParser parser = new HTMLParser("src/index.html");
        String parsedHTML = parser.parseHTML();


        ArrayList<String> tokens = Tokenizer.tokenizeString(parsedHTML);



        Database database = new Database();
        database.setupDatabase();
        database.createTable("filtered_tokens");

        database.createTable("unfiltered_tokens");
        database.saveTokens("unfiltered_tokens", tokens);

        tokens = database.loadTokens("unfiltered_tokens");
        database.printTokens(tokens);
    }
}