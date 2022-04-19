package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Token;

import java.util.ArrayList;

class MockStopW {


    ArrayList<Token> mockStopW(ArrayList<Token> mocktokens){
        for(int i = 0; i< mocktokens.size(); i++){
            mocktokens.remove(3);
            mocktokens.remove(7);
            mocktokens.remove(12);
        }
        return mocktokens;
    }

}
