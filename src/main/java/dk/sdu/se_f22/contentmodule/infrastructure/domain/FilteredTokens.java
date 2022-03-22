package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.ArrayList;

public class FilteredTokens implements iToken {

    ArrayList<String> tokens;


    @Override
    public ArrayList<String> getTokens() {
        return tokens;
    }

    @Override
    public void setTokens(ArrayList<String> tokenslist) {
        tokens = new ArrayList<>();
        tokens.addAll(tokenslist);
    }
}
