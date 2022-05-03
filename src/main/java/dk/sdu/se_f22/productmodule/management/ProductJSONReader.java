package dk.sdu.se_f22.productmodule.management;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ProductJSONReader {
    
    private final String filepath;
    private int currentLineNumber = 1;
    
    
    public ProductJSONReader(String filepath){
        this.filepath = filepath;
    }
    
    public ArrayList<Product> read() throws IOException{
        return read(filepath);
    }
    
    public ArrayList<Product> read(String filepath2) throws IOException{
        ArrayList<Product> output = new ArrayList<>();
        currentLineNumber = 1;
        int amountOfProducts = 1;
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath2));
            
            readLines(output, amountOfProducts, br);
            
        }catch (StringIndexOutOfBoundsException e){
            System.out.println("String index out of bounds at line " + currentLineNumber);
            e.printStackTrace();
        }
        return output;
    }
    
    private void readLines(ArrayList<Product> output, int amountOfProducts, BufferedReader br) throws IOException {
        boolean containsArray;
        boolean containsProductStart;
        boolean newProduct;
        boolean containsData;
        Product currentProduct = new Product();
        String line;
        
        br.readLine(); //Skipping the first line as to not break the array detection.
        currentLineNumber++;
        
        while((line = br.readLine()) != null){
            currentLineNumber++;
            
            newProduct = line.contains("}");
            containsArray = line.contains("[");
            containsData = line.contains("\"");
            containsProductStart = line.contains("{");
            
            
            if(containsArray){
                
                currentProduct.setLocations(calculateInStockArray(br));
                
            }else if(newProduct){
                
                currentProduct.set(ProductAttribute.ID, String.valueOf(amountOfProducts));
                output.add(currentProduct);
                currentProduct = new Product();
                amountOfProducts++;
                
            }else if(!containsProductStart && containsData) {
                
                setProductAttribute(line, currentProduct);
                
            }
        }
        
        br.close();
    }
    
    private ArrayList<String> calculateInStockArray(BufferedReader br) throws IOException {
        ArrayList<String> array = new ArrayList<>();
        String arrayLine;
        
        while((arrayLine = br.readLine()) != null && arrayLine.contains("\"")){
            currentLineNumber++;
            int entryStart = arrayLine.indexOf("\"");
            int entryEnd = arrayLine.lastIndexOf("\"");
            
            array.add(arrayLine.substring(entryStart + 1,entryEnd));
        }
        
        return array;
    }
    
    private String getPropertyName(String line){
        int propertyStart = line.indexOf("\"");
        int propertyEnd = line.indexOf(":");
        
        return line.substring(propertyStart + 1, propertyEnd - 1).trim();
    }
    
    private String getPropertyValue(String line){
        int valueEnd = findLastOccurence(",",line);
        int valueStart = countOccurences(line, '"') < 4 ? line.indexOf(":") - 1 : line.indexOf(":");
        
        String result = line.substring(valueStart + 3, valueEnd - 1);
        if (result.isEmpty())
            return null;
        else
            return result;
    }
    
    private void setProductAttribute(String line, Product p){
        
        String propertyName = getPropertyName(line);
        p.set(ProductAttribute.fromString(propertyName), getPropertyValue(line));
        
    }
    
    public boolean write(ArrayList<Product> list, String filepath){
        
        if(list == null || list.isEmpty()){
            throw new NullPointerException("Invalid List to write to file");
        }
        
        boolean success = false;
        int productNumber = 0;
        StringBuilder builder = new StringBuilder();
        
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            
            
            builder.append("[");                    //The file works as an array of objects with certain attributes, thus an "array-starter" here. This, however, is ignored upon reading it.
            
            for(Product p : list){                  //For each current product
                builder.append("\n{");              //Open a new "object" to be written
                
                addProductToBuilder(productNumber, builder, p, (productNumber == list.size() - 1));
                productNumber++;
            }
            
            builder.append("\n]");
            
            if(builder.isEmpty()){  //Preventing a complete dataloss if something messed up
                success = false;
                
            }else{
                bw.write(builder.toString());
                success = true;
            }
            
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return success;
    }
    
    private int addProductToBuilder(int productNumber, StringBuilder builder, Product p, boolean isLast) {
        ArrayList<String> inStockArray = new ArrayList<>();
        
        for(ProductAttribute pAttr : ProductAttribute.values()){    //Go through all known attributes
            
            String lineEnd = pAttr == ProductAttribute.CLOCKSPEED ? "" : ","; //Since writing does not ignore the attributes with no value, "Clockspeed" will always be last, and so, to get the proper JSON formating
            //this ternary is used to either add the , if there's more data in the line, or add an empty string.
            
            if(pAttr != ProductAttribute.IN_STOCK) {    //Append a line containing the string name of that attribute and it's value
                
                addPropertyToBuilder(builder, p, pAttr, lineEnd);
                
            }else{  //If it happens to be the "inStock" array, it needs to notate it as an array.
                inStockArray.clear();
                addArrayToBuilder(builder, p, pAttr, lineEnd,inStockArray);
            }
        }
        productNumber++;    //Tracking which number product we're at
        
        if(isLast) {  //If it's the last product to be written, it should not include a comma
            builder.append("\n}");
        }else{
            builder.append("\n},");
        }
        return productNumber;
    }
    
    private void addPropertyToBuilder(StringBuilder builder, Product p, ProductAttribute pAttr, String lineEnd) {
        String propertyValue = p.get(pAttr) == null ? "" : p.get(pAttr);
        
        //Getting the correct formatting: "<attr name>": "<attr value>", (Comma if it's not the last line in the product)
        builder.append("\n").append("\t").append("\"").append(pAttr.alias).append("\":").append(" \"").append(propertyValue).append("\"").append(lineEnd);
    }
    
    private void addArrayToBuilder(StringBuilder builder, Product p, ProductAttribute pAttr, String lineEnd,ArrayList<String> inStockArray) {
        builder.append("\n").append("\t").append("\"").append(pAttr.alias).append("\": ["); //Opening the array with formatting "<attr name>": [
        
        inStockArray.addAll(p.getLocations());
        
        for(int i = 0; i < inStockArray.size(); i++){      //As it's an array, each attribute value in it does not have it's own name, and can be written out like this: "<value>",
            if(i == inStockArray.size() - 1){
                builder.append("\n\t\t\"").append(inStockArray.get(i)).append("\"");   //If it's the last value in an array, it must not have a comma in the end
            }else{
                builder.append("\n\t\t\"").append(inStockArray.get(i)).append("\",");
            }
        }
        
        builder.append("\n\t]").append(lineEnd);    //Finally closing the array using
    }
    
    public boolean write(ArrayList<Product> list) throws IOException{
        return write(list, filepath);
    }
    
    private int findLastOccurence(String whatToFind, String line){
        int index = line.length();
        boolean foundIt = false;
        
        for(int i = index; i > 0; i--){
            if(line.substring(i - 1,i).contains(whatToFind)){
                foundIt = true;
                break;
            }
            index--;
        }
        if(!foundIt) {
            index = line.length() + 1;
        }
        
        return index;
    }
    
    private int countOccurences(String line, Character whatToCount){
        
        byte[] asByteArray = line.getBytes(StandardCharsets.UTF_8);
        int occurences = 0;
        
        for(byte b : asByteArray){
            if(b == whatToCount){
                occurences++;
            }
        }
        
        return occurences;
    }
    
    
}