package dk.sdu.se_f22.productmodule.management;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ProductManager implements IProductManager, Runnable{
    
    private final ProductJSONReader jsonReader;
    
    public ArrayList<BaseProduct> baseProductArray;
    private ArrayList<BaseProduct> updatedBaseProductArray;
    private boolean backgroundThreadIsRunning = false;
    private final Thread backgroundThread;
    private boolean runBackgroundUpdates = true;
    
    private int updateInterval;    //Measured in minutes
    private final String config = "src/main/resources/dk/sdu/se_f22/productmodule/management/config.txt";
    
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
    }
    
    ///Default File Path
    public ProductManager(){
        this("src/main/resources/dk/sdu/se_f22/productmodule/management/products.json");
    }
    
    @Override
    public boolean create(BaseProduct p) {
        
        //Adds new product to the productArray.
        //Returns whether this was possible or not.
        
        checkForUpdates();
        boolean success = baseProductArray.add(p);
        updateSource();
        
        return success;
    }
    
    @Override
    public boolean createAll(ArrayList<BaseProduct> pList){
        checkForUpdates();
        
        boolean success = baseProductArray.addAll(pList);
        
        updateSource();
        return success;
    }
    
    @Override
    public BaseProduct readProduct(String productId) {
        
        //This function returns a single product based on the UUID
        //Since the read() function doesn't alter any attribute values on the product
        //There's no reason to update the source.
        
        checkForUpdates();
        
        BaseProduct toReturn = null;
        
        for(BaseProduct p : baseProductArray){
            if(p.get(ProductAttribute.UUID).equalsIgnoreCase(productId)){
                toReturn = p;
                break;
            }
        }
        
        return toReturn;
    }
    
    @Override
    public BaseProduct[] readProducts(String[] productIds) {
        
        //This function returns an array of products based on an array of UUID's
        //The size of the return array should equal the size of the input ID array
        
        checkForUpdates();
        
        BaseProduct[] returnArray = new BaseProduct[productIds.length];
        
        for(int i = 0; i < productIds.length; i++){
            for(BaseProduct p : baseProductArray){
                
                if(p.get(ProductAttribute.ID).equalsIgnoreCase(productIds[i])){
                    returnArray[i] = p;
                    break;
                }
            }
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
    public boolean update(String productId, BaseProduct p) {
        
        //This function updates the attributes of a product based on its UUID
        //By replacing it entirely with a new one. Make sure this new product has all it's attributes set correctly.
        //Any missing attribute on this other product is also removed from the source file.
        
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
    
    public boolean removeAll(String[] productIds){
        
        //This function removes all products from the productArray that matches any of the given productIds
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
        
        //ProductIndexInfrastructure.getInstance().getProductIndex().indexProducts(updatedProductArray);
        
        return backgroundThread.isAlive();
    }
    
    @Override
    public void run(){
        
        //This function is only used by the backgroundThread.
        //It busy-waits (Thread.sleep would work as well) for updateInterval (minutes)
        //Then reads the json file again and prepares a new ArrayList<Product> for use.
        
        while(runBackgroundUpdates) {
            backgroundUpdate();
        }
    }
    
    @Override
    public void reparse(){
        
        //Reads a new productArray from the jsonReader.
        //The current array is swapped out with the new one next time any CRUD operation is called.
        //This is done as such, to prevent the backgroundThread and external calls to reparse() to cause issues.
        
        try {
            updatedBaseProductArray = jsonReader.read();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private boolean checkForUpdates(){
        
        //This function is called from every create, update or delete operation and checks if a newer version of the productArray is available
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
        
        //This call rewrites the source file with the current productArray
        //This !.isEmpty and != null redundancy might not be necessary, but it's here just in case.
        
        if(!baseProductArray.isEmpty() || baseProductArray != null) {
            
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

        for(Product p : productArray){
            System.out.println(p);
        }
    }*/
    
    @Override
    public ArrayList<BaseProduct> readAllProducts() {
        
        //This function returns the entire product array. Using this function may result in errors,
        //rather use readAll()
        
        checkForUpdates();
        return baseProductArray;
    }

    /*public void printAllProducts(){

        //Prints a detailed description of each product.

        for(Product p : productArray){
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
    
    protected void finalize(){
        
        runBackgroundUpdates = false;
        backgroundThread.interrupt();
        try {
            backgroundThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}