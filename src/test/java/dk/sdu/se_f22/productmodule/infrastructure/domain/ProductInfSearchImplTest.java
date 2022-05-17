package dk.sdu.se_f22.productmodule.infrastructure.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductInfSearchImplTest {

    @Test
    void searchProducts() {
        ProductInfSearchImpl productInfSearch = new ProductInfSearchImpl();
        assertNotNull(productInfSearch.searchProducts(new ArrayList<>()));
    }
}