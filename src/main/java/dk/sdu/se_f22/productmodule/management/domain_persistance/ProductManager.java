package dk.sdu.se_f22.productmodule.management.domain_persistance;

import dk.sdu.se_f22.productmodule.infrastructure.ProductIndexInfrastructure;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProductManager implements IProductManager, Runnable{
    
    private final ProductJSONReader jsonReader;
    
    protected ArrayList<BaseProduct> baseProductArray;
    private ArrayList<BaseProduct> updatedBaseProductArray;
    private boolean backgroundThreadIsRunning = false;
    private final Thread backgroundThread;
    private boolean runBackgroundUpdates = true;
    
    public int getUpdateInterval() {
        return updateInterval;
    }
    
    private int updateInterval;    //Measured in minutes
    private final String config = "src/main/resources/dk/sdu/se_f22/productmodule/management/domain_persistance/config.txt";
    
    public ProductManager(String sourcePath){
        //When you create a new object of this class, the background thread is started automatically.
        //If you want to alter an attribute on a product, you MUST do this through the CRUD functions in this class.
        
        jsonReader = new ProductJSONReader(sourcePath);
        baseProductArray = getFromSource();
        updatedBaseProductArray = null;
        
        backgroundThread = new Thread(this);
        backgroundThread.setPriority(Thread.MIN_PRIORITY);
        backgroundThread.start();
        
        updateInterval = readConfig();
        
        Runtime.getRuntime().addShutdownHook(new Thread(this::finalizePM));
    }
    
    ///Default File Path: "resources/products.json"
    public ProductManager(){
        this("src/main/resources/dk/sdu/se_f22/productmodule/management/domain_persistance/products.json");
    }
    
    @Override
    public boolean create(Product p) {
        return create(toBaseProduct(p));
    }
    @Override
    public boolean create(BaseProduct p){
        
        //Adds new product to the baseProductArray.
        //Returns whether this was possible or not.
        
        checkForUpdates();
        boolean success = baseProductArray.add(p);
        updateSource();
        return success;
    }
    
    //Convert ProductManager.createAll(List<Product>) to ProductHit?? All products are being converted to BaseProducts and added to baseProductArray??
    @Override
    public boolean createAllBaseProduct(ArrayList<BaseProduct> pList){
        checkForUpdates();
        
        
        boolean success = baseProductArray.addAll(pList);
        
        updateSource();
        return success;
    }
    
    @Override
    public boolean createAll(ArrayList<Product> pList){
        checkForUpdates();
        
        if(pList == null || pList.isEmpty()){
            return false;
        }
        boolean success = true;
        
        for (Product p: pList) {
            
            if(!create(p)){
                success = false;
            }
        }
        updateSource();
        return success;
    }
    
    @Override
    public BaseProduct readBaseProduct(String productId) {
        
        //This function returns a single product based on the UUID
        //Since the read() function doesn't alter any attribute values on the product
        //There's no reason to update the source.
        
        checkForUpdates();
        
        BaseProduct toReturn = null;
        
        for(BaseProduct p : baseProductArray){ //Runs through product UUID's until the requested one is found.
            if(p.get(ProductAttribute.UUID).equalsIgnoreCase(productId)){
                toReturn = p;
                break;
            }
        }
        return toReturn;
    }
    
    @Override
    public Product readProduct(String productID){
        BaseProduct tempBaseProduct = readBaseProduct(productID);
        if (tempBaseProduct == null) {
            return null;
        } else {
            return new Product(tempBaseProduct);
        }
    }
    
    @Override
    public BaseProduct[] readBaseProducts(String[] productIds) {
        
        //This function returns an array of products based on an array of UUID's
        //The size of the return array should equal the size of the input ID array
        
        checkForUpdates();
        
        BaseProduct[] returnArray = new BaseProduct[productIds.length];
        
        for(int i = 0; i < productIds.length; i++){
            for(BaseProduct bP : baseProductArray){
                
                if(bP.get(ProductAttribute.UUID).equalsIgnoreCase(productIds[i])){
                    returnArray[i] = bP;
                    break;
                }
            }
        }
        
        return returnArray;
    }
    
    @Override
    public Product[] readProducts(String[] productIds) { //a copy with return type Product
        
        //This function returns an array of products based on an array of UUID's
        //The size of the return array should equal the size of the input ID array
        
        int i = 0;
        Product[] returnArray = new Product[productIds.length];
        for (String pId : productIds) { //running through array from rBP to convert
            
            if (pId!=null) { //checking bP has a UUID
                returnArray[i]=readProduct(pId); //converting to P, adding to array, moving to next index
            }
            i++;
        }
         
        return returnArray;
    }
    
    @Override
    public boolean update(String productId, ProductAttribute a, String s) {
        
        //This function sets the value (String) of an attribute on a product
        
        checkForUpdates();
        
        boolean succes = false;
        
        for(BaseProduct pT : baseProductArray){
            if(Objects.equals(pT.get(ProductAttribute.UUID), productId)){
                succes = pT.set(a,s);
                break;
            }
        }
        
        updateSource();
        
        return succes;
    }
    
    @Override
    public boolean update(String productId, Product p) {
        
        //This function updates the attributes of a product based on its UUID
        //By replacing it entirely with a new one. Make sure this new product has all it's attributes set correctly.
        //Any missing attribute on this other product is also removed from the source file.
        
        checkForUpdates();
        
        boolean success = false;
        
        for(BaseProduct pT : baseProductArray){
            if(pT.get(ProductAttribute.UUID).equalsIgnoreCase(productId)){
                pT = toBaseProduct(p);
                success = true;
                break;
            }
        }
        
        updateSource();
        
        return success;
    }
    
    @Override
    public boolean updateBaseProduct(String productId, BaseProduct p) {
        
        checkForUpdates();
        
        boolean success = false;
        
        for(BaseProduct pT : baseProductArray){
            if(pT.get(ProductAttribute.UUID).equalsIgnoreCase(productId)){
                pT = p;
                success = true;
                break;
            }
        }
        
        updateSource();
        
        return success;
    }
    
    @Override
    public boolean remove(String productId) {
        
        //Removes a product from the array, and consequently from the source file as well.
        
        checkForUpdates();
        boolean toReturn = false;
        
        
        for(BaseProduct p : baseProductArray){
            if (p.get(ProductAttribute.UUID).equalsIgnoreCase(productId)){
                toReturn = baseProductArray.remove(p);
                break;
            }
        }
        
        updateSource();
        
        return toReturn;
    }
    
    @Override
    public boolean removeAll(String[] productIds){
        
        //This function removes all products from the baseProductArray that matches any of the given productIds
        //It returns whether it was able to find products matching all the id's or not.
        
        checkForUpdates();
        int counter = 0;
        
        for (String productId : productIds) {
            for (BaseProduct p : baseProductArray) {
                
                if (p.get(ProductAttribute.UUID).equalsIgnoreCase(productId)) {
                    baseProductArray.remove(p);
                    counter++;
                    break;
                }
            }
        }
        
        return counter == productIds.length;
    }
    
    @Override
    public void setUpdateInterval(int time) {
        checkForUpdates();
        System.out.println("Set ProductManager update interval to: " + time + " min.");
        
        updateInterval = time;
    }
    
    private synchronized boolean backgroundUpdate(){
        
        //This is a background function to be called once every index update interval.
        //For direct control of index reparses, use reparse()
        
        if(!backgroundThreadIsRunning) {
            backgroundThreadIsRunning = true;
        }
        
        try {
            Thread.sleep(updateInterval * 60000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //Right here is where the XXXX.updateIndex() call to the module from Group 2.2 goes (see below):
        
        ProductIndexInfrastructure.getInstance().getProductIndex().indexProducts(updatedBaseProductArray);
        
        return backgroundThread.isAlive();
    }
    
    @Override
    public void run(){
        
        //This function is only used by the backgroundThread.
        //It busy-waits (Thread.sleep would work as well) for updateInterval (minutes)
        //Then reads the json file again and prepares a new ArrayList<BaseProduct> for use.
        
        while(runBackgroundUpdates) {
            backgroundUpdate();
        }
    }
    
    @Override
    public void reparse(){
        
        //Reads a new baseProductArray from the jsonReader.
        //The current array is swapped out with the new one next time any CRUD operation is called.
        //This is done as such, to prevent the backgroundThread and external calls to reparse() to cause issues.
        try {
            updatedBaseProductArray = jsonReader.read();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    BaseProduct toBaseProduct(Product product) {
        BaseProduct toReturn = new BaseProduct();
        toReturn.set(ProductAttribute.UUID, product.getUuid().toString());
        toReturn.set(ProductAttribute.AVERAGE_USER_REVIEW, Double.toString(product.getAverageUserReview()));
        toReturn.set(ProductAttribute.EAN, Long.toString(product.getEan()));
        toReturn.set(ProductAttribute.PRICE, Double.toString(product.getPrice()));
        toReturn.set(ProductAttribute.PUBLISHED_DATE, product.getPublishedDate().toString().substring(0, product.getPublishedDate().toString().indexOf("Z")));
        toReturn.set(ProductAttribute.EXPIRATION_DATE, product.getExpirationDate().toString().substring(0, product.getExpirationDate().toString().indexOf("Z")));
        toReturn.set(ProductAttribute.CATEGORY, product.getCategory());
        toReturn.set(ProductAttribute.NAME, product.getName());
        toReturn.set(ProductAttribute.DESCRIPTION, product.getDescription());
        toReturn.set(ProductAttribute.WEIGHT, Double.toString(product.getWeight()));
        toReturn.set(ProductAttribute.SIZE, product.getSize());
        toReturn.set(ProductAttribute.CLOCKSPEED, Double.toString(product.getClockspeed()));
        toReturn.setLocations(product.getInStock());
        return toReturn;
    }
    
    private boolean checkForUpdates(){
        
        //This function is called from every create, update or delete operation and checks if a newer version of the baseProductArray is available
        //This newer version may come from an external call to reparse() or from the backgroundThread from run()
        //Alternatively, use clone(). It might be more performant.
        
        boolean output = false;
        if(updatedBaseProductArray != null){
            
            baseProductArray.clear();
            baseProductArray.addAll(updatedBaseProductArray);
            updatedBaseProductArray = null;
            output = true;
        }
        return output;
    }
    
    private void updateSource(){
        
        //This call rewrites the source file with the current baseProductArray
        //This !.isEmpty and != null redundancy might not be necessary, but it's here just in case.
        
        if(baseProductArray != null && !baseProductArray.isEmpty()) {
            
            try {
                jsonReader.write(baseProductArray);
            }catch (IOException e){
                e.printStackTrace();
            }
            
        }else{
            System.err.println("ERROR function call ignored: Tried to write an empty or null array to source file.");
            System.err.println("\t  Error occurred at ProductManager.updateSource() line " + (Thread.currentThread().getStackTrace()[1].getLineNumber() - 3));
        }
    }
    
    private int readConfig(){
        
        //This function reads off the config file to get the update interval
        //Furthermore, the config file must only contain 1 numerical value
        
        int toReturn;
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(config));
            String line = br.readLine();
            int index = line.indexOf("=") +1;
            toReturn = Integer.parseInt(line.substring(index));
            
            br.close();
        }catch (IOException e){
            e.printStackTrace();
            toReturn = 5;
        }
        return toReturn;
    }

    /*public void print(){
        //Prints each products name and price.

        for(BaseProduct p : baseProductArray){
            System.out.println(p);
        }
    }*/
    
    protected ArrayList<BaseProduct> readAllProducts() {
        
        //This function returns the entire product array. Using this function may result in errors,
        //rather use readAll()
        
        checkForUpdates();
        return baseProductArray;
    }

    /*public void printAllProducts(){

        //Prints a detailed description of each product.

        for(BaseProduct p : baseProductArray){
            p.print();
        }
    }*/
    
    private ArrayList<BaseProduct> getFromSource(){
        try{
            return jsonReader.read();
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    private void finalizePM(){
        
        runBackgroundUpdates = false;
        backgroundThread.interrupt();
        
        try {
            backgroundThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}