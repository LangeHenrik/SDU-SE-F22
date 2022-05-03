package dk.sdu.se_f22.contentmodule.infrastructure.domain;


import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchClass implements IndexingModule{
    @Override
    public List queryIndex(List tokens) {
        ArrayList<Integer> matchingIDs;
        return null;
    }
}
