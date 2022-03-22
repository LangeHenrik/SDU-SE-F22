package dk.sdu.se_f22.searchmodule.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenization {
    private SearchModuleUtils searchModuleUtils = new SearchModuleUtils();
    private DelimiterSettings delimiterSettings = new DelimiterSettings();
    private List<String> stringList = delimiterSettings.getDelimiters();

    public List<String> tokenize(String s) {
        String[] string = s.split(searchModuleUtils.convertDelimitersToRegex(stringList));
        List<String> returnList = new ArrayList<>();
        for (String s1 : Arrays.stream(string).toList()) {
            if (!s1.equals("")) {
                returnList.add(s1);
            }
        }
        return returnList;
    }
}
