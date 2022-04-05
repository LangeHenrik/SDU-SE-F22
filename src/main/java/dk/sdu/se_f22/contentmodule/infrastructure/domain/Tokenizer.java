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

        String[] splittedString;
//
//
//        for (HTMLSite site: webSites){
//            bodyText.add(site.getDocumentText());
//        }

        for (int i = 0; i < webSites.size(); i++) {
            splittedString = webSites.get(i).getDocumentText().split("[-.,;:_!?]");
            for (String s: splittedString){
                tokens.add(s.toLowerCase(Locale.ROOT));
            }
        }
//
//
//        for (int i = 0; i < bodyText.size(); i++) {
//            splittedString = bodyText.get(i).split("[-.,;:_!?]");
//            for (String s : splittedString) {
//                if (s != "") {
//                    String string = s.toLowerCase(Locale.ROOT);
//                    tokens.add(string);
//                }
//            }
//        }
//
//
        return tokens;
    }


}
