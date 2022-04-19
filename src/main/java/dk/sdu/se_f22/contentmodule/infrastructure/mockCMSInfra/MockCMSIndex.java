package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.HTMLSite;
import dk.sdu.se_f22.contentmodule.infrastructure.domain.Token;

import java.util.ArrayList;

public class MockCMSIndex implements IMockCMSIndex {


    @Override
    public void mockIndex(ArrayList<Token> mocktokens) {
        for(Token i: mocktokens){
            System.out.println("Token: " + mocktokens + " filtered tokens indexed");
        }
    }

    @Override
    public ArrayList<Integer> mockSearch(ArrayList<Token> searchtokens) {
        return null;
    }

}
