package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.ArrayList;
import java.util.Date;

class  FilteredTokens {
    private static ArrayList<String> tokens;
    private int htmlId;
    private int idToken;
    private String filteredTokens;
    private Date timeStamp;

    public FilteredTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }

    public FilteredTokens(int idToken, String filteredTokens){
        this.idToken = idToken;
        this.filteredTokens = filteredTokens;
    }

    public int getIdToken() {
        return idToken;
    }

    public String getFilteredTokens() {
        return filteredTokens;
    }

    static void filterTokens(ArrayList<String> tokens) {
        //tokens.set(classstopwords.methodprovided(tokens));
        //tokens.set(classstopwords.methodprovided(tokens));
        //tokens.set(classstopwords.methodprovided(tokens));

        // save tokens to database - which tables? attributes?
    }

    void setTokens(ArrayList<String> tokenslist) {
        tokens = new ArrayList<>();
        tokens.addAll(tokenslist);
    }
}
