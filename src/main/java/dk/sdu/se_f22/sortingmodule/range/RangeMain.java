package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.ReadRangeFilterInterface;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;
import dk.sdu.se_f22.sortingmodule.range.rangefilter.RangeFilterCreator;
import dk.sdu.se_f22.sortingmodule.range.rangefilter.RangeFilterInterface;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 1. Filter(id, min, max) bliver sendt fra SOM-1
 * 2. Vi bruger InternalFilter til at matche Filter og DBRangeFilter
 *      2.1 InternalFilter(Filter, productAttribute)
 * 3. Internal filter bruges til at validere på
**/

public class RangeMain{
    private RangeFilterInterface rangeFilterCreator;

    public RangeMain() {
        this.rangeFilterCreator = new RangeFilterCreator();
    }

    public static void main(String[] args) {
        Product product = new Product();
        product.set(ProductAttribute.ID, "1cf3d1fd-7787-4b64-8ef9-0b6f131a9f4e");
        product.set(ProductAttribute.AVERAGE_USER_REVIEW, "4.446");
        product.set(ProductAttribute.EAN, "2054647099864");
        product.set(ProductAttribute.PRICE, "1787.50");
        product.set(ProductAttribute.PUBLISHED_DATE, "2021-06-02T05:05:06.622164");
        product.set(ProductAttribute.EXPIRATION_DATE, "2025-01-25T07:40:33.169509");
        product.set(ProductAttribute.CATEGORY, "PC/Laptops");
        product.set(ProductAttribute.NAME, "Lenovo ThinkPad T410 35.8 cm (14.1\")");
        product.set(ProductAttribute.DESCRIPTION, "Lenovo ThinkPad T410, 35.8 cm (14.1\"), 1280 x 800 pixels Lenovo ThinkPad T410. Display diagonal: 35.8 cm (14.1\"), Display resolution: 1280 x 800 pixels");
        product.set(ProductAttribute.WEIGHT, "1");

        ArrayList<String> locations = new ArrayList<>();
        locations.add("Charlottenlund");
        locations.add("Herning");
        product.setLocations(ProductAttribute.IN_STOCK, locations);

        ProductHit productHit = new ProductHit(product);
    }
}
