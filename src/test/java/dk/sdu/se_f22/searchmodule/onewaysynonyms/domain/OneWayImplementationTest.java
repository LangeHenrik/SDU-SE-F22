package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import dk.sdu.se_f22.searchmodule.onewaysynonyms.data.DatabaseAPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OneWayImplementationTest {
    private static OneWayImplementation oneWayImplementation;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @BeforeAll
    static void setUp() {
        oneWayImplementation = new OneWayImplementation();
    }

    @BeforeEach
    public void streamSetup() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    void filter() {
        try {
            oneWayImplementation.filter(new ArrayList<>(List.of(new String[]{"root"})));
        } catch (NullPointerException ex) {
            fail("Exception was thrown when not expected");
        }
        try {
            oneWayImplementation.filter(null);
            fail("Exception was not thrown when expected");
        } catch (NullPointerException ex) {
            assertTrue(true);
        }
    }

    @Test
    void addItem() {
        String name = "root";
        String superItem = "root";
        oneWayImplementation.addItem(name, superItem);

        name = "1";
        oneWayImplementation.addItem(name, superItem);

        name = "TEST";
        superItem = "ÆØÅ";
        oneWayImplementation.addItem(name, superItem);

        superItem = "root";
        oneWayImplementation.addItem(name, superItem);

        assertEquals("Invalid name" +
                "Invalid name" +
                "Could not add TEST with super ID: -2" +
                "Transaction was a succes" +
                "Item: TEST with super ID: 0 was added",
                outContent.toString().replaceAll("\n","").replaceAll("\r",""));
    }

    @Test
    void testAddItem() {
        String name = "root";
        int superItem = 0;
        oneWayImplementation.addItem(name, superItem);

        name = "1";
        oneWayImplementation.addItem(name, superItem);

        name = "TEST";
        superItem = -1;
        oneWayImplementation.addItem(name, superItem);

        superItem = 0;
        oneWayImplementation.addItem(name, superItem);

        assertEquals("Invalid name" +
                        "Invalid name" +
                        "Could not add TEST with super ID: -1" +
                        "Transaction was a succes" +
                        "Item: TEST with super ID: 0 was added",
                outContent.toString().replaceAll("\n","").replaceAll("\r",""));
    }

    @Test
    void changeSuperId() {
        String name = "root";
        String superItem = "root";
        oneWayImplementation.changeSuperId(name, superItem);
        oneWayImplementation.changeSuperId(name, 0);
        oneWayImplementation.changeSuperId(0, superItem);
        oneWayImplementation.changeSuperId(0, 0);

        assertEquals("Could not update Super ID" +
                        "Could not update Super ID" +
                        "Could not update Super ID" +
                        "Could not update Super ID",
                outContent.toString().replaceAll("\n","").replaceAll("\r",""));

    }

    @Test
    void changeItemName() {
        oneWayImplementation.changeItemName("root","Test1");
        oneWayImplementation.changeItemName(0,"Test1");

        assertEquals("Unable to update item nameUnable to update item name",outContent.toString().replaceAll("\n","").replaceAll("\r",""));
    }

    @Test
    void deleteItem() {
        oneWayImplementation.deleteItem("root");
        oneWayImplementation.deleteItem(0);

        assertEquals("Unable to delete itemUnable to delete item",
                outContent.toString().replaceAll("\n","").replaceAll("\r",""));
    }

    @Test
    void printCatalog() {
        oneWayImplementation.printCatalog();

        StringBuilder expected= new StringBuilder();
        for (Item item :DatabaseAPI.readEntireDB()){
            expected.append(item.toString());
        }

        assertEquals(expected.toString(),outContent.toString().replaceAll("\n","").replaceAll("\r",""));
    }
}