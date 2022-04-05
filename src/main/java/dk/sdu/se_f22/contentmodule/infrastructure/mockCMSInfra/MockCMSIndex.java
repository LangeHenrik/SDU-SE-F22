package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import java.util.ArrayList;

public class MockCMSIndex implements IMockCMSIndex {


    @Override
    public void mockIndex(ArrayList<String> mocktokens) {
        for(String i: mocktokens){
            System.out.println("Token: " + mocktokens + " filtered tokens indexed");
        }
    }

    @Override
    public int[] mockSearch(ArrayList<String> searchtokens) {
        for(String s: searchtokens){
            System.out.println("Token: " + searchtokens + " is recieved");
        }
        int[] mockId = {1, 3, 5};
        return mockId;
    }
}
