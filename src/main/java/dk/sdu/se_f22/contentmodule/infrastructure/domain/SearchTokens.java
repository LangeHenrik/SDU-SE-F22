package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.ArrayList;

public class SearchTokens implements iToken{
    ArrayList<String> tokens;

    @Override
    public ArrayList<String> getTokens() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTokens() {
        throw new UnsupportedOperationException();
    }
}
