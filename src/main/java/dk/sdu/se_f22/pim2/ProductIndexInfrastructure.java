package dk.sdu.se_f22.pim2;

import dk.sdu.se_f22.pim2.data.TokenParameter;
import dk.sdu.se_f22.pim2.domain.ProductInfIndex;
import dk.sdu.se_f22.pim2.domain.ProductInfIndexImpl;
import dk.sdu.se_f22.pim2.domain.ProductInfSearch;
import dk.sdu.se_f22.pim2.domain.ProductInfSearchImpl;

import java.util.List;

public class ProductIndexInfrastructure {
	private static ProductIndexInfrastructure INSTANCE;
	private final TokenParameter tokenParameter;

	private ProductIndexInfrastructure() {
		tokenParameter = TokenParameter.load();
	}

	public ProductInfSearch getProductSearch() {
		return new ProductInfSearchImpl();
	}

	public ProductInfIndex getProductIndex() {
		return new ProductInfIndexImpl();
	}

	public void setTokenParameters(String delimiter, List<String> ignoredChars) {
		this.tokenParameter.setDelimiter(delimiter);
		this.tokenParameter.setIgnoredChars(ignoredChars);
		this.tokenParameter.save();
	}

	public TokenParameter getTokenParameter() {
		return this.tokenParameter;
	}

	public static ProductIndexInfrastructure getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ProductIndexInfrastructure();
		}

		return INSTANCE;
	}


}
