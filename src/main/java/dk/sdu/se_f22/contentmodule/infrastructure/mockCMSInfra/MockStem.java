package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import java.util.ArrayList;
import java.util.List;

public class MockStem {

    List<String> mockStem(ArrayList<String> mocktokens) {
        for (int i = 0; i < mocktokens.size(); i++) {
            mocktokens.remove(7);
            mocktokens.remove(8);
            mocktokens.remove(1);
            mocktokens.add("StemWord1");
            mocktokens.add("StemWord2");
            mocktokens.add("StemWord3");
        }
        return mocktokens;
    }
}
