package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


class Tokenizer {


    public static ArrayList<String> tokenizeHTMLBodyText(HTMLSite site) {
        ArrayList<String> tokens = new ArrayList<>();

        String [] splittedStrings = site.getDocumentText().split(" ");


        for (String s: splittedStrings){

            if (!tokens.contains(s.toLowerCase(Locale.ROOT))){
               tokens.add(new Token(s, site.getId()).documentText.toLowerCase(Locale.ROOT));
            }

        }
        return tokens;
    }
}
