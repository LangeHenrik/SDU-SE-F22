package dk.sdu.se_f22.productmodule.index;

import dk.sdu.se_f22.sharedlibrary.models.ProductHit;

public interface IProductIndexDataAccess {
    public void updateProduct(String id, ProductHit product);
    public void deleteProduct(String id);
    public void createProduct(ProductHit product);
}
