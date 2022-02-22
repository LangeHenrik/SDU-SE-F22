package dk.sdu.se_f22.pim2;

public class ProductIndexInfrastructure {
    private ProductIndexInfrastructure() {
    }

    private static final ProductIndexInfrastructure INSTANCE = new ProductIndexInfrastructure();

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
