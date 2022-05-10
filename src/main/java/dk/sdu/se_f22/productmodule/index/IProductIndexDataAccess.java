package dk.sdu.se_f22.productmodule.index;

import dk.sdu.se_f22.sharedlibrary.models.Product;

public interface IProductIndexDataAccess {
    public void updateProduct(String id, Product product);
    public void deleteProduct(String id);
    public void createProduct(Product product);
}
