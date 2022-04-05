package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


class Tokenizer {

    ArrayList<HTMLSite> webSites;


    public Tokenizer(ArrayList<HTMLSite> webSites) {
        this.webSites = webSites;

    }


    public static ArrayList<String> tokenizeHTMLBodyText(ArrayList<HTMLSite> webSites) {
        ArrayList<String> tokens = new ArrayList<>();
        String [] splitString;

        for (int i = 0; i < webSites.size(); i++) {
            splitString = webSites.get(i).getDocumentText().split(" ");

            for (String s: splitString){
                if (! tokens.contains(s.toLowerCase(Locale.ROOT))){
                    tokens.add(s.toLowerCase(Locale.ROOT));
                }

            }

        }
//
//
        return tokens;
    }


}
