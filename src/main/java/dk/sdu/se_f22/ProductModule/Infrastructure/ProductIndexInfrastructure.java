package dk.sdu.se_f22.ProductModule.Infrastructure;

import dk.sdu.se_f22.ProductModule.Infrastructure.data.TokenParameter;
import dk.sdu.se_f22.ProductModule.Infrastructure.domain.ProductInfIndex;
import dk.sdu.se_f22.ProductModule.Infrastructure.domain.ProductInfIndexImpl;
import dk.sdu.se_f22.ProductModule.Infrastructure.domain.ProductInfSearch;
import dk.sdu.se_f22.ProductModule.Infrastructure.domain.ProductInfSearchImpl;

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
