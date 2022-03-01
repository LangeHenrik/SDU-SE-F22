package dk.sdu.se_f22.brandmodule.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandInfrastructureTest {
    BrandInfrastructure brandInfrastructure;

    @BeforeEach
    void setup(){
        brandInfrastructure = new BrandInfrastructure();

    }

    @Test
    void testTokenizationParameters() {
        brandInfrastructure.setTokenizationParameters(",",".");
        brandInfrastructure = new BrandInfrastructure();
        TokenizationParameters tokenizationParameters = brandInfrastructure.getTokenizationParameters();
        assertEquals(",",tokenizationParameters.delimiterRegex);
        assertEquals(".",tokenizationParameters.ignoreRegex);
    }

    @Test
    void getBrandsFromSearchTokens() {
    }

    @Test
    void indexBrands() {
    }

    @Test
    void setTokenizationParameters() {
    }
}