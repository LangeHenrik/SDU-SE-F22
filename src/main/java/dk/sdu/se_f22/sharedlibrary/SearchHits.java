package dk.sdu.se_f22.sharedlibrary;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;

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
    private Collection<Product> products;
    private Collection<Brand> brands;
    private Collection<Content> contents;

    public SearchHits() {
        this.products = new ArrayList<Product>();
        this.brands = new ArrayList<Brand>();
        this.contents = new ArrayList<Content>();
    }

    /**
     * Get collection, that contains all products
     * 
     * @return Collection
     */
    public Collection<Product> getProducts() {
        return this.products;
    }

    /**
     * Get collection, that contains all brands
     * 
     * @return Collection
     */
    public Collection<Brand> getBrands() {
        return this.brands;
    }

    /**
     * Get collection, that contains all content
     * 
     * @return Collection
     */
    public Collection<Content> getContents() {
        return this.contents;
    }

    /**
     * Set new product collection
     * 
     * @param products
     * @throws NullPointerException
     */
    public void setProducts(Collection<Product> products) throws NullPointerException {
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
    public void setBrands(Collection<Brand> brands) throws NullPointerException {
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
    public void setContents(Collection<Content> contents) throws NullPointerException {
        if (contents == null) {
            throw new NullPointerException();
        }
        this.contents = contents;
    }

    /**
     * Add a product to product hits 
     * 
     * @param product
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * Add a brand to brand hits
     * 
     * @param brand
     */
    public void addBrand(Brand brand) {
        this.brands.add(brand);
    }

    /**
     * Add a content to content hits
     * 
     * @param content
     */
    public void addContent(Content content) {
        this.contents.add(content);
    }
}
