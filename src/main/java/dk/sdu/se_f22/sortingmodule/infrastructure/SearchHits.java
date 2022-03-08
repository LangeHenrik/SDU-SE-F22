package dk.sdu.se_f22.sortingmodule.infrastructure;

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

    public SearchHits() {
        this.products = new ArrayList();
        this.brands = new ArrayList();
        this.contents = new ArrayList();
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
     * @throws NullPointerException
     */
    public void setProducts(Collection products) throws NullPointerException {
        if (products == null) {
            throw new NullPointerException();
        }
        this.products = products;
    }

    /**
     * Set new brands collection
     * 
     * @param brands
     * @throws NullPointerException
     */
    public void setBrands(Collection brands) throws NullPointerException {
        if (brands == null) {
            throw new NullPointerException();
        }
        this.brands = brands;
    }

    /**
     * Set new content collection
     * 
     * @param contents
     * @throws NullPointerException
     */
    public void setContents(Collection contents) throws NullPointerException {
        if (contents == null) {
            throw new NullPointerException();
        }
        this.contents = contents;
    }
}
