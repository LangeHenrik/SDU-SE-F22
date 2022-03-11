package dk.sdu.se_f22.sharedlibrary.models;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;

import java.util.ArrayList;
import java.util.HashMap;

public class Product { //initialize class

    private final HashMap<ProductAttribute, String> productAttributes; //initialize hashmap to contain product attributes
    private final ArrayList<String> availableAt; //initialize array of strings to contain local stores with stock

    public Product(){ //product constructor w.o. attribute input
        productAttributes = new HashMap<>(); //initialize hashmap
        availableAt = new ArrayList<>(); //initialize string array
    }

    public String get(ProductAttribute pA){ //String method running through pA's to assign values to productAttributes
        productAttributes.putIfAbsent(pA, "unavailable"); //replace nulls with string "unavailable" >function may be removed?
        return productAttributes.get(pA); //returns hashmap of pA's
    }

    public ArrayList<String> getLocations(){ //String array method returning the class attribute availableAt
        return availableAt;
    } //returns an arraylist of the available shops

    public HashMap<ProductAttribute,String> getAttributeMap(){
        return productAttributes;
    } //makes productAttributes available for other classes

    public boolean set(ProductAttribute pA, String value){

        if(value.endsWith("\"")){
            value = value.substring(0,value.length() - 1); //?
        }

        productAttributes.put(pA,value); //assigns key and values to map
        return productAttributes.get(pA).equalsIgnoreCase(value);
    }

    public boolean setLocations(ProductAttribute pA, ArrayList<String> values){
        availableAt.addAll(values);
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
