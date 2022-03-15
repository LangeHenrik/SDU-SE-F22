package dk.sdu.se_f22.brandmodule.infrastructure;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;
import dk.sdu.se_f22.brandmodule.index.IndexInterface;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

public class BrandInfrastructure implements BrandInfrastructureInterface {

    protected TokenizationParameters tokenizationParameters;
    private final Gson gson;
    private final File file;
    private IndexInterface index;

    public BrandInfrastructure() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
        file = new File("src/main/resources/dk/sdu/se_f22/brandmodule/infrastructure/TokenizationParameters.json");
        loadTokenizationParameters();
    }

    private void loadTokenizationParameters(){
        String jsonString;
        try {
            jsonString = Files.readString(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        JsonObject object = gson.fromJson(jsonString, JsonObject.class);
        String delimiter = object.get("delimiterRegex").getAsString();
        String ignore = object.get("ignoreRegex").getAsString();
        this.tokenizationParameters = new TokenizationParameters(delimiter, ignore);
    }

    private void saveTokenizationParameters() {
        JsonObject object = new JsonObject();
        object.addProperty("delimiterRegex", tokenizationParameters.delimiterRegex);
        object.addProperty("ignoreRegex", tokenizationParameters.ignoreRegex);
        String jsonString = gson.toJson(object);
        try {
            Files.write(file.toPath(), jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Brand> getBrandsFromSearchTokens(List<String> tokens) {
        return null;
    }

    @Override
    public void indexBrands(List<Brand> brands) {
        for (Brand brand : brands) {
            index.indexBrandInformation(brand, tokenizeBrand(brand).stream().toList());
        }
    }

    protected Set<String> tokenizeBrand(Brand b){
        Set<String> tokens = new HashSet<>();
        tokens.addAll(tokenizeString(b.getName()));
        tokens.addAll(tokenizeString(b.getDescription()));
        tokens.addAll(tokenizeString(b.getFounded()));
        tokens.addAll(tokenizeString(b.getHeadquarters()));
        for(String product : b.getProducts()){
            tokens.addAll(tokenizeString(product));
        }
        return tokens;
    }

    protected List<String> tokenizeString(String s){
        s = s.replaceAll(tokenizationParameters.ignoreRegex,"");
        List<String> tokens = List.of(s.split(tokenizationParameters.delimiterRegex));
        return tokens.stream().filter(String::isEmpty).toList();
    }


    @Override
    public void setTokenizationParameters(String delimiterRegex, String ignoreRegex) {
        this.tokenizationParameters = new TokenizationParameters(delimiterRegex, ignoreRegex);
        saveTokenizationParameters();
    }
}
