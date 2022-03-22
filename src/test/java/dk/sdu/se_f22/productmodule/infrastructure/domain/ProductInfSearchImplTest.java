package dk.sdu.se_f22.productmodule.infrastructure.domain;

import dk.sdu.se_f22.productmodule.infrastructure.ProductIndexInfrastructure;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductInfSearchImplTest {

    @Test
    void searchProducts() {
        ProductInfSearchImpl productInfSearch = new ProductInfSearchImpl();
        assertNotNull(productInfSearch.searchProducts(new ArrayList<>()));
    }
}