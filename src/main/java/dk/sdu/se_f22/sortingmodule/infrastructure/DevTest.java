package dk.sdu.se_f22.sortingmodule.infrastructure;

import java.util.Collection;

public class DevTest {
    public static void main(String[] args) {
        SearchHits sh = SearchHits.getInstance();

        System.out.println(sh.getBrands());
        System.out.println(sh.getProducts());
        System.out.println(sh.getContents());

        Collection brands = sh.getBrands();

        brands.add("Demo");
        brands.add("brand 2");
        brands.add("brand 3");

        Collection products = sh.getProducts();

        products.add("Product 1");
        products.add("Product 2");
        products.add("Product 3");

        Collection contents = sh.getContents();

        contents.add("page 1");
        contents.add("page 2");
        contents.add("page 3");

        System.out.println(sh.getBrands());
        System.out.println(sh.getProducts());
        System.out.println(sh.getContents());
    }
}
