package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.ArrayList;

public class TokenAccess {
    private iToken unfilteredTokens;
    private iToken filteredTokens;
    private iToken searchTokens;

    public TokenAccess(){
        unfilteredTokens = new UnfilteredTokens();
        filteredTokens = new FilteredTokens();
        searchTokens = new SearchTokens();
    }

    public ArrayList<String>  getUnfilteredTokens(){
        return unfilteredTokens.getTokens();
    }

    public void setUnfilteredTokens(ArrayList<String> tokens){
        unfilteredTokens.setTokens(tokens);
    }

    public ArrayList<String>  getFilteredTokens(){
        return filteredTokens.getTokens();
    }

    public void setFilteredTokens(ArrayList<String> tokens){
        filteredTokens.setTokens(tokens);
    }

    public ArrayList<String> getSearchTokens(){ return searchTokens.getTokens(); }

    public void setSearchTokens(ArrayList<String> tokens){
        searchTokens.setTokens(tokens);
    }
}
