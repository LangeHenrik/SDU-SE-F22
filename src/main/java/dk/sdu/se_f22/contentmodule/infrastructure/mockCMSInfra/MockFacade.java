package dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Token;

import java.util.ArrayList;

public class MockFacade {
    private MockStem stem;
    private MockIrrW irrW;
    private MockStopW stopW;

    public MockFacade(){
        stem = new MockStem();
        irrW = new MockIrrW();
        stopW = new MockStopW();
    }

    public void mockUseStem(ArrayList<String> mocktokens){
        stem.mockStem(new ArrayList<>());
    }

    public void mockUseStopW(ArrayList<String> mocktokens){
        stopW.mockStopW(new ArrayList<>());
    }

    public void mockUseIrr(ArrayList<String> mocktokens){
        irrW.mockIrrW(new ArrayList<>());
    }
}
