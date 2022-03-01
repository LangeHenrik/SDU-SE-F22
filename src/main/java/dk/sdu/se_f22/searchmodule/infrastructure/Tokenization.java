package dk.sdu.se_f22.searchmodule.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenization {
    SearchSettings searchSettings = new SearchSettings();

    public List<String> tokenize(String s){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<searchSettings.getDelimiters().size();i++){
            if (i!=searchSettings.getDelimiters().size()-1){
                sb.append(searchSettings.getDelimiters().get(i)+"|");
            } else {
                sb.append(searchSettings.getDelimiters().get(i));
            }

        }
        return Arrays.stream(s.split(sb.toString())).toList();
    }

}
