package dk.sdu.se_f22.searchmodule.infrastructure.GUI.mocks;

import dk.sdu.se_f22.productmodule.management.BaseProduct;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockProductIndexingModule implements IndexingModule<BaseProduct> {
    private Map<String, BaseProduct> data = new HashMap<>();

    public MockProductIndexingModule() {
        data.put("Test1", new BaseProduct());
    }

    @Override
    public List<BaseProduct> queryIndex(List<String> tokens) {
        var results = new ArrayList<BaseProduct>();

        for(var token : tokens) {
            System.out.println(token);
            if(data.get(token) != null)
                results.add(data.get(token));
        }

        return results;
    }
}
