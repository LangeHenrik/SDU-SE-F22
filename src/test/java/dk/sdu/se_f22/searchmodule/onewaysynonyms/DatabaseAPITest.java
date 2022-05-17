package dk.sdu.se_f22.searchmodule.onewaysynonyms;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI;
import dk.sdu.se_f22.searchmodule.onewaysynonyms.domain.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseAPITest {
//    @Test
//    void addItem() {
//
//        String item = "Peters pæne Bukser";
//
//        try {
//            DatabaseAPI.addItem(item);
//            DatabaseAPI.addItem(item, 2);
//        } catch (SQLException e) {
//            fail("Create query couldn't be accomplished");
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void updateSuperId() {
//        String item = "Peters grimme Bukser";
//
//        try {
//            DatabaseAPI.updateSuperId(item, 2);
//        } catch (Exception e) {
//            fail("Update query couldn't be accomplished");
//        }
//    }
//
//    @Test
//    void updateName() {
//        String item = "Peters pæne Bukser";
//        try {
//            DatabaseAPI.updateName(2, item);
//        } catch (SQLException e) {
//            fail("Update query couldn't be accomplished");
//            e.printStackTrace();
//        }
//    }

    @Test
    void readEntireDB() {
        Item[] items = {
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
                new Item(21, "Eibert", 16),
                new Item(2, "menneskedrevet", 15)};

        Item[] items2 = DatabaseAPI.readEntireDB();

        for (int i = 0; i < items.length; i++) {
            assertEquals(items[i].getName(), items2[i].getName());
        }
    }
}