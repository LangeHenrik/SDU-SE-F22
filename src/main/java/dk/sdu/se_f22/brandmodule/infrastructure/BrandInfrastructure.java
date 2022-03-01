package dk.sdu.se_f22.brandmodule.infrastructure;

import com.google.gson.GsonBuilder;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;

public class BrandInfrastructure implements BrandInfrastructureInterface {

    private TokenizationParameters tokenizationParameters;
    private Gson gson;
    private File file;

    public BrandInfrastructure() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TokenizationParameters.class, new TokenizationParametersAdapter());
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
        this.tokenizationParameters = gson.fromJson(jsonString,TokenizationParameters.class);
    }

    public void saveTokenizationParameters() {
        String jsonString = gson.toJson(tokenizationParameters);
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
