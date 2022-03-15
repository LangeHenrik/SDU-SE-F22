package dk.sdu.se_f22.productmodule.infrastructure.domain;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.List;

public interface ProductInfSearch {
    List<Product> searchProducts(List<String> tokens);
}