package dk.sdu.se_f22.productmodule.index;


import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ProductIndex p = ProductIndex.getInstance();
        List<String> mylist = new ArrayList<>();
        mylist.add("Herning");
        mylist.add("Køge");
        Product product = new Product(UUID.fromString("5cf4d1fd-2787-4b64-8ef9-0b6f131a3f24"),
                4.446,mylist, 20544709,
                1787.50,Instant.parse("2021-06-02T05:05:06.622164Z"), Instant.parse("2025-01-25T07:40:33.169509Z"),
                "Memory/SSD","Lenovo ThinkPad T410 35.8 cm (14.1\")",
                "Lenovo ThinkPad T410, 35.8 cm (14.1\")," +
                        " 1280 x 800 pixels Lenovo ThinkPad T410. " +
                        "Display diagonal: 35.8 cm (14.1\"), Display resolution: 1280 x 800 pixels","12",52,
                1);
        p.createProduct(product);
        List<Product> products = p.getProducts();
        for (Product product1 :
                products) {
            System.out.println(product1);
        }

    }
}