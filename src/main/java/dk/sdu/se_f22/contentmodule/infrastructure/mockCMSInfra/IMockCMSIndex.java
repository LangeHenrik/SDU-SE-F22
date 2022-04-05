package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import java.util.ArrayList;

public interface IMockCMSIndex {

    void mockIndex(ArrayList<String> mocktokens);

    public ArrayList<Integer> mockSearch(ArrayList<String> searchtokens);
}
