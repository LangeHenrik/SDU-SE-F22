package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    //private ArrayList<Content> contentPages;
    private List<Product> productPages;
    private List<Brand> brandPages;

    public SearchResult(/*ArrayList<Content> contentPages,*/ List<Product> productPages, List<Brand>brandPages) {
        /*this.contentPages = contentPages;*/
        this.productPages = productPages;
        this.brandPages = brandPages;
    }

    /*
    public ArrayList<Content> getContentPages(){
        return contentPages;
    }*/

    public List<Product> getProductPages() {
        return productPages;
    }

    public List<Brand> getBrandPages(){
        return brandPages;
    }
}
