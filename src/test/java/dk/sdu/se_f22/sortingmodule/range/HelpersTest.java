package dk.sdu.se_f22.sortingmodule.range;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HelpersTest {
    @Test
    @DisplayName("Create Attribute map")
    void createAttributeMap() {

        Map<String, Double> generatedMap = Helpers.createAttributeMapForMockResults(new String[]{"price", "height", "stock"},"1000.0,100.0,2" );

        Map<String, Double> expectedMap = new HashMap<>();
        expectedMap.put("price", 1000.0);
        expectedMap.put("height", 100.0);
        expectedMap.put("stock", 2.0);

        assertEquals(expectedMap, generatedMap);
    }

    @Test
    @DisplayName("creating the map using the headers/attribute names")
    void creatingTheMapUsingTheHeadersAttributeNames() {
        Map<String, Double> generatedMap = Helpers.createAttributeMapForMockResults(new String[]{"price", "height", "stock"},"price,height,stock" );
        assertEquals(new HashMap<String, Double>(), generatedMap);
    }
}
