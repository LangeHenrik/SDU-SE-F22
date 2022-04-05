package dk.sdu.se_f22.searchmodule.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    private DelimiterSettings delimiterSettings = new DelimiterSettings();
    private List<String> stringList = delimiterSettings.getDelimiters();

    public List<String> tokenize(String s) {
        if (listIsEmpty()){
            System.out.println("ERR: There is no delimiters set!");
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
        return s.split(SearchModuleUtils.convertDelimitersToRegex(stringList));
    }

    private boolean listIsEmpty() {
        return stringList.size() == 0;
    }
}
