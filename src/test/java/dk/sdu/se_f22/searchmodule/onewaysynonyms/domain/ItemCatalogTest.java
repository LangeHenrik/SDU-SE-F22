package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.notFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ItemCatalogTest {
    private ItemCatalog itemCatalog;
    private Item item1, item2, item3, item4, item5;

    @BeforeEach
    void setUp() {
        item1 = new Item(0, "root", 0);
        item2 = new Item(1, "Vehicle", 0);
        item3 = new Item(2, "Car", 1);
        item4 = new Item(3, "Train", 1);
        item5 = new Item(4, "Car", 1);

        item1.addSubItem(item2);
        item2.addSubItem(item3);
        item2.addSubItem(item4);
        item2.addSubItem(item5);

        itemCatalog = new ItemCatalog(new Item[]{item1, item2, item3, item4, item5});
    }

    @Test
    void containsItem() {
        if (itemCatalog.containsItem("root") != 1) fail("Item not found when its expected to be there");
        if (itemCatalog.containsItem("NotVehicle") != 0) fail("Found an item that shouldn't be there");
        if (itemCatalog.containsItem("Car") <= 1) fail("Didn't find all items");
        assertTrue(true);
    }

    @Test
    void testContainsItem() {
        if (itemCatalog.containsItem(0) != 1) fail("Item not found when its expected to be there");
        if (itemCatalog.containsItem(5) != 0) fail("Found an item that shouldn't be there");
        assertTrue(true);

    }

    @Test
    void oneWaySynonymStrings() {
        LinkedList<Item> expected = new LinkedList<>(Arrays.asList(item3, item4, item5));
        LinkedList<Item> actual = null;
        try {
            actual = itemCatalog.oneWaySynonymStrings("Vehicle");
        } catch (notFoundException e) {
            fail();
        }
        boolean equals = true;

        for (int i =0; i<expected.size(); i++){
            if (expected.get(i)!=actual.get(i)){
                equals = false;
            }
        }

        assertTrue(equals);
    }

    @Test
    void getItemInCatalog() {
        if (itemCatalog.getItemInCatalog(0)!=item1)fail();
        if (itemCatalog.getItemInCatalog(5)!=null)fail();
        assertTrue(true);
    }
}