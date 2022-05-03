package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Token;

import java.util.ArrayList;

public class MockStem {

    ArrayList<String> mockStem(ArrayList<String> mocktokens) {
        for (int i = 0; i < mocktokens.size(); i++) {
            mocktokens.remove(2);
            mocktokens.remove(4);
            mocktokens.remove(1);
            mocktokens.add("StemWord2");
            mocktokens.add("StemWord3");
        }
        return mocktokens;
    }
}
