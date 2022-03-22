package dk.sdu.se_f22.productmodule.management;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ProductManager implements IProductManager, Runnable{

    private final JSONReader jsonReader;

    public ArrayList<Product> productArray;
    private ArrayList<Product> updatedProductArray;
    private boolean backgroundThreadIsRunning = false;
    private final Thread backgroundThread;
    private long lastCall;

    private int updateInterval;    //Measured in minutes
    private final String config = "src/main/resources/dk/sdu/se_f22/productmodule/management/config.txt";

    public ProductManager(String sourcePath){

        //When you create a new object of this class, the background thread is started automatically.
        //If you want to alter an attribute on a product, you MUST do this through the C.U.D. functions in this class.

        jsonReader = new JSONReader(sourcePath);
        productArray = getFromSource();
        updatedProductArray = null;

        backgroundThread = new Thread(this);
        backgroundThread.start();

        lastCall = System.currentTimeMillis();
        updateInterval = readConfig();
    }
    
    ///Default File Path
    public ProductManager(){
        this("src/main/resources/dk/sdu/se_f22/productmodule/management/products.json");
    }

    public static void main(String[] args) {

        //This is only used for testing right now.

        ProductManager manager = new ProductManager();
        try {
            manager.productArray = manager.jsonReader.read();
            manager.jsonReader.write(manager.productArray);
        }catch (IOException e){
            e.printStackTrace();
        }


        manager.printAllProducts();
        // write your code here
    }

    @Override
    public boolean create(Product p) {

        //Adds new product to the productArray.
        //Returns whether this was possible or not.

        checkForUpdates();
        boolean success = productArray.add(p);
        updateSource();

        return success;
    }

    @Override
    public boolean createAll(ArrayList<Product> pList){
        checkForUpdates();

        boolean success = productArray.addAll(pList);

        updateSource();
        return success;
    }

    @Override
    public Product read(String productId) {

        //This function returns a single product based on the UUID
        //Since the read() function doesn't alter any attribute values on the product
        //There's no reason to update the source.

        checkForUpdates();

        Product toReturn = null;

        for(Product p : productArray){
            if(p.get(ProductAttribute.UUID).equalsIgnoreCase(productId)){
                toReturn = p;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public Product[] readAll(String[] productIds) {

        //This function returns an array of products based on an array of UUID's
        //The size of the return array should equal the size of the input ID array

        checkForUpdates();

        Product[] returnArray = new Product[productIds.length];

        for(int i = 0; i < productIds.length; i++){
            for(Product p : productArray){

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

        for(Product pT : productArray){
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

        for(Product pT : productArray){
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


        for(Product p : productArray){
            if (p.get(ProductAttribute.UUID).equalsIgnoreCase(productId)){
                toReturn = productArray.remove(p);
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
            for (Product p : productArray) {

                if (p.get(ProductAttribute.UUID).equalsIgnoreCase(productId)) {
                    productArray.remove(p);
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

        updateInterval = time;
    }

    private boolean backgroundUpdate(){

        //This is a background function to be called once every index update interval.
        //For direct control of index reparses, use reparse()

        if(!backgroundThreadIsRunning) {
            backgroundThreadIsRunning = true;
        }

        //Right here is where the XXXX.updateIndex() call to the module from Group 2.2 goes

        return backgroundThread.isAlive();
    }

    @Override
    public void run(){

        //This function is only used by the backgroundThread.
        //It busy-waits (Thread.sleep would work as well) for updateInterval (minutes)
        //Then reads the json file again and prepares a new ArrayList<Product> for use.

        while(System.currentTimeMillis() < lastCall + (updateInterval * 60000L)){
            System.out.println();
        }

        lastCall = System.currentTimeMillis();
        reparse();
        backgroundUpdate();
    }

    @Override
    public void reparse(){

        //Reads a new productArray from the jsonReader.
        //The current array is swapped out with the new one next time any CRUD operation is called.
        //This is done as such, to prevent the backgroundThread and external calls to reparse() to cause issues.

        try {
            updatedProductArray = jsonReader.read();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean checkForUpdates(){

        //This function is called from every create, update or delete operation and checks if a newer version of the productArray is available
        //This newer version may come from an external call to reparse() or from the backgroundThread from run()
        //Alternatively, use clone(). It might be more performant.

        boolean output = false;
        if(updatedProductArray != null){

            productArray.clear();
            productArray.addAll(updatedProductArray);
            updatedProductArray = null;
            output = true;
        }
        return output;
    }

    private void updateSource(){

        //This call rewrites the source file with the current productArray
        //This !.isEmpty and != null redundancy might not be necessary, but it's here just in case.

        if(!productArray.isEmpty() || productArray != null) {

            try {
                jsonReader.write(productArray);
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
            toReturn = Integer.parseInt(br.readLine());

            br.close();
        }catch (IOException e){
            e.printStackTrace();
            toReturn = 5;
        }
        return toReturn;
    }

    public void print(){
        //Prints each products name and price.

        for(Product p : productArray){
            System.out.println(p);
        }
    }

    @Override
    public ArrayList<Product> getAllProducts() {

        //This function returns the entire product array. Using this function may result in errors,
        //rather use readAll()

        checkForUpdates();
        return productArray;
    }

    public void printAllProducts(){

        //Prints a detailed description of each product.

        for(Product p : productArray){
            p.print();
        }
    }

    private ArrayList<Product> getFromSource(){
        try{
            return jsonReader.read();
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<Product>();
    }
}
