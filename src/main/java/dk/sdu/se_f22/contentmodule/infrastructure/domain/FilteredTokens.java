package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.IMockCMSIndex;
import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.MockCMSIndex;
import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.MockFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class  FilteredTokens {
    //Skal være Arraylist af tokens
    private static ArrayList<String> tokens;
    private int htmlId;
    private int idToken;
    private String filteredTokens;
    private Date timeStamp;
    IMockCMSIndex mockindex = new MockCMSIndex(); // used for mocking Index-module
    ArrayList<String> mocktokens = new ArrayList<>();  //used for mocking Index-module
    MockFacade mockfacade = new MockFacade(); //used for mocking Stop,Irr & Stem-modules

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

    void filterTokens(ArrayList<String> tokens) {
        //tokens.set(classstopwords.methodprovided(tokens));
        //tokens.set(classirr.methodprovided(tokens));
        //tokens.set(classstem.methodprovided(tokens));

        // save tokens to database - which tables? attributes?
    }

    void setTokens(ArrayList<String> tokenslist) {   //This method should be changed to a method for passing the tokens on to the Index module
        tokens = new ArrayList<>();
        tokens.addAll(tokenslist);

        mockindex.mockIndex(mocktokens); //used for mocking the method for indexing tokens

    }

    List<String> mockFilterTokens(ArrayList<String> mocktokens){  //a method for mocking and testing
        mockfacade.mockUseStopW(mocktokens);
        mockfacade.mockUseIrr(mocktokens);
        mockfacade.mockUseStem(mocktokens);
        return new ArrayList<>(mocktokens);
    }
}
