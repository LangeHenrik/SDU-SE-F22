package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.IMockCMSIndex;
import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.IMockCMSSearch;
import dk.sdu.se_f22.contentmodule.infrastructure.mockCMSInfra.MockCMSIndex;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchClass implements IndexingModule, IMockCMSSearch{
    @Override
    public List queryIndex(List tokens) {
        ArrayList<Integer> matchingIDs;

        matchingIDs = MockCMSIndex.mockSearch((ArrayList<String>) tokens);


        return matchingIDs;
    }

}
