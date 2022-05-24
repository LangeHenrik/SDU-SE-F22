package dk.sdu.se_f22.searchmodule.onewaysynonyms;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.Item;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.OneWayImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OneWayImplementationTest {
    OneWayImplementation owi;
    List<String> tokens;

    @BeforeEach
    void setUp() {
        owi = new OneWayImplementation();
        tokens = new LinkedList<>();
        tokens.add("Humans");
        tokens.add("Peter");
        tokens.add("Lau");
        tokens.add("Emre");
        tokens.add("Eibert");
    }

    @Test
    void filter() {

        ArrayList<String> dbTokens = new ArrayList<>();
        dbTokens.add("Humans");

        dbTokens = owi.filter(dbTokens);


        assertArrayEquals(tokens.toArray(), dbTokens.toArray());
        dbTokens.clear();
        dbTokens = owi.filter(dbTokens);
        tokens.clear();
        assertEquals(dbTokens,tokens);
    }

    @Test
    void filterMultipleItems(){
        boolean bool;
        tokens.add("motordrevet");
        tokens.add("racerbil");
        tokens.add("personbil");
        tokens.add("lastbil");

        ArrayList<String> dbTokens = new ArrayList<>();
        dbTokens.add("Humans");
        dbTokens.add("motordrevet");

        dbTokens = owi.filter(dbTokens);

        for (String item : dbTokens) {
            bool = false;
            for (String item2 : tokens){
                if (item.equals(item2)){
                    bool = true;
                    break;
                }
            }
            assertTrue(bool,"Unexpected Item in DB or Item missing");
        }

    }
    @Test
    void createItemArray() {
        Item[] items = {
                new Item(2, "menneskedrevet", 1),
                new Item(3, "motordrevet", 1),
                new Item(4, "cykel", 2),
                new Item(5, "løbehjul", 2),
                new Item(6, "skateboard", 2),
                new Item(7, "racerbil", 3),
                new Item(8, "personbil", 3),
                new Item(9, "lastbil", 3),
                new Item(10, "5 Dørs", 8),
                new Item(11, "Superbil", 8),
                new Item(12, "3 Dørs", 8),
                new Item(13, "Børnecontainer", 10),
                new Item(14, "Luksus", 10),
                new Item(15, "Root", 0),
                new Item(1, "køretøjer", 15),
                new Item(17, "Peter", 16),
                new Item(18, "Lau", 16),
                new Item(16, "Humans", 15),
                new Item(20, "Emre", 16),
                new Item(21, "Eibert", 16)};

        Item[] items2 = owi.getDatabaseItems();

        for (int i = 0; i < items.length; i++) {
            assertEquals(items[i].getName(),items2[i].getName());
        }
    }
}