package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import java.util.ArrayList;
import java.util.List;

class MockStopW {


    List<String> mockStopW(ArrayList<String> mocktokens){
        for(int i = 0; i< mocktokens.size(); i++){
            mocktokens.remove(3);
            mocktokens.remove(7);
            mocktokens.remove(12);
        }
        return mocktokens;
    }

}
