package dk.sdu.se_f22.searchmodule.infrastructure.GUI.mocks;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockProductIndexingModule implements IndexingModule<Product> {
    private Map<String, Product> data = new HashMap<>();

    public MockProductIndexingModule() {
        data.put("Test1", new Product());
    }

    @Override
    public List<Product> queryIndex(List<String> tokens) {
        var results = new ArrayList<Product>();

        for(var token : tokens) {
            System.out.println(token);
            if(data.get(token) != null)
                results.add(data.get(token));
        }

        return results;
    }
}
