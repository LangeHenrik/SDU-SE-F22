package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;


import dk.sdu.se_f22.contentmodule.infrastructure.data.DatabaseQueries;

import java.io.IOException;
import java.util.*;



//To be implemented

public class Tokenizer {

    public void tokenizeHTMLBodyText(HTMLSite site) throws IOException {

        ArrayList<String> tokens = new ArrayList<>();
        String convertedsite = site.getDocumentText();
        DatabaseQueries database = new DatabaseQueries();

        int pageid = database.getPageID(site.getId());



        //TODO: Laves om sådan at parametre bliver hentet fra DB og ikke hard coded
        for (int i = 0; i < convertedsite.length(); i++) {
            switch (convertedsite.charAt(i)) {
                case '-' -> database.logParameters(pageid, database.getParameterID('-'));
                case ',' -> database.logParameters(pageid, database.getParameterID(','));
                case '_' -> database.logParameters(pageid, database.getParameterID('_'));
                case ':' -> database.logParameters(pageid, database.getParameterID(':'));
                case '.' -> database.logParameters(pageid, database.getParameterID('.'));
                case ';' -> database.logParameters(pageid, database.getParameterID(';'));
                case ' ' -> database.logParameters(pageid, database.getParameterID(' '));
                default -> { System.out.println("Duplicate parameter or not valid"); }
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
    }
}

