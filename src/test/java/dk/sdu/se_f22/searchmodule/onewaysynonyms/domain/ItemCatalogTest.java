package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemCatalogTest {
    ItemCatalog itemCatalog;
    @BeforeEach
    void setUp(){
        Item item1 = new Item(0,"root",0);
        Item item2 = new Item(1,"Vehicle",0);
        Item item3 = new Item(2,"Car",1);
        Item item4 = new Item(3,"Train",1);
        Item item5 = new Item(2,"Car",1);

        itemCatalog = new ItemCatalog(new Item[]{item1, item2, item3, item4,item5});
    }

    @Test
    void containsItem() {
        if (itemCatalog.containsItem("root")!=1)fail("Item not found when its expected to be there");
        if (itemCatalog.containsItem("NotVehicle")!=0)fail("Found an item that shouldn't be there");
        if (itemCatalog.containsItem("Car")<=1)fail("Didn't find all items");
        assertTrue(true);
    }

    @Test
    void testContainsItem() {
    }

    @Test
    void oneWaySynonymStrings() {
    }

    @Test
    void getItemInCatalog() {
    }
}