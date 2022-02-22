package dk.sdu.se_f22.pim2.domain;

import dk.sdu.se_f22.pim2.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductInfSearch {


    List<Product> searchProducts(List<String> tokens);


}