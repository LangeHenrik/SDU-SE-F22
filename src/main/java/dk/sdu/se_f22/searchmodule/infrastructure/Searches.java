package dk.sdu.se_f22.searchmodule.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class Searches {
    private int id;
    private String searchString;
    private String timeSearched;

    private int brandsCounter;
    private int productCounter;
    private int contentCounter;

    private List<String> brands;
    private List<String> products;
    private List<String> contents;

    public Searches(int id, String searchString, String timeSearched, List<String> brands, List<String> products, List<String> contents) {
        this.id = id;
        this.searchString = searchString;
        this.timeSearched = timeSearched;

        this.brands = brands;
        this.products = products;
        this.contents = contents;

        this.brandsCounter = brands.size();
        this.productCounter = products.size();
        this.contentCounter = contents.size();
    }

    @Override
    public String toString() {
        return "Search string: " + searchString
                + "Time searched: " + timeSearched
                + "Number of brands: " + brandsCounter
                + "Number of products: " + productCounter
                + "Number of contents: " + contentCounter
                + "BrandIDs: " + brands.toString()
                + "ProductIDs: " + products.toString()
                + "ContentIDs: " + contents.toString();

    }
}
