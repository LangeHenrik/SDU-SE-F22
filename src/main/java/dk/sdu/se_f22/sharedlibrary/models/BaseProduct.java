package dk.sdu.se_f22.sharedlibrary.models;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseProduct { //initialize class
    
    private final HashMap<ProductAttribute, String> productAttributes; //initialize hashmap to contain product attributes
    
    public BaseProduct(){ //product constructor w.o. attribute input
        productAttributes = new HashMap<>(); //initialize hashmap
    }
    
    public @Nullable String get(ProductAttribute pA){ //String method running through pA's to assign values to productAttributes
        return productAttributes.get(pA).isEmpty() ? null : productAttributes.get(pA); //returns hashmap of pA's
    }
    
    public double getAsNumeric(ProductAttribute pA){
        /*if (get(pA).isEmpty()){
            throw new NullPointerException("No value for " + pA.alias);
        }*/
        String pAttr = get(pA).replaceAll("\"","");
        double result;
        result = Double.parseDouble(pAttr);
        
        return result;
    }
    
    public ArrayList<String> getLocations(){ //String array method returning the class attribute availableAt
        return new ArrayList<>(List.of(this.get(ProductAttribute.IN_STOCK).split(",")));
    } //returns an arraylist of the available shops
    
    public boolean set(ProductAttribute pA, String value){
        if (value.isEmpty()){
            productAttributes.put(pA, null);
            return productAttributes.get(pA) == null;
        }
        
        if(value.endsWith("\"")){
            value = value.substring(0,value.length() - 1); //?
        }
        
        productAttributes.put(pA,value); //assigns key and values to map
        return productAttributes.get(pA).equalsIgnoreCase(value);
    }
    
    public boolean setLocations(ArrayList<String> values){
        
        StringBuilder sB = new StringBuilder();
        for(String s : values){
            sB.append(s).append(",");
        }
        productAttributes.put(ProductAttribute.IN_STOCK, sB.toString());
        
        return values.size() > 0; //checks if there is stock of the product in any shop
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
                for(String s : getLocations()){
                    toPrint.append(s).append("\t");
                }
                System.out.println("\t " + pA.alias + ": " + toPrint);
                
            }else {
                
                System.out.println("\t " + pA.alias + ": " + get(pA));
            }
        }
    }
}