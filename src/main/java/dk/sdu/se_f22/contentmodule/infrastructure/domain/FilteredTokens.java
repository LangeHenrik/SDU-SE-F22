package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.IMockCMSIndex;
import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.MockCMSIndex;
import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.MockFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

class  FilteredTokens {
    private static ArrayList<Token> tokens;
    private int htmlId;
    private int idToken;
    private static int tempId; //To hold temp token ID
    private String filteredTokens;
    private Date timeStamp;
    private static ArrayList<String> tokenStrings;
    private static ArrayList<Token> stringTokens;
    //IStemmer stemming = new Stemmer; // method name stem
    IMockCMSIndex mockindex = new MockCMSIndex(); // used for mocking Index-module
    ArrayList<Token> mocktokens = new ArrayList<>();  //used for mocking Index-module
    MockFacade mockfacade = new MockFacade(); //used for mocking Stop,Irr & Stem-modules
    //IIrregularWords irregularWords = new IrregularWords();

    public FilteredTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }



    public FilteredTokens(int idToken, String filteredTokens){
        this.idToken = idToken;
        this.filteredTokens = filteredTokens;
    }

    int getIdToken() {
        return idToken;
    }

    String getFilteredTokens() {
        return filteredTokens;
    }

    static ArrayList<String> tokenToString(ArrayList<Token> tokens) {
        tokenStrings = new ArrayList<>();
        for(Token t : tokens) {
            tokenStrings.add(t.getDocumentText());
            //if(tempId == -1) {
            //    tempId = t.getOriginID();
            //} else continue;
            tempId = t.getOriginID();
        }
        return tokenStrings;
    }

    public static ArrayList<Token> stringToToken(ArrayList<String> tokenStrings) {
        stringTokens = new ArrayList<Token>();
        for(String s : tokenStrings) {
            stringTokens.add(new Token(s, tempId));
        }
        return stringTokens;
    }

    ArrayList<Token> filterTokens(ArrayList<Token> tokens) {
        tokenToString(tokens);

        //tokens.set(classstopwords.methodprovided(tokenStrings));
        //tokens.set(Irregularwords.INSTANCE.searchForIrregularWords(tokenStrings));
        //tokens.set(Stemming.stem(tokenStrings));

        //For unit-testing
        mockfacade.mockUseStopW(tokenStrings);
        mockfacade.mockUseIrr(tokenStrings);
        mockfacade.mockUseStem(tokenStrings);

        stringToToken(tokenStrings);

        //save log data??
        return stringTokens;
    }

    /*void setTokens(ArrayList<Token> tokenslist) {   //This method should be changed to a method for passing the tokens on to the Index module
        tokens = new ArrayList<>();
        tokens.addAll(tokenslist);

        mockindex.mockIndex(mocktokens); //used for mocking the method for indexing tokens

    }*/ //This method is probably not nessecery

    /* Just a method for testing
    ArrayList<Token> mockFilterTokens(ArrayList<Token> mocktokens){  //a method for mocking and testing
        mockfacade.mockUseStopW(mocktokens);
        mockfacade.mockUseIrr(mocktokens);
        mockfacade.mockUseStem(mocktokens);
        return mocktokens;
    }*/
}
