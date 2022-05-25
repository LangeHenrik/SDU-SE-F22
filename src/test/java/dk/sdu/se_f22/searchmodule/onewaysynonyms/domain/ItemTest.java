package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private static Item item,item1,item2,item3,item4,item5,item6,item7;

    @BeforeAll
    static void setUp() {
        item = new Item(0, "root", 0);
        item1 = new Item(1, "Objects", 0);
        item2 = new Item(2, "Cars", 0);

        item3 = new Item(3,"Humans",0);
        item4 = new Item(4,"Bob",3);
        item5 = new Item(5,"Ole",3);
        item3.addSubItem(item4);
        item3.addSubItem(item5);
        item4.setSuperItem(item3);

        item6 = new Item(6,"Computer",0);
        item7 = new Item(7,"MacBook",6);
        item6.addSubItem(item7);


    }

    @Test
    void addSubItem() {
        item.addSubItem(item1);
        item.addSubItem(item2);
        assertEquals(item.getSubItems().get(0),item1);
    }

    @Test
    void setSuperItem() {
        item1.setSuperItem(item);
        assertEquals(item1.getSuperItem(),item);
    }

    @Test
    void removeSubItem() {
        item6.removeSubItem(item7);
        assertEquals(0,item6.getSubItems().size());
    }

    @Test
    void getName() {
        assertEquals("root",item.getName());
    }

    @Test
    void getSubItems() {
        assertEquals(2,item3.getSubItems().size());

    }

    @Test
    void getSuperItem() {
        assertEquals(item4.getSuperItem(),item3);
    }

    @Test
    void getSuperId() {
        assertEquals(item.getSuperId(),0);
    }

    @Test
    void setSuperId() {
        item2.setSuperId(1);
        assertEquals(item2.getSuperId(),1);
    }

    @Test
    void getId() {
        assertEquals(item.getId(),0);
    }
}