package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.IMockCMSIndex;
import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.MockCMSIndex;
import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.MockFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class  FilteredTokens {
    private static ArrayList<Token> tokens;
    private int htmlId;
    private int idToken;
    private String filteredTokens;
    private Date timeStamp;
    IMockCMSIndex mockindex = new MockCMSIndex(); // used for mocking Index-module
    ArrayList<Token> mocktokens = new ArrayList<>();  //used for mocking Index-module
    MockFacade mockfacade = new MockFacade(); //used for mocking Stop,Irr & Stem-modules

    public FilteredTokens(ArrayList<Token> tokens) {
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

    ArrayList<Token> filterTokens(ArrayList<Token> tokens) {
        //tokens.set(classstopwords.methodprovided(tokens));
        //tokens.set(classirr.methodprovided(tokens));
        //tokens.set(classstem.methodprovided(tokens));

        // save tokens to database - which tables? attributes?
        return tokens;
    }

    /*void setTokens(ArrayList<Token> tokenslist) {   //This method should be changed to a method for passing the tokens on to the Index module
        tokens = new ArrayList<>();
        tokens.addAll(tokenslist);

        mockindex.mockIndex(mocktokens); //used for mocking the method for indexing tokens

    }*/ //This method is probably not nessecery

    ArrayList<Token> mockFilterTokens(ArrayList<Token> mocktokens){  //a method for mocking and testing
        mockfacade.mockUseStopW(mocktokens);
        mockfacade.mockUseIrr(mocktokens);
        mockfacade.mockUseStem(mocktokens);
        return mocktokens;
    }
}
