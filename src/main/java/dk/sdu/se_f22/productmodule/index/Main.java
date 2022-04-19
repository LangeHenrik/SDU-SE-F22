package dk.sdu.se_f22.productmodule.index;


import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductIndex p = ProductIndex.getInstance();
        // create list of strings
        List<String> str = new ArrayList<>();
        str.add("HP");

        // call searchproduct to find corresponding products
        List<Product> products = p.searchProducts(str);


    }
}