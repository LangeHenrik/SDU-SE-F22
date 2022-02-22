package dk.sdu.se_f22.pim2.Data;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class TokenParameter {
    private String delimiter;
    private List<String> ignoredChars;

    public TokenParameter(String delimiter, List<String> ignoredChars){
        this.delimiter = delimiter;
        this.ignoredChars = ignoredChars;
    }

    public void save() {};

    public TokenParameter load() {
        return new TokenParameter(" ", Arrays.asList("('./?!')".split("")));
    }

    public void setIgnoredChars(List<String> ignoredChars) {
        this.ignoredChars = ignoredChars;
    }

    public List<String> getIgnoredChars() {
        return ignoredChars;
    }
}
