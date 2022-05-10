package dk.sdu.se_f22.productmodule.index;

import java.util.List;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;

public interface IProductIndex {

    public List<ProductHit> indexProductsByToken(List<ProductHit> products, List<String> token);

}
