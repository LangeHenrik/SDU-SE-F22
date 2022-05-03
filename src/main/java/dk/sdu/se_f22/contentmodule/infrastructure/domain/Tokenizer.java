package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


class Tokenizer {


    public static ArrayList<Token> tokenizeHTMLBodyText(HTMLSite site) {
        ArrayList<Token> tokens = new ArrayList<>();

        String [] splittedStrings = site.getDocumentText().split("["+Database.getInstance().getParameters()+"]");


        for (String s: splittedStrings){
            {
                if (s != "") {
                    tokens.add(new Token(s.toLowerCase(Locale.ROOT), site.getId()));
                }
            }
        }
        return tokens;
    }
}

