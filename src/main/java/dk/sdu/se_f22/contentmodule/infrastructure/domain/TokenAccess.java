package dk.sdu.se_f22.contentmodule.infrastructure.domain;

public class TokenAccess {
    private iToken unfilteredTokens;
    private iToken filteredTokens;
    private iToken searchTokens;

    public TokenAccess(){
        unfilteredTokens = new UnfilteredTokens();
        filteredTokens = new FilteredTokens();
        searchTokens = new SearchTokens();
    }

    public void getUnfilteredTokens(){
        unfilteredTokens.getTokens();
    }

    public void setUnfilteredTokens(){
        unfilteredTokens.setTokens();
    }

    public void getFilteredTokens(){
        filteredTokens.getTokens();
    }

    public void setFilteredTokens(){
        filteredTokens.setTokens();
    }

    public void getSearchTokens(){
        searchTokens.getTokens();
    }

    public void setSearchTokens(){
        searchTokens.setTokens();
    }
}
