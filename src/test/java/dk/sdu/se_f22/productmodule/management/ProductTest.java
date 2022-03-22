package dk.sdu.se_f22.productmodule.management;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private static JSONReader reader = new JSONReader("src/test/resources/dk/sdu/se_f22/productmodule/management/products.json");

    @BeforeAll
    static void initialize(){
        System.out.println("============ ProductTest TEST SETUP ============");
    }

    @BeforeEach
    void setup(){
        System.out.println("============ INITIALIZING ============");
    }

    @Test
    void get() {
        ArrayList<Product> productTestList = new ArrayList<>(); //initializing test arraylist
        try { //trying read function
            productTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Product p : productTestList) { //running through test list
            int i = 0; //int for counting unavailable attributes
            p.print();

            for (ProductAttribute pA : ProductAttribute.values()) { //asserting pAs
                if (p.get(pA).equalsIgnoreCase("unavailable")) { //if pA is "unavailable" add i+1
                    i++;
                    //System.out.println("i++ " + p.get(pA));
                }
                assertNotNull(pA);
            }
            assertFalse(i>2);
        }
        assertFalse(productTestList.isEmpty());

        for(Product p : productTestList) {
            for (ProductAttribute pattr : ProductAttribute.values()) {   //Has all attributes been assigned correctly?
                assertNotNull(p.get(pattr));
            }
        }

        String currentUUIDInspected;
        for(Product p : productTestList){
            assertNotNull(currentUUIDInspected = p.get(ProductAttribute.UUID)); //Does each product have an UUID?
            assertEquals(36, currentUUIDInspected.length());            //Does each product's UUID have 36 characters? (It must)
        }

    }

    @Test
    void getAsNumeric(){
        ArrayList<Product> productTestList = new ArrayList<>(); //initializing test arraylist
        try {                                                   //trying read function
            productTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ProductAttribute pA : ProductAttribute.values()) {
            for(Product p : productTestList) {

                assertNotEquals(p.getAsNumeric(pA), null);
                assertTrue(p.getAsNumeric(pA) >= 0);

            }

        }

    }

    @Test
    void getLocations() {

        ArrayList<Product> productTestList = new ArrayList<>(); //initializing test arraylist
        try {                                                   //trying read function
            productTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Product p : productTestList) {

            assertNotEquals(p.getLocations(), null);
            assertFalse(p.getLocations().isEmpty());

            assertTrue(p.getLocations().size() > 0);

            //Testing if the city name is actually a name (No danish city has a name of less than 1 character)
            assertTrue(p.getLocations().get(0).length() > 1);

            //Testing for duplicates - Product.getLocations() return a new ArrayList<String>
            ArrayList<String> currentArray = p.getLocations();
            ArrayList<String> arrayCopy = p.getLocations();
            for(String s1 : currentArray){
                int counter = 0;

                for(String s2 : arrayCopy){
                    if(s1.equalsIgnoreCase(s2)){
                        counter++;
                    }
                }
                //We expect there to be only one of each city name in the locations array
                assertFalse(counter > 1);
            }
        }
    }

    @Test
    void set() {

        ArrayList<Product> productTestList = new ArrayList<>(); //initializing test arraylist
        try {                                                   //trying read function
            productTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String baseTestString = "hello there";

        for(Product p : productTestList){
            for(ProductAttribute pA : ProductAttribute.values()){

                String previousValue = p.get(pA);

                assertTrue(p.set(pA, baseTestString));
                assertTrue(p.get(pA).equalsIgnoreCase(baseTestString));

                assertNotEquals(previousValue, p.get(pA));  //Was the value actually changed
            }
        }

    }

    @Test
    void setLocations() {
        Product testProduct = new Product();

        ArrayList<String> namesTest = new ArrayList<>(List.of("Some","Body","Once","Told","me","The","World","Was"));
        assertTrue(testProduct.setLocations(new ArrayList<>(namesTest)));
        assertEquals(namesTest, testProduct.getLocations());
    }

    @AfterEach
    void teardown(){
        System.out.println("============ TEARDOWN ============");
    }

    @AfterAll
    static void ending(){
        System.out.println("============ TEST COMPLETED SUCCESSFULLY ============");
    }
}
