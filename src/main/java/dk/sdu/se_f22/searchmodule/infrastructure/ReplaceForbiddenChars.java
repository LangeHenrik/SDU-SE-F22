package dk.sdu.se_f22.searchmodule.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class ReplaceForbiddenChars {

    private List<String> illegalChars = new ArrayList<String>(List.of(new String[]{"@", "´", "|", "*"}));
    ;

    public String removeForbiddenChars(String toSort) {
        toSort = toSort.replaceAll(SearchModuleUtils.convertDelimitersToRegex(this.illegalChars), "");
        return toSort;
    }

    public List<String> getIllegalChars() {
        return illegalChars;
    }

    public void setIllegalChars(List<String> illegalChars) {
        this.illegalChars = illegalChars;
    }

    public void addIllegalChars(String illegalChar) {
        this.illegalChars.add(illegalChar);
    }


}
