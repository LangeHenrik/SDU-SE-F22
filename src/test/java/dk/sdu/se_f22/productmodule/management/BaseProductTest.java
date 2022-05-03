package dk.sdu.se_f22.productmodule.management;

import dk.sdu.se_f22.sharedlibrary.models.BaseProduct;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseProductTest {
    private static ProductJSONReader reader = new ProductJSONReader("src/test/resources/dk/sdu/se_f22/productmodule/management/products.json");
    
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
        ArrayList<BaseProduct> baseProductTestList = new ArrayList<>(); //initializing test arraylist
        try { //trying read function
            baseProductTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (BaseProduct p : baseProductTestList) { //running through test list
            int i = 0; //int for counting null attributes
            p.print();
            
            for (ProductAttribute pA : ProductAttribute.values()) { //asserting pAs
                if (p.get(pA) == null) { //if pA is null add i+1
                    i++;
                    //System.out.println("i++ " + p.get(pA));
                }
                assertNotNull(pA);
            }
            assertFalse(i>2);
        }
        assertFalse(baseProductTestList.isEmpty());
        
        String currentUUIDInspected;
        for(BaseProduct p : baseProductTestList){
            assertNotNull(currentUUIDInspected = p.get(ProductAttribute.UUID)); //Does each product have an UUID?
            assertEquals(36, currentUUIDInspected.length());            //Does each product's UUID have 36 characters? (It must)
        }
        
    }
    
    @Test
    void getAsNumeric(){
        ArrayList<BaseProduct> baseProductTestList = new ArrayList<>(); //initializing test arraylist
        try {                                                   //trying read function
            baseProductTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        BaseProduct baseProduct = baseProductTestList.get(0);
        assertThrows(NumberFormatException.class, () -> baseProduct.getAsNumeric(ProductAttribute.NAME));
        assertThrows(NullPointerException.class, () -> baseProduct.getAsNumeric(ProductAttribute.SIZE));
        
        /*for (ProductAttribute pA : ProductAttribute.values()) {
            for(Product p : productTestList) {
                assertThrows(NumberFormatException.class, () -> p.getAsNumeric(pA));
                //assertNull(p.getAsNumeric(pA));
            }
        }*/
        
    }
    
    @Test
    void getLocations() {
        
        ArrayList<BaseProduct> baseProductTestList = new ArrayList<>(); //initializing test arraylist
        try {                                                   //trying read function
            baseProductTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(BaseProduct p : baseProductTestList) {
            
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
        
        ArrayList<BaseProduct> baseProductTestList = new ArrayList<>(); //initializing test arraylist
        try {                                                   //trying read function
            baseProductTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String baseTestString = "hello there";
        
        for(BaseProduct p : baseProductTestList){
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
        BaseProduct testBaseProduct = new BaseProduct();
        
        ArrayList<String> namesTest = new ArrayList<>(List.of("Some","Body","Once","Told","me","The","World","Was"));
        assertTrue(testBaseProduct.setLocations(new ArrayList<>(namesTest)));
        assertEquals(namesTest, testBaseProduct.getLocations());
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