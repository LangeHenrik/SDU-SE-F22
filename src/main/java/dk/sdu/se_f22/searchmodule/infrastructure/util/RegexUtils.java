package dk.sdu.se_f22.searchmodule.infrastructure.util;

import java.util.List;

public class RegexUtils {

    public static String convertStringListToRegexString(List<String> stringList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringList.size(); i++) {
            addDelimiterToStringBuilder(sb, i, stringList);
        }
        return sb.toString();
    }

    private static void addDelimiterToStringBuilder(StringBuilder sb, int i, List<String> stringList) {
        if(stringList.get(i).isEmpty()) {
            return;
        }

        if (i != stringList.size() - 1) {
            if (!stringList.get(i).matches("[a-zA-Z]+")) {
                sb.append("\\");
            }
            sb.append(stringList.get(i));
            sb.append("|");
        } else {
            sb.append("\\").append(stringList.get(i));
        }
    }
}