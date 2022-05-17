package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.Item;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.ItemCatalog;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ItemCatalogTest {

    static Item[] list;
    static LinkedList<Item> subtypesFor5Dørs;


    @BeforeAll
    static void setup() {
        Item i1 = new Item("køretøjer");
        Item i2 = new Item("motordrevet",i1);
        Item i3 = new Item("menneskedrevet",i1);
        Item i4 = new Item("racerbil",i2);
        Item i5 = new Item("personbil",i2);
        Item i6 = new Item("lastbil",i2);
        Item i7 = new Item("superbil",i5);
        Item i8 = new Item("5 dørs",i5);
        Item i9 = new Item("3 dørs",i5);
        Item i10 = new Item("børnecontainer",i8);
        Item i11 = new Item("lukus",i8);
        Item i12 = new Item("cykel",i3);
        Item i13 = new Item("løbehjul",i3);
        Item i14 = new Item("skateboard",i3);

        list = new Item[]{i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14};
        subtypesFor5Dørs = new LinkedList<>();
        subtypesFor5Dørs.add(i10);
        subtypesFor5Dørs.add(i11);
    }
    @Test
    void test1(){
        ItemCatalog test = new ItemCatalog(list);
        LinkedList<Item> subtypesFromMethod = new LinkedList<>();
        boolean bool = true;
        try {
            subtypesFromMethod.addAll(test.oneWaySynonymStrings("5 dørs"));
        } catch (notFoundException e) {}

        for(int i = 0; i<2; i++){
            if(bool){
                bool = subtypesFor5Dørs.get(i).getName().equals(subtypesFromMethod.get(i).getName());
            }
        }
        
        assertTrue(bool);
    }
}