package dk.sdu.se_f22.productmodule.index;


import dk.sdu.se_f22.productmodule.infrastructure.domain.ProductInfIndex;
import dk.sdu.se_f22.productmodule.infrastructure.domain.ProductInfSearch;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductIndex implements ProductInfIndex, ProductInfSearch {
    @Override
    public void indexProducts(List<Product> products) {

    }

    @Override
    public List<Product> searchProducts(List<String> tokens) {
        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader("/Users/mathiaspetersen/Documents/GitHub/SDU-SE-F22/src/main/java/dk/sdu/se_f22/productmodule/index/products.json");
            Object obj = parser.parse(fileReader);
            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}