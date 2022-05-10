package dk.sdu.se_f22.searchmodule.infrastructure.GUI.mocks;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockBrandIndexingModule implements IndexingModule<Brand> {
    private Map<String, Brand> data = new HashMap<>();

    public MockBrandIndexingModule() {
        var brand = new Brand();
        brand.setId(123);
        brand.setName("Hello1");
        data.put("Hello1", brand);
    }

    @Override
    public List<Brand> queryIndex(List<String> tokens) {
        var results = new ArrayList<Brand>();

        for(var token : tokens) {
            if(data.get(token) != null)
                results.add(data.get(token));
        }

        return results;
    }
}

