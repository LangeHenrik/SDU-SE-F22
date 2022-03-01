package dk.sdu.se_f22.searchmodule.infrastructure;

import java.util.ArrayList;

public class TokenFiltration {

    public ArrayList<String> filter(ArrayList<String> tokens){

        tokens = Misspellings.filter(tokens);
        tokens = OneWaySynonyms.filter(tokens);
        tokens = TwoWaySynonyms.filter(tokens);

       return tokens;
    }

}
