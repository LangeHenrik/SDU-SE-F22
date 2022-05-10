package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;

import java.util.*;


public class Tokenizer {

    public HTMLSite tokenizeHTMLBodyText(HTMLSite site) {
        ArrayList<String> tokens = new ArrayList<>();
        String convertedsite = site.getDocumentText();
        Database database = Database.getInstance();

        int pageid = database.getPageID(site.getId());

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

        String [] splittedStrings = convertedsite.split("["+Database.getInstance().getParameters()+"]");


        for (String word: splittedStrings){
            {
                if (word != "") {
                    tokens.add(word.toLowerCase(Locale.ROOT));
                }
            }
        }
        return new HTMLSite(site.getId(), site.getHtmlCode(), tokens);
    }
}

