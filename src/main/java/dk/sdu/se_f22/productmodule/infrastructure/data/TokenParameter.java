package dk.sdu.se_f22.productmodule.infrastructure.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenParameter {
    private String delimiter;
    private List<String> ignoredChars;

    public TokenParameter(String delimiter, List<String> ignoredChars){
        this(delimiter);
        this.setIgnoredChars(ignoredChars);
    }

    public TokenParameter(String delimiter, String ignoredChars) {
        this(delimiter);
        this.setIgnoredChars(ignoredChars);
    }

    private TokenParameter(String delimiter) {
        this.delimiter = delimiter;
    }


    public List<String> getIgnoredChars() {
        return new ArrayList<>(ignoredChars);
    }

    public void setIgnoredChars(List<String> ignoredChars) {
        this.ignoredChars = ignoredChars;
    }

    public void setIgnoredChars(String ignoredChars) {
        this.ignoredChars = Arrays.asList(ignoredChars.split(""));
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void save() {
        TokenParameterStore.saveTokenParameter(this);
    }

    public static TokenParameter load() {
        return TokenParameterStore.loadTokenParameter();
    }

}
