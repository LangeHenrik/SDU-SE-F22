package dk.sdu.se_f22.searchmodule.infrastructure;

import java.util.List;

public class SearchModuleUtils {

    public static String convertDelimitersToRegex(List<String> stringList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <stringList.size(); i++){
            addDelimiterToStringBuilder(sb, i,stringList);
        }
        return sb.toString();
    }

    private static void addDelimiterToStringBuilder(StringBuilder sb, int i,List<String> stringList) {
        if (i != stringList.size() - 1) {
            sb.append(stringList.get(i) + "|");
        } else {
            sb.append(stringList.get(i));
        }
    }
}