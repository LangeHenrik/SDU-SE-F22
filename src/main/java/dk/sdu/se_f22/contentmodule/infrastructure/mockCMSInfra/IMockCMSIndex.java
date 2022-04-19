package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Token;

import java.util.ArrayList;

public interface IMockCMSIndex {

    void mockIndex(ArrayList<Token> mocktokens);

    public ArrayList<Integer> mockSearch(ArrayList<Token> searchtokens);
}
