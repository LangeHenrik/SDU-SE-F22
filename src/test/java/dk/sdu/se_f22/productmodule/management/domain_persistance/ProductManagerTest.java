package dk.sdu.se_f22.productmodule.management.domain_persistance;

import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {
    
    private BaseProduct baseProduct;
    private Product product;
    private ProductManager productManager;
    
    @BeforeAll
    static void initialize(){
        System.out.println("============ ProductManager TEST SETUP ============");
    }
    
    @BeforeEach
    void setup(){
        try {
            FileWriter fw = new FileWriter("src/test/resources/dk/sdu/se_f22/productmodule/management/domain_persistance/cheese.json");
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        baseProduct = new BaseProduct();
        for(ProductAttribute pa : ProductAttribute.values()){
            baseProduct.set(pa, "test");
        }
        
        baseProduct.set(ProductAttribute.UUID, "1cf3d1fd-7787-4b64-8ef9-0b6f131a9f7d");
        baseProduct.set(ProductAttribute.AVERAGE_USER_REVIEW, "2.8");
        baseProduct.set(ProductAttribute.EAN, "32131231");
        baseProduct.set(ProductAttribute.PRICE, "2.99");
        baseProduct.set(ProductAttribute.PUBLISHED_DATE, "2021-06-02T05:05:06.622164");
        baseProduct.set(ProductAttribute.EXPIRATION_DATE, "2025-01-25T07:40:33.169509");
        baseProduct.set(ProductAttribute.CLOCKSPEED, "2.4");
        baseProduct.set(ProductAttribute.WEIGHT, "3.1");
        product = new Product(baseProduct);
        
        productManager = new ProductManager("src/test/resources/dk/sdu/se_f22/productmodule/management/domain_persistance/cheese.json");
        System.out.println("============ INITIALIZING ============");
    }
    
    @Order(0)
    @Test
    void toBaseProduct() {
        BaseProduct returnedBaseProduct = productManager.toBaseProduct(product);
        
        assertEquals(baseProduct.get(ProductAttribute.UUID), returnedBaseProduct.get(ProductAttribute.UUID));
        assertEquals(baseProduct.get(ProductAttribute.AVERAGE_USER_REVIEW), returnedBaseProduct.get(ProductAttribute.AVERAGE_USER_REVIEW));
        assertEquals(baseProduct.getLocations(), returnedBaseProduct.getLocations());
        assertEquals(baseProduct.get(ProductAttribute.EAN), returnedBaseProduct.get(ProductAttribute.EAN));
        assertEquals(baseProduct.get(ProductAttribute.PRICE), returnedBaseProduct.get(ProductAttribute.PRICE));
        assertEquals(baseProduct.get(ProductAttribute.PUBLISHED_DATE), returnedBaseProduct.get(ProductAttribute.PUBLISHED_DATE));
        assertEquals(baseProduct.get(ProductAttribute.EXPIRATION_DATE), returnedBaseProduct.get(ProductAttribute.EXPIRATION_DATE));
        assertEquals(baseProduct.get(ProductAttribute.CATEGORY), returnedBaseProduct.get(ProductAttribute.CATEGORY));
        assertEquals(baseProduct.get(ProductAttribute.NAME), returnedBaseProduct.get(ProductAttribute.NAME));
        assertEquals(baseProduct.get(ProductAttribute.DESCRIPTION), returnedBaseProduct.get(ProductAttribute.DESCRIPTION));
        assertEquals(baseProduct.get(ProductAttribute.CLOCKSPEED), returnedBaseProduct.get(ProductAttribute.CLOCKSPEED));
        assertEquals(baseProduct.get(ProductAttribute.WEIGHT), returnedBaseProduct.get(ProductAttribute.WEIGHT));
        assertEquals(baseProduct.get(ProductAttribute.SIZE), returnedBaseProduct.get(ProductAttribute.SIZE));
    }
    
    @Order(1)
    @Test
    void create() {
        
        //creating baseProduct
        assertTrue(productManager.create(baseProduct));
        
        //creating Product
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
    void createAllBaseProducts(){
        //trying to createAll() using non-initialized, empty List
        assertFalse(productManager.createAllBaseProduct(new ArrayList<>()));
        
        ArrayList<BaseProduct> List = new ArrayList<>();
        
        assertFalse(productManager.createAllBaseProduct(List));
        
        //trying to createAll() after adding products to the List
        List.add(baseProduct);
        assertTrue(productManager.createAllBaseProduct(List));
        
        System.out.println("========== createAll() TEST DONE ============");
    }
    
    
    @Order(4)
    @Test
    void read() {
        productManager.create(product);
        
        //trying to read a productId (UUID) using an empty String
        assertNull(productManager.readProduct(""));
        
        //using read on a set product UUID
        assertEquals(productManager.readProduct("1cf3d1fd-7787-4b64-8ef9-0b6f131a9f7d").getUuid(), UUID.fromString("1cf3d1fd-7787-4b64-8ef9-0b6f131a9f7d"));
        
        //setting baseProduct UUID
        productManager.update("1cf3d1fd-7787-4b64-8ef9-0b6f131a9f7d", ProductAttribute.UUID, "1cf3d1fd-7787-4b64-8ef9-0b6f132a9f7e");
        
        assertEquals(productManager.readBaseProduct("1cf3d1fd-7787-4b64-8ef9-0b6f132a9f7e").get(ProductAttribute.UUID), "1cf3d1fd-7787-4b64-8ef9-0b6f132a9f7e");
        
        System.out.println("========== read() TEST DONE ============");
    }
    
    @Order(5)
    @Test
    void update() {
        //testing with nothing in the baseProductArray
        assertFalse(productManager.update("46", ProductAttribute.UUID, "50"));
        
        //adding baseProduct to baseProductArray
        productManager.create(baseProduct);
        
        //setting baseProduct UUID
        baseProduct.set(ProductAttribute.UUID, "30");
        
        //testing if desired update occurred
        assertTrue(productManager.update("30", ProductAttribute.UUID, "50"));
        System.out.println("========== update() TEST DONE ============");
    }
    
    
    @Order(6)
    @Test
    void remove() {
        
        //trying to remove baseProduct not in baseProductArray
        assertFalse(productManager.remove("30"));
        
        //adding baseProduct to baseProductArray
        productManager.create(baseProduct);
        productManager.baseProductArray.add(baseProduct);
        
        //setting baseProduct UUID
        baseProduct.set(ProductAttribute.UUID, "30");
        
        //trying to remove baseProduct in baseProductArray, using faulty UUID
        assertFalse(productManager.remove("45"));
        
        //trying to remove baseProduct in baseProductArray, using actual UUID
        assertTrue(productManager.remove("30"));
        System.out.println("========== remove() TEST DONE ============");
    }
    
    @Order(7)
    @Test
    void removeAll() {
        //Initializing String array of possible baseProduct UUIDs
        String[] ids = {"35", "42", "1", "364"};
        
        //Initializing second String array of UUIDs, for test with 1 not matching
        String[] ids1 = {"35", "42", "1", "363"};
        
        //creating additional baseProduct objects
        BaseProduct baseProduct1 = new BaseProduct();
        BaseProduct baseProduct2 = new BaseProduct();
        BaseProduct baseProduct3 = new BaseProduct();
        BaseProduct baseProduct4 = new BaseProduct();
        
        for (ProductAttribute pa : ProductAttribute.values()) {
            baseProduct1.set(pa, "test");
            baseProduct2.set(pa, "test");
            baseProduct3.set(pa, "test");
            baseProduct4.set(pa, "test");
        }
        
        //trying to removeAll products in empty baseProductArray
        assertFalse(productManager.removeAll(ids));
        
        //adding products to baseProductArray
        productManager.create(baseProduct1);
        productManager.create(baseProduct2);
        productManager.create(baseProduct3);
        productManager.create(baseProduct4);
        
        productManager.baseProductArray.add(baseProduct1);
        productManager.baseProductArray.add(baseProduct2);
        productManager.baseProductArray.add(baseProduct3);
        productManager.baseProductArray.add(baseProduct4);
        
        //setting baseProduct UUIDs
        baseProduct1.set(ProductAttribute.UUID, "35");
        baseProduct2.set(ProductAttribute.UUID, "42");
        baseProduct3.set(ProductAttribute.UUID, "1");
        baseProduct4.set(ProductAttribute.UUID, "364");
        
        //testing removeAll with an array containing 1 non-matching UUID
        assertFalse(productManager.removeAll(ids1));
        
        //testing removeAll with an array of matching UUIDs
        assertTrue(productManager.removeAll(ids));
        System.out.println("========== removeAll() TEST DONE ============");
    }
    
    @Order(8)
    @Test
    void readBaseProducts() {
        String[] ids = {"35", "42", "1", "364"};
        BaseProduct[] baseProducts = productManager.readBaseProducts(ids);
        BaseProduct[] baseProductTest = new BaseProduct[4];
        
        assertNotNull(baseProducts);
        assertEquals(4, baseProducts.length);
        //assertArrayEquals(baseProductTest, baseProducts);
        assertNotNull(Arrays.asList(baseProducts));
    }
    
    @Order(9)
    @Test
    void readProducts(){
        String[] ids = {
                "1cf3d1fd-7787-4b64-8ef9-0b6f131a9f7d",
                "1cf3d1fd-7787-4b64-8ef9-0b6f131a9f8d",
                "1cf3d1fd-7787-4b64-8ef9-0b6f131a9f6d",
                "1cf3d1fd-7787-4b64-8ef9-0b6f131a9f9d",
                "1cf3d1fd-7787-4b64-8ef9-0b6f131a9f5d"
        };
        
        for(String id : ids){
            //Filling in with test data
            baseProduct.set(ProductAttribute.UUID,id);
            productManager.create(new Product(baseProduct));
        }
        Product[] list = productManager.readProducts(ids);
        List<Product> list2 = new ArrayList<>(Arrays.stream(list).toList());
        assertEquals(ids.length, list.length);
        
        for(Product p : list2){
            assertNotNull(p);
            assertTrue(isPartOf(ids,p));
        }
        
        for(int i = 0; i < list.length; i++){
            assertTrue(ids[i].equalsIgnoreCase(list[i].getUuid().toString()));
        }
        System.out.println("========== readProducts() TEST DONE ============");
    }
    private boolean isPartOf(String[] ids, Product p){
        for(String id : ids){
            if(id.equalsIgnoreCase(p.getUuid().toString())){
                return true;
            }
        }
        return false;
    }
    
    @Order(10)
    @Test
    void setUpdateInterval() {
        //Setting new UpdateInterval
        productManager.setUpdateInterval(9);
        
        //Asserting the correct interval change
        assertEquals(productManager.getUpdateInterval(), 9);
        
        //Asserting a false interval change
        assertNotEquals(10, productManager.getUpdateInterval());
        System.out.println("========== setUpdateInterval() TEST DONE ============");
        
    }
    
    @Order(11)
    @Test
    void readAllProducts() {
        //Ensuring that the returned baseProduct arraylist isn't null
        assertNotNull(productManager.readAllProducts());
        
        //Ensuring that the returned arraylist is indeed an instance of the appropriate class
        assertInstanceOf(ArrayList.class, productManager.readAllProducts());
        System.out.println("========== readAllProducts() TEST DONE ============");
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