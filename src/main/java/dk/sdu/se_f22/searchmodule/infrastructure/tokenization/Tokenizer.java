package dk.sdu.se_f22.searchmodule.infrastructure.tokenization;

import dk.sdu.se_f22.searchmodule.infrastructure.util.RegexUtils;
import dk.sdu.se_f22.sharedlibrary.db.LoggingProvider;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final DelimiterSettings delimiterSettings = new DelimiterSettings();
    private final List<String> stringList = delimiterSettings.getDelimiters();

    public List<String> tokenize(String s) {
        if (listIsEmpty()){
            LoggingProvider.getLogger(Tokenizer.class).error("There is no delimiters set!");
            return List.of(s);
        } else {
            List<String> returnList = splitString(s);
            removeAllEmptyStrings(returnList);
            return returnList;
        }
    }

    private ArrayList<String> splitString(String s) {
        return new ArrayList<>(List.of(splitStringUsingDelimiters(s)));
    }

    private void removeAllEmptyStrings(List<String> returnList) {
        while (returnList.contains("")){
            returnList.remove("");
        }
    }

    private String[] splitStringUsingDelimiters(String s) {
        return s.split(RegexUtils.convertStringListToRegexString(stringList));
    }

    private boolean listIsEmpty() {
        return stringList.size() == 0;
    }
}
