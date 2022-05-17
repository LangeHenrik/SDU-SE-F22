package dk.sdu.se_f22.productmodule.infrastructure.domain;

import dk.sdu.se_f22.productmodule.management.domain_persistance.BaseProduct;

import java.util.List;

public interface ProductInfSearch {


    List<BaseProduct> searchProducts(List<String> tokens);


}