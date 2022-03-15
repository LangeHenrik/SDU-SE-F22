package dk.sdu.se_f22.brandmodule.infrastructure;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.google.gson.*;
import dk.sdu.se_f22.brandmodule.index.*;
import dk.sdu.se_f22.brandmodule.stemming.*;
import dk.sdu.se_f22.sharedlibrary.models.*;

public class BrandInfrastructure implements BrandInfrastructureInterface {

	protected TokenizationParameters tokenizationParameters;
	private final Gson gson;
	private final File file;
	private final IndexInterface index;
	private final IStemmer stemming;

	public BrandInfrastructure() {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		gson = builder.create();
		file = new File("src/main/resources/dk/sdu/se_f22/brandmodule/infrastructure/TokenizationParameters.json");
		loadTokenizationParameters();
		index = new BrandIndex();
		stemming = new Stemmer();
	}

	@Override
	public void indexBrands(List<Brand> brands) {
		for (Brand brand : brands) {
			index.indexBrandInformation(brand, tokenizeBrand(brand).stream().toList());
		}
	}

	@Override
	public List<Brand> getBrandsFromSearchTokens(List<String> tokens) {
		return index.searchBrandIndex(filterTokens(tokens));
	}

	@Override
	public void setTokenizationParameters(String delimiterRegex, String ignoreRegex) {
		this.tokenizationParameters = new TokenizationParameters(delimiterRegex, ignoreRegex);
		saveTokenizationParameters();
	}

	private List<String> filterTokens(List<String> tokens) {
		tokens = stemming.stem(tokens);
		return tokens;
	}

	protected Set<String> tokenizeBrand(Brand brand) {
		Set<String> tokens = new HashSet<>();
		tokens.addAll(tokenizeString(brand.getName()));
		tokens.addAll(tokenizeString(brand.getDescription()));
		tokens.addAll(tokenizeString(brand.getFounded()));
		tokens.addAll(tokenizeString(brand.getHeadquarters()));
		for (String product : brand.getProducts()) {
			tokens.addAll(tokenizeString(product));
		}
		return tokens;
	}

	protected List<String> tokenizeString(String toTokenize) {
		toTokenize = toTokenize.replaceAll(tokenizationParameters.ignoreRegex, "");
		List<String> tokens = List.of(toTokenize.split(tokenizationParameters.delimiterRegex));
		return tokens.stream().filter(x -> !x.isEmpty()).toList();
	}

	private void loadTokenizationParameters() {
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
}
