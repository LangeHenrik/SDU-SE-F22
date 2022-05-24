package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;


import dk.sdu.se_f22.contentmodule.infrastructure.data.DatabaseQueries;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;



//To be implemented

public class Tokenizer {

    public HTMLSite tokenizeHTMLBodyText(HTMLSite site) throws IOException {

        ArrayList<String> tokens = new ArrayList<>();
        String convertedsite = site.getDocumentText();
        DatabaseQueries database = new DatabaseQueries();

        int pageid = database.getPageID(site.getId());



        char chararray[] = database.getParameters().toCharArray();
        for (int i = 0; i < convertedsite.length(); i++) {
            for (int j = 0; j < database.getParameters().length(); j++) {
                char c = Array.getChar(chararray, j);
                if (convertedsite.charAt(i) == c) {
                    database.logParameters(pageid, database.getParameterID(c));
                }
            }
        }

        String [] splittedStrings = convertedsite.split("["+database.getParameters()+"]");


        for (String word: splittedStrings){
            {
                if (word != "") {
                    tokens.add(word.toLowerCase(Locale.ROOT));
                }
            }
        }
        site.setTokens(tokens);
        return site;
    }
}

