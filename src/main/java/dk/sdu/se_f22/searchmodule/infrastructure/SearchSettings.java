package dk.sdu.se_f22.searchmodule.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchSettings {
    private ArrayList<String> delimiters = new ArrayList<String>(Arrays.asList(" ", "\\p{Punct}"));

    public ArrayList<String> getDelimiters() {
        return delimiters;
    }

    public void appendDelimiters(String delimiter) {
        this.delimiters.add(delimiter);
    }
}