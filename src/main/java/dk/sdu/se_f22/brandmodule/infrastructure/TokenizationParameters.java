package dk.sdu.se_f22.brandmodule.infrastructure;

public class TokenizationParameters {
    public String delimiterRegex;
    public String ignoreRegex;

    public TokenizationParameters(String delimiterRegex, String ignoreRegex) {
        this.delimiterRegex = delimiterRegex;
        this.ignoreRegex = ignoreRegex;
    }
}
