package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import java.util.ArrayList;
import java.util.List;

class MockIrrW {

    List<String> mockIrrW(ArrayList<String> mocktokens) {
        for (int i = 0; i < mocktokens.size(); i++) {
            mocktokens.remove(1);
            mocktokens.remove(2);
            mocktokens.remove(4);
            mocktokens.add("IrrWord1");
            mocktokens.add("IrrWord2");
            mocktokens.add("IrrWord3");
        }
        return mocktokens;
    }
}
