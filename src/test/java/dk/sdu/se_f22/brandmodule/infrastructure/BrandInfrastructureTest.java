package dk.sdu.se_f22.brandmodule.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

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
        assertEquals(",", tokenizationParameters.delimiterRegex);
        assertEquals(".", tokenizationParameters.ignoreRegex);
    }


    @RepeatedTest(20)
    void testTokenizationParametersAtRandom(){
        Random r = new Random();
        char c1 = (char)(r.nextInt(26) + 'a');
        char c2 = (char)(r.nextInt(26) + 'a');

        brandInfrastructure.setTokenizationParameters(Character.toString(c1),Character.toString(c2));
        brandInfrastructure = new BrandInfrastructure();
        TokenizationParameters tokenizationParameters = brandInfrastructure.getTokenizationParameters();
        assertEquals(Character.toString(c1), tokenizationParameters.delimiterRegex);
        assertEquals(Character.toString(c2), tokenizationParameters.ignoreRegex);


    }
}