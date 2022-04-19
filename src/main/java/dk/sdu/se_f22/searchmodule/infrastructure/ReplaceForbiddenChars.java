package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReplaceForbiddenChars {

    private IllegalChars illegalCharsClass = new IllegalChars();
    private List<String> illegalChars = illegalCharsClass.illegalCharsFromDB();

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
