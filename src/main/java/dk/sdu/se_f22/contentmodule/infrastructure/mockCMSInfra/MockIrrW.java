package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Token;

import java.util.ArrayList;

class MockIrrW {

    ArrayList<Token> mockIrrW(ArrayList<Token> mocktokens) {
        for (int i = 0; i < mocktokens.size(); i++) {
            mocktokens.remove(1);
            mocktokens.remove(2);
            mocktokens.remove(4);
            mocktokens.add(1,new Token("IrrWord2", 1));
            mocktokens.add(2,new Token("IrrWord2",2));
            mocktokens.add(3, new Token("IrrWord3", 3));
        }
        return mocktokens;
    }
}
