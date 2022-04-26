package dk.sdu.se_f22.productmodule.management;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;

public interface IProductManager {
    
    boolean create(Product p);
    boolean createAll(ArrayList<Product> p);
    Product readProduct(String productId);
    Product[] readProducts(String[] productIds);
    boolean update(String productId, ProductAttribute a, String s);
    boolean update(String productId, Product p);
    boolean remove(String productId);
    void setUpdateInterval(int time);
    void reparse();
    
    ArrayList<Product> readAllProducts();
    
}