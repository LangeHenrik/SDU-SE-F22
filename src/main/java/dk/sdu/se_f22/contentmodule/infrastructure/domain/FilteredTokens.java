package dk.sdu.se_f22.contentmodule.infrastructure.domain;

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
    //IStopWords stopwords = new StopWords;
    //IIrregularWords irregularWords = new IrregularWords();
    //IStemmer stemming = new Stemmer; // method name stem
    //interfaceSearch indexSearch = new interfaceSearch; //Search() returns Arraylist of Integers, takes Arraylist of Strings
    //index() void - method for indexing

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

    public static ArrayList<String> tokenToString(ArrayList<Token> tokens) {
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

        //tokens.set(StopWords.filter(tokenStrings));
        //tokens.set(Irregularwords.INSTANCE.searchForIrregularWords(tokenStrings));
        //tokens.set(Stemming.stem(tokenStrings));

        stringToToken(tokenStrings);

        //save log data??
        return stringTokens;
    }

    /*void setTokens(ArrayList<Token> tokenslist) {   //This method should be changed to a method for passing the tokens on to the Index module
        tokens = new ArrayList<>();
        tokens.addAll(tokenslist);

        mockindex.mockIndex(mocktokens); //used for mocking the method for indexing tokens

    }*/ //This method is probably not nessecery



}
