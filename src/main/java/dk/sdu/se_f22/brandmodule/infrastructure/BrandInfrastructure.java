package dk.sdu.se_f22.brandmodule.infrastructure;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

public class BrandInfrastructure implements BrandInfrastructureInterface {

    private TokenizationParameters tokenizationParameters;
    private final Gson gson;
    private final File file;

    public BrandInfrastructure() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
        file = new File("src/main/resources/dk/sdu/se_f22/brandmodule/infrastructure/TokenizationParameters.json");
        loadTokenizationParameters();
    }

    public void loadTokenizationParameters(){
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

    public void saveTokenizationParameters() {
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

    }

    @Override
    public void setTokenizationParameters(String delimiterRegex, String ignoreRegex) {
        this.tokenizationParameters = new TokenizationParameters(delimiterRegex, ignoreRegex);
        saveTokenizationParameters();
    }

    public TokenizationParameters getTokenizationParameters() {
        return this.tokenizationParameters;
    }
}
