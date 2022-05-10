package dk.sdu.se_f22.productmodule.index;

import java.util.List;
import dk.sdu.se_f22.sharedlibrary.models.Product;

public interface IProductIndex {

    public List<Product> indexProductsByToken(List<Product> products, List<String> token);

}
