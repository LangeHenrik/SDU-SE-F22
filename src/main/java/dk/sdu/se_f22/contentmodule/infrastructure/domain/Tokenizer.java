package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Tokenizer {

    static HashMap<Integer, String> HTMLSites;

    public Tokenizer(HashMap<Integer, String> HTMLSites) {
        this.HTMLSites = HTMLSites;
    }


    public static ArrayList<String> tokenizeHTMLBodyText(HashMap HTMLSites){


        ArrayList<String> bodyText = new ArrayList<>();
        String[] splittedString;
        ArrayList<String> tokens = new ArrayList();

        for (Object theValues: HTMLSites.values()){
            bodyText.add((String) theValues);
        }

        for (int i = 0; i < bodyText.size() ; i++) {
            splittedString = bodyText.get(i).split("[-.,;:_!?]");
            for (String s : splittedString) {
                if (s != "") {
                    String string = s.toLowerCase(Locale.ROOT);
                    tokens.add(string);
                }
            }
        }


        return tokens;
    }




    }






