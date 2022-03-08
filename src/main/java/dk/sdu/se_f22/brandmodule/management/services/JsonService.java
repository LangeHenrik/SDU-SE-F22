package dk.sdu.se_f22.brandmodule.management.services;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonService implements IJsonService {

    @Override
    public List<Brand> deserializeBrand() {
        try {
            // Load .json file
            Object obj = new JSONParser().parse(new FileReader("src/main/java/dk/sdu/se_f22/brandmodule/management/json/brands.json"));

            // JSONObject will be worked with
            JSONObject jo = (JSONObject) obj;
            var keys = jo.keySet().iterator();

            // List of brands that will be returned
            ArrayList<Brand> brands = new ArrayList<>();

            while(keys.hasNext()) {
                var name = keys.next();
                JSONObject properties =  (JSONObject) jo.get(name);

                Brand brand = new Brand();
                // Set single valued properties
                brand.name = (name.toString());
                brand.description = (properties.get("description").toString());
                brand.founded = (properties.get("founded").toString());
                brand.headquarters = (properties.get("headquarters").toString());

                // Build product array
                JSONArray objs = (JSONArray) properties.get("products");
                Iterator<String> iterator = objs.iterator();

                ArrayList<String> products = new ArrayList<>();
                iterator.forEachRemaining(p -> products.add(p.toString()));

                // Set brand property
                brand.products = (products);

                // Add brand to list of brands
                brands.add(brand);
            }

            return brands;

        } catch (IOException e) {
            System.out.println("IOException during JSON parsing.");
        } catch (ParseException e) {
            System.out.println("ParseException during JSON parsing.");
        }

        return null;
    }
}