package dk.sdu.se_f22.productmodule.management;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import org.junit.jupiter.api.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {
    
    private Product product;
    private ProductManager productManager;
    
    @BeforeAll
    static void initialize(){
        try {
            FileWriter fw = new FileWriter("src/test/resources/dk/sdu/se_f22/productmodule/management/cheese.txt");
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("============ ProductManager TEST SETUP ============");
    }
    
    @BeforeEach
    void setup(){
        product = new Product();
        for(ProductAttribute pa : ProductAttribute.values()){
            product.set(pa, "test");
        }
        productManager = new ProductManager("src/test/resources/dk/sdu/se_f22/productmodule/management/cheese.txt");
        System.out.println("============ INITIALIZING ============");
    }
    
    @Order(1)
    @Test
    void create() {
        
        //creating product
        assertTrue(productManager.create(product));
        System.out.println("========== create() TEST DONE ============");
    }
    
    @Order(2)
    @Test
    void createAll() {
        
        //trying to createAll() using non-initialized, empty List
        assertFalse(productManager.createAll(new ArrayList<>()));
        
        ArrayList<Product> List = new ArrayList<>();
        
        assertFalse(productManager.createAll(List));
        
        //trying to createAll() after adding products to the List
        List.add(product);
        assertTrue(productManager.createAll(List));
        
        System.out.println("========== createAll() TEST DONE ============");
    }
    
    @Order(3)
    @Test
    void read() {
        
        //trying to read a productId (UUID) using an empty String
        assertNull(productManager.readProduct(""));
        
        //setting product UUID
        product.set(ProductAttribute.UUID, "25");
        
        //using read on a set product UUID
        assertEquals(product.get(ProductAttribute.UUID), "25");
        System.out.println("========== read() TEST DONE ============");
    }
    
    @Order(4)
    @Test
    void update() {
        //testing with nothing in the productArray
        assertFalse(productManager.update("46", ProductAttribute.UUID, "50"));
        
        //adding product to productArray
        productManager.create(product);
        productManager.productArray.add(product);
        
        //setting product UUID
        product.set(ProductAttribute.UUID, "30");
        
        //testing if desired update occurred
        assertTrue(productManager.update("30", ProductAttribute.UUID, "50"));
        System.out.println("========== update() TEST DONE ============");
    }
    
    @Order(5)
    @Test
    void remove() {
        
        //trying to remove product not in productArray
        assertFalse(productManager.remove("30"));
        
        //adding product to productArray
        productManager.create(product);
        productManager.productArray.add(product);
        
        //setting product UUID
        product.set(ProductAttribute.UUID, "30");
        
        //trying to remove product in productArray, using faulty UUID
        assertFalse(productManager.remove("45"));
        
        //trying to remove product in productArray, using actual UUID
        assertTrue(productManager.remove("30"));
        System.out.println("========== remove() TEST DONE ============");
    }
    
    @Order(6)
    @Test
    void removeAll() {
        //Initializing String array of possible product UUIDs
        String[] ids = {"35", "42", "1", "364"};
        
        //Initializing second String array of UUIDs, for test with 1 not matching
        String[] ids1 = {"35", "42", "1", "363"};
        
        //creating additional product objects
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        
        for (ProductAttribute pa : ProductAttribute.values()) {
            product1.set(pa, "test");
            product2.set(pa, "test");
            product3.set(pa, "test");
            product4.set(pa, "test");
        }
        
        //trying to removeAll products in empty productArray
        assertFalse(productManager.removeAll(ids));
        
        //adding products to productArray
        productManager.create(product1);
        productManager.create(product2);
        productManager.create(product3);
        productManager.create(product4);
        
        productManager.productArray.add(product1);
        productManager.productArray.add(product2);
        productManager.productArray.add(product3);
        productManager.productArray.add(product4);
        
        //setting product UUIDs
        product1.set(ProductAttribute.UUID, "35");
        product2.set(ProductAttribute.UUID, "42");
        product3.set(ProductAttribute.UUID, "1");
        product4.set(ProductAttribute.UUID, "364");
        
        //testing removeAll with an array containing 1 non-matching UUID
        assertFalse(productManager.removeAll(ids1));
        
        //testing removeAll with an array of matching UUIDs
        assertTrue(productManager.removeAll(ids));
        System.out.println("========== removeAll() TEST DONE ============");
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