package dk.sdu.se_f22.pim2;

import dk.sdu.se_f22.pim2.data.TokenParameter;
import dk.sdu.se_f22.pim2.domain.ProductInfIndex;
import dk.sdu.se_f22.pim2.domain.ProductInfIndexImpl;
import dk.sdu.se_f22.pim2.domain.ProductInfSearch;
import dk.sdu.se_f22.pim2.domain.ProductInfSearchImpl;

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
