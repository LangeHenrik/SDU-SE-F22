package dk.sdu.se_f22.searchmodule.onewaysynonyms.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OneWayImplementationTest {
    private OneWayImplementation oneWayImplementation;

    @BeforeEach
    void setUp() {
        oneWayImplementation = new OneWayImplementation();
    }

    @Test
    void filter() {
        try{
            oneWayImplementation.filter(new ArrayList<>(List.of(new String[]{"root"})));
        }catch (NullPointerException ex){
            fail("Exception was thrown when not expected");
        }
        try{
            oneWayImplementation.filter(null);
            fail("Exception was not thrown when expected");
        }catch (NullPointerException ex){
            assertTrue(true);
        }

    }

    @Test
    void addItem() {
    }

    @Test
    void testAddItem() {
    }

    @Test
    void changeSuperId() {
    }

    @Test
    void testChangeSuperId() {
    }

    @Test
    void testChangeSuperId1() {
    }

    @Test
    void testChangeSuperId2() {
    }

    @Test
    void changeItemName() {
    }

    @Test
    void testChangeItemName() {
    }

    @Test
    void deleteItem() {
    }

    @Test
    void testDeleteItem() {
    }

    @Test
    void printCatalog() {
    }
}