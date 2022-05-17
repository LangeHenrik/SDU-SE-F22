package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class  FilteredTokens {
    //IStopWords stopwords = new StopWords;
    //IIrregularWords irregularWords = new IrregularWords();
    //IStemmer stemming = new Stemmer; // method name stem


     HTMLSite siteWithFilteredTokens(HTMLSite newSite) throws IllegalArgumentException {
        List<String> tokens = new ArrayList<>(newSite.getTokens());

        //tokens.set(StopWords.filter(tokens));
        //tokens.set(Irregularwords.INSTANCE.searchForIrregularWords(tokens));
        //tokens.set(Stemmer.stem(tokens));

         if(!tokens.isEmpty()){
             newSite.setFilteredTokens(new ArrayList<String>(tokens));
             newSite.setTokens(null); //empty the array

             //The index module is asking for a String[] and not an arrayList
             String[] tokenArray = tokens.toArray(new String[tokens.size()]);
             newSite.setFilteredTokensArray(tokenArray);
         } else throw new IllegalArgumentException();

        return newSite;
    }
}
