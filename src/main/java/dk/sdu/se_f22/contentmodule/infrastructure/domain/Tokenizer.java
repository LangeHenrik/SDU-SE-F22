package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.ArrayList;
import java.util.Locale;

class Tokenizer {
    static ArrayList<String> tokenizeString(String text) {
        String[] splittedString = text.split("[-.,;:_ ]");
        ArrayList<String> tokens = new ArrayList();

        for (String s : splittedString) {
            if (s != "") {
                String string = s.toLowerCase(Locale.ROOT);
                tokens.add(string);
            }
        }
        return tokens;
        //filterStopwords(tokens); //preparation for chain reaction, change method to void, remove return statement
    }
}
