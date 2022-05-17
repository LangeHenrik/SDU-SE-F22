package dk.sdu.se_f22.brandmodule.stemming;

import java.util.ArrayList;
import java.util.List;

public class Stemmer implements IStemmer {
    public ArrayList<String> stem(List<String> words) {
        ArrayList<String> returnList = new ArrayList<>();
        for (String word : words) {
            returnList.add(stem(word));
        }
        return returnList;
    }

    public String stem(String word) {
        if (word.length() < 3) return word;
        Word stemmingWord = new Word(word);
        stemmingWord = StemmingUtilities.step1a(stemmingWord);
        stemmingWord = StemmingUtilities.step1b(stemmingWord);
        stemmingWord = StemmingUtilities.step1c(stemmingWord);
        stemmingWord = StemmingUtilities.step2(stemmingWord);
        stemmingWord = StemmingUtilities.step3(stemmingWord);
        stemmingWord = StemmingUtilities.step4(stemmingWord);
        stemmingWord = StemmingUtilities.step5a(stemmingWord);
        stemmingWord = StemmingUtilities.step5b(stemmingWord);
        return stemmingWord.getWordString();
    }
}
