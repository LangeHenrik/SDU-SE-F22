package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Token;

import java.util.ArrayList;

public class MockStem {

    ArrayList<Token> mockStem(ArrayList<Token> mocktokens) {
        for (int i = 0; i < mocktokens.size(); i++) {
            mocktokens.remove(7);
            mocktokens.remove(8);
            mocktokens.remove(1);
            mocktokens.add(1,new Token("StemWord2", 1));
            mocktokens.add(2, new Token("StemWord2", 2));
            mocktokens.add(3, new Token("StemWord3", 3));
        }
        return mocktokens;
    }
}
