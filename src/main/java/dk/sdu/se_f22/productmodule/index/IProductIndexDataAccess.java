package dk.sdu.se_f22.productmodule.index;

import dk.sdu.se_f22.sharedlibrary.models.Product;

public interface IProductIndexDataAccess {
    public void updateProduct(int id, Product product);
    public void deleteProduct(int id);
    public void createProduct(Product product);
}
