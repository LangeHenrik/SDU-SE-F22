package dk.sdu.se_f22.productmodule.management.domain_persistance;

import java.util.ArrayList;

public interface IProductManager {
    
    boolean create(BaseProduct p);
    boolean createAll(ArrayList<BaseProduct> p);
    BaseProduct readProduct(String productId);
    BaseProduct[] readProducts(String[] productIds);
    boolean update(String productId, ProductAttribute a, String s);
    boolean update(String productId, BaseProduct p);
    boolean remove(String productId);
    void setUpdateInterval(int time);
    void reparse();
    
    ArrayList<BaseProduct> readAllProducts();
    
}