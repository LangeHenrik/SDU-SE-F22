package dk.sdu.se_f22.som1.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collection of all hits from:<br>
 * - BIM<br>
 * - PIM<br>
 * - CMS<br>
 * <br>
 * Designed as singleton
 */
public final class SearchHits {
    private Collection products;
    private Collection brands;
    private Collection contents;
    private static SearchHits instance;

    private SearchHits() {
        this.products = new ArrayList();
        this.brands = new ArrayList();
        this.contents = new ArrayList();
    }

    /**
     * Get instance of SearchHits class
     * 
     * @return SearchHits
     */
    public static SearchHits getInstance() {
        if (instance == null) {
            instance = new SearchHits();
        }
        return instance;
    }

    /**
     * Get collection, that contains all products
     * 
     * @return Collection
     */
    public Collection getProducts() {
        return this.products;
    }

    /**
     * Get collection, that contains all brands
     * 
     * @return Collection
     */
    public Collection getBrands() {
        return this.brands;
    }

    /**
     * Get collection, that contains all content
     * 
     * @return Collection
     */
    public Collection getContents() {
        return this.contents;
    }

    /**
     * Set new product collection
     * 
     * @param products
     */
    public void setProducts(Collection products) {
        this.products = products;
    }

    /**
     * Set new brands collection
     * 
     * @param brands
     */
    public void setBrands(Collection brands) {
        this.brands = brands;
    }

    /**
     * Set new content collection
     * 
     * @param brands
     */
    public void setContents(Collection contents) {
        this.contents = contents;
    }
}
