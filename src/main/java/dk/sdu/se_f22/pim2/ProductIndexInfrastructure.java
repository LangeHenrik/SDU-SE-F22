package dk.sdu.se_f22.pim2;

import dk.sdu.se_f22.pim2.domain.ProductInfIndex;
import dk.sdu.se_f22.pim2.domain.ProductInfIndexImpl;
import dk.sdu.se_f22.pim2.domain.ProductInfSearch;
import dk.sdu.se_f22.pim2.domain.ProductInfSearchImpl;

public class ProductIndexInfrastructure {
    private static final ProductIndexInfrastructure INSTANCE = new ProductIndexInfrastructure();

	private ProductIndexInfrastructure() {
    }


    public ProductInfSearch getProductSearch() {
        return new ProductInfSearchImpl();
    }

    public ProductInfIndex getProductIndex() {
        return new ProductInfIndexImpl();
    }

    public static ProductIndexInfrastructure getInstance() {
        return INSTANCE;
    }
}
