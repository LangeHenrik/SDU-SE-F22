package dk.sdu.se_f22.productmodule.index;


import dk.sdu.se_f22.sharedlibrary.models.ProductHit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ProductIndexTest {

    @BeforeAll
    static void setup(){
        System.out.println("@BeforeAll executed");
    }

    @Test
    void testFindHits()
    {
        ProductHit product = new ProductHit("\"Lenovo ThinkPad T410 35.8 cm (14.1\\\")\"",
                                         "Lenovo ThinkPad T410, 35.8 cm (14.1\"), 1280 x 800 pixels Lenovo ThinkPad T410. Display diagonal: 35.8 cm (14.1\"), Display resolution: 1280 x 800 pixels",
                                           "PC/Laptops");
        String token = "thinkpad";
        ProductIndex productIndex = ProductIndex.getInstance();
        String[] descriptionWords = product.getDescription().toLowerCase().split(" ");

        assertEquals(2, productIndex.findHits(descriptionWords, token));
    }

    @AfterAll
    static void tearDown(){
        System.out.println("@AfterAll executed");
    }

}