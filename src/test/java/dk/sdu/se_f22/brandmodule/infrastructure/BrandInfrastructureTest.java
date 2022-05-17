package dk.sdu.se_f22.brandmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BrandInfrastructureTest extends BrandInfrastructure {
    BrandInfrastructure brandInfrastructure;

    @BeforeEach
    void setup(){
        brandInfrastructure = new BrandInfrastructure();

    }

    @RepeatedTest(20)
    void testTokenizationParametersAtRandom(){
        Random r = new Random();
        char c1 = (char)(r.nextInt(26) + 'a');
        char c2 = (char)(r.nextInt(26) + 'a');

        brandInfrastructure.setTokenizationParameters(Character.toString(c1),Character.toString(c2));
        brandInfrastructure = new BrandInfrastructure();
        TokenizationParameters tokenizationParameters = brandInfrastructure.tokenizationParameters;
        assertEquals(Character.toString(c1), tokenizationParameters.delimiterRegex);
        assertEquals(Character.toString(c2), tokenizationParameters.ignoreRegex);

    }

    @Test
    void testTokenizeString(){
        String s = "Lorem, ipsum. dolor, sit amet";
        String reg = "[,\\.]";
        String del = " ";
        brandInfrastructure.setTokenizationParameters(del,reg);
        List<String> expected = List.of("Lorem","ipsum","dolor","sit","amet");
        List<String> actual = brandInfrastructure.tokenizeString(s);
        assertEquals(expected,actual);
    }

    @Test
    void testTokenizeBrand(){
        Brand brand = new Brand(0,"Lorem","Lorem, ipsum. dolor, sit amet","ipsum","Lorem", new ArrayList<String>());
        Set<String> expected = new HashSet<>(List.of("Lorem", "ipsum", "dolor", "sit", "amet"));
        String reg = "[,\\.]";
        String del = " ";
        brandInfrastructure.setTokenizationParameters(del,reg);
        Set<String> actual = brandInfrastructure.tokenizeBrand(brand);
        assertEquals(expected,actual);
    }

    @Test
    void testTokenizeBrandFail(){
        Brand brand = new Brand(0,"Lorem","Lorem, ipsum. dolor, sit amet","ipsum","Lorem", new ArrayList<String>());
        Set<String> expected = new HashSet<>(List.of("Lorem", "ipsum", "dolor", "sit", "amet", "FAIL"));
        String reg = "[,\\.]";
        String del = " ";
        brandInfrastructure.setTokenizationParameters(del,reg);
        Set<String> actual = tokenizeBrand(brand);
        assertNotEquals(expected,actual);
    }

    @AfterEach
    void testTokenizationParameters() {
        brandInfrastructure.setTokenizationParameters(",",".");
        brandInfrastructure = new BrandInfrastructure();
        TokenizationParameters tokenizationParameters = brandInfrastructure.tokenizationParameters;
        assertEquals(",", tokenizationParameters.delimiterRegex);
        assertEquals(".", tokenizationParameters.ignoreRegex);
    }

}