package dk.sdu.se_f22.searchmodule.infrastructure.mocks;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockIndexingModule implements IndexingModule<MockIndexingData> {
    public List<MockIndexingData> indexingData = new ArrayList<>();

    @Override
    public List<MockIndexingData> queryIndex(List<String> tokens) {
        return indexingData.stream().filter(data -> tokens.contains(data.name)).collect(Collectors.toList());
    }
}