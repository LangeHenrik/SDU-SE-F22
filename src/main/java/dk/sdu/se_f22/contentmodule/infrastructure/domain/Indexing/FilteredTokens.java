package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class  FilteredTokens {



    //IStopWords stopwords = new StopWords;
    //IIrregularWords irregularWords = new IrregularWords();
    //IStemmer stemming = new Stemmer; // method name stem
    //InterfaceOutgoing indexNSearch = new InterfaceOutgoing;
    //Search() returns Arraylist of Integers, takes Arraylist of Strings
    //index() void - method for indexing



     HTMLSite siteWithFilteredTokens(HTMLSite site) {
        List<String> tokens = new ArrayList<>(site.getTokens());


        //tokens.set(StopWords.filter(tokens));
        //tokens.set(Irregularwords.INSTANCE.searchForIrregularWords(tokenStrings));
        //tokens.set(Stemmer.stem(tokenStrings));


        //save log data??

        List<String> filteredTokens = new ArrayList<>();

        return new HTMLSite(site.getId(), site.getHtmlCode(), filteredTokens);
    }

    /* //method for passing the tokens on to the Index module
    void index(ArrayList<Token> stringTokens) {
        indexNsearch.index(stringTokens);
    }*/
}
