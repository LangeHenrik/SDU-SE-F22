package dk.sdu.se_f22.productmodule.infrastructure;

import dk.sdu.se_f22.productmodule.infrastructure.data.TokenParameter;
import dk.sdu.se_f22.productmodule.infrastructure.domain.ProductInfIndex;
import dk.sdu.se_f22.productmodule.infrastructure.domain.ProductInfIndexImpl;
import dk.sdu.se_f22.productmodule.infrastructure.domain.ProductInfSearch;
import dk.sdu.se_f22.productmodule.infrastructure.domain.ProductInfSearchImpl;

public class ProductIndexInfrastructure {
    private static final ProductIndexInfrastructure INSTANCE = new ProductIndexInfrastructure();
    private TokenParameter tokenParameter;

	private ProductIndexInfrastructure() {
        tokenParameter = TokenParameter.load();
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

    public void setTokenParameter(String delimiter, String ignoredChars){
        this.tokenParameter.setDelimiter(delimiter);
        this.tokenParameter.setIgnoredChars(ignoredChars);
        this.tokenParameter.save();
    }

    public TokenParameter getTokenParameter() {
        return tokenParameter;
    }


}
