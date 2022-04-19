package dk.sdu.se_f22.searchmodule.infrastructure.GUI.mocks;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.util.List;

public class MockBrandIndexingModule implements IndexingModule<Brand> {
    @Override
    public List<Brand> queryIndex(List<String> tokens) {
        var brand = new Brand();
        brand.setName("Hello");

        return List.of(brand);
    }
}
