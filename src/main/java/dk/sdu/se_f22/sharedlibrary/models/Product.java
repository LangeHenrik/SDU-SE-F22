package dk.sdu.se_f22.sharedlibrary.models;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;

import java.util.ArrayList;
import java.util.HashMap;

public class Product { //initialize class
    
    private final HashMap<ProductAttribute, String> productAttributes; //initialize hashmap to contain product attributes
    private ArrayList<String> availableAt; //initialize array of strings to contain local stores with stock
    
    public Product(){ //product constructor w.o. attribute input
        productAttributes = new HashMap<>(); //initialize hashmap
        availableAt = new ArrayList<>(); //initialize string array
    }
    
    public String get(ProductAttribute pA){ //String method running through pA's to assign values to productAttributes
        productAttributes.putIfAbsent(pA, "unavailable"); //replace nulls with string "unavailable" >function may get removed based on feedback
        return productAttributes.get(pA); //returns hashmap of pA's
    }
    
    public double getAsNumeric(ProductAttribute pA){
        String pattr = get(pA).replaceAll("\"","");
        double result;
        try {
            result = Double.parseDouble(pattr);
        } catch (NumberFormatException e) {
            return 0.00D;
        } catch (NullPointerException e) {
            return 0.00D;
        }
        return result;
    }
    
    public ArrayList<String> getLocations(){ //String array method returning the class attribute availableAt
        return new ArrayList<>(availableAt);
    } //returns an arraylist of the available shops
    
    HashMap<ProductAttribute,String> getAttributeMap(){
        return productAttributes;
    }                               //makes productAttributes available for other classes
    
    public boolean set(ProductAttribute pA, String value){
        
        if(value.endsWith("\"")){
            value = value.substring(0,value.length() - 1); //?
        }
        
        productAttributes.put(pA,value); //assigns key and values to map
        return productAttributes.get(pA).equalsIgnoreCase(value);
    }
    
    public boolean setLocations(ArrayList<String> values){
        
        availableAt = values;
        
        StringBuilder sB = new StringBuilder();
        for(String s : availableAt){
            sB.append(s).append(",");
        }
        productAttributes.put(ProductAttribute.IN_STOCK, sB.toString());
        
        return availableAt.size() > 0; //checks if there is stock of the product in any shop
    }
    
    @Override
    public String toString(){ //overrides toString method, returning product name and price
        return "Product: " + productAttributes.get(ProductAttribute.NAME) + " price: " + productAttributes.get(ProductAttribute.PRICE);
    }
    
    public void print(){
        System.out.println("Product : " + productAttributes.get(ProductAttribute.NAME)); //returns product name
        
        for(ProductAttribute pA : ProductAttribute.values()){ //runs through all product attributes
            
            if(pA == ProductAttribute.IN_STOCK){ //if product is in stock, prints the available locations
                StringBuilder toPrint = new StringBuilder();
                for(String s : availableAt){
                    toPrint.append(s).append("\t");
                }
                System.out.println("\t " + pA.alias + ": " + toPrint);
                
            }else {
                
                System.out.println("\t " + pA.alias + ": " + get(pA));
            }
        }
    }
}