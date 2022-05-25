package dk.sdu.se_f22.productmodule.index;

import java.util.List;
import dk.sdu.se_f22.sharedlibrary.models.Product;

public interface IProductIndex {

    List<Product> searchProducts(List<Product> products, List<String> token);
    void indexProducts(List<Product> products);
    int findHits(String[] info, String token);

}
