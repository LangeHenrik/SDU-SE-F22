package dk.sdu.se_f22.searchmodule.infrastructure.logger;

import java.util.List;

public class SearchLog {
    private final String searchString;

    private final String timeSearched;

    private final int brandsCounter;
    private final int productCounter;
    private final int contentCounter;

    private final List<String> brands;
    private final List<String> products;
    private final List<String> contents;

    public SearchLog(String searchString, String timeSearched, List<String> brands, List<String> products, List<String> contents) {
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

    public String getSearchString() {
        return searchString;
    }

    public String getTimeSearched() {
        return timeSearched;
    }

    public int getBrandsCounter() {
        return brandsCounter;
    }

    public int getProductCounter() {
        return productCounter;
    }
}
