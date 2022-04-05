package dk.sdu.se_f22.productmodule.infrastructure.domain;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductInfSearchImpl implements ProductInfSearch, IndexingModule<Product> {

    @Override
    public List<Product> searchProducts(List<String> tokens) {
        // return PIM3.searchProducts(tokens);
        return new ArrayList<>();
    }

    @Override
    public List<Product> queryIndex(List<String> tokens) {
        return this.searchProducts(tokens);
    }
}
