package dk.sdu.se_f22.productmodule.infrastructure.domain;

import dk.sdu.se_f22.productmodule.infrastructure.ProductIndexInfrastructure;
import dk.sdu.se_f22.productmodule.management.BaseProduct;
import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductInfIndexImplTest {
	private static ProductInfIndexImpl productIndex;


	@BeforeAll
	static void init() {
		productIndex = (ProductInfIndexImpl) ProductIndexInfrastructure.getInstance().getProductIndex();
	}

	@Test
	void validateTokenizer() throws Exception {
		BaseProduct product = new BaseProduct();


		List<String> tokens = Whitebox.invokeMethod(productIndex, "tokenize", product);

		assertNotNull(tokens);
		System.out.println(tokens);
		assertTrue(tokens.isEmpty());

		product.set(ProductAttribute.NAME, "My Awesome Product");
		List<String> tokensAfterData = Whitebox.invokeMethod(productIndex, "tokenize", product);

		assertNotNull(tokensAfterData);
		assertFalse(tokensAfterData.isEmpty());

	}


}
