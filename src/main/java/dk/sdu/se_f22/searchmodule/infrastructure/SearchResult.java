package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.util.ArrayList;

public class SearchResult {
    //private ArrayList<Content> contentPages;
    //private ArrayList<Product> productPages;
    private ArrayList<Brand> brandPages;

    public SearchResult(/*ArrayList<Content> contentPages, ArrayList<Product> productPages,*/ ArrayList<Brand> brandPages) {
        /*this.contentPages = contentPages;
        this.productPages = productPages;*/
        this.brandPages = brandPages;
    }

    /*
    public ArrayList<Content> getContentPages(){
        return contentPages;
    }

    public ArrayList<Product> getProductPages() {
        return productPages;
    }*/

    public ArrayList<Brand> getBrandPages(){
        return brandPages;
    }
}
