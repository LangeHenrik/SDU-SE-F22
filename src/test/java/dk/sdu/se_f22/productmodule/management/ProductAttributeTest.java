package dk.sdu.se_f22.productmodule.management;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductAttributeTest {
    
    @Test
    void values() {
        //Først laver jeg en liste over alle de værdier vi gerne vil have ProductAttribe skal have
        String[] sArr = {"UUID", "ID", "AVERAGE_USER_REVIEW", "IN_STOCK", "EAN", "PRICE", "PUBLISHED_DATE", "EXPIRATION_DATE", "CATEGORY", "NAME", "DESCRIPTION", "WEIGHT", "SIZE", "CLOCKSPEED"};

        //Derefter laver jeg en liste over alle de værdier ProductAttribe rent faktisk har og laver dem om til en string
        ProductAttribute[] pArr = ProductAttribute.values();
        String[] sArr2 = new String[sArr.length];

        for (int i = 0; i < pArr.length; i++) {
            sArr2[i] = pArr[i].toString();
        }

        //Til sidst tjekker vi om de to string arrays indeholder det samme
        assertArrayEquals(sArr, sArr2);
    }
    
    @Test
    void valueOf() {
        //Først laver jeg en liste over alle de værdier vi gerne vil have ProductAttribe skal kunne lave om til enum-værdien af samme navn
        String[] sArr = {"UUID", "ID", "AVERAGE_USER_REVIEW", "IN_STOCK", "EAN", "PRICE", "PUBLISHED_DATE", "EXPIRATION_DATE", "CATEGORY", "NAME", "DESCRIPTION", "WEIGHT", "SIZE", "CLOCKSPEED"};

        //Derefter laver jeg en liste over enum-værdierne
        ProductAttribute[] pArr = ProductAttribute.values();

        //Til sidst tjekker jeg hver værdi for at se om .valueOf() laver det om til den rigtige enum-værdi
        for (int i = 0; i < pArr.length; i++) {
            assertEquals(pArr[i], ProductAttribute.valueOf(sArr[i]));
        }
    }
}