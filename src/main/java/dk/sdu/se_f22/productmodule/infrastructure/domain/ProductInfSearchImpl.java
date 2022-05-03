package dk.sdu.se_f22.productmodule.infrastructure.domain;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.sharedlibrary.models.BaseProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductInfSearchImpl implements ProductInfSearch, IndexingModule<BaseProduct> {

    @Override
    public List<BaseProduct> searchProducts(List<String> tokens) {
        // return PIM3.searchProducts(tokens);
        return new ArrayList<>();
    }

    @Override
    public List<BaseProduct> queryIndex(List<String> tokens) {
        return this.searchProducts(tokens);
    }
}
