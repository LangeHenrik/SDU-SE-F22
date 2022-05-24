package dk.sdu.se_f22.brandmodule.infrastructure;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;
import dk.sdu.se_f22.brandmodule.index.*;
import dk.sdu.se_f22.brandmodule.stemming.*;
import dk.sdu.se_f22.productmodule.irregularwords.IrregularWords;
import dk.sdu.se_f22.sharedlibrary.models.*;

public class BrandInfrastructure implements BrandInfrastructureInterface {

	protected TokenizationParameters tokenizationParameters;
	private final Gson gson;
	private final File file;
	private final IndexInterface index;
	private final IStemmer stemming;
	//private final IStopWords stopwords;

	public BrandInfrastructure() {
		// initialize gson
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		gson = builder.create();

		// setup tokenizationParameters
		file = new File("src/main/resources/dk/sdu/se_f22/brandmodule/infrastructure/TokenizationParameters.json");
		try {
			loadTokenizationParameters();
		} catch (IOException e) {
			// use default parameters
			this.tokenizationParameters = new TokenizationParameters(".", ",");
		}

		// initialize filters
		index = new BrandIndex();
		stemming = new Stemmer();
		//stopwords = new Stopwords();
	}

	@Override
	public List<Brand> queryIndex(List<String> tokens) {
		return index.searchBrandIndex(tokens);
	}

	@Override
	public void indexBrands(List<Brand> brands) {
		for (Brand brand : brands) {
			// .stream.toList() converts from a Set to a List
			List<String> brandTokens = tokenizeBrand(brand).stream().toList();
			brandTokens = filterTokens(brandTokens);
			index.indexBrandInformation(brand, brandTokens);
		}
	}

	@Override
	public void setTokenizationParameters(String delimiterRegex, String ignoreRegex) {
		this.tokenizationParameters = new TokenizationParameters(delimiterRegex, ignoreRegex);
		try {
			saveTokenizationParameters();
		} catch (IOException ignored) { }
	}

	private void loadTokenizationParameters() throws IOException {
		String jsonString;
		jsonString = Files.readString(file.toPath());

		JsonObject object = gson.fromJson(jsonString, JsonObject.class);
		String delimiter = object.get("delimiterRegex").getAsString();
		String ignore = object.get("ignoreRegex").getAsString();
		this.tokenizationParameters = new TokenizationParameters(delimiter, ignore);
	}

	private void saveTokenizationParameters() throws IOException {
		JsonObject object = new JsonObject();
		object.addProperty("delimiterRegex", tokenizationParameters.delimiterRegex);
		object.addProperty("ignoreRegex", tokenizationParameters.ignoreRegex);
		String jsonString = gson.toJson(object);

		Files.write(file.toPath(), jsonString.getBytes());
	}

	private List<String> filterTokens(List<String> tokens) {
		tokens = stemming.stem(tokens);
		tokens = IrregularWords.INSTANCE.searchForIrregularWords(tokens);
		// tokens = stopwords.filter(tokens);
		return tokens;
	}

	protected Set<String> tokenizeBrand(Brand brand) {
		Set<String> tokens = new HashSet<>();
		// run tokenizeString on all fields of the brand
		tokens.addAll(tokenizeString(brand.getName()));
		tokens.addAll(tokenizeString(brand.getDescription()));
		tokens.addAll(tokenizeString(brand.getFounded()));
		tokens.addAll(tokenizeString(brand.getHeadquarters()));
		for (String product : brand.getProducts()) {
			tokens.addAll(tokenizeString(product));
		}
		return tokens;
	}

	protected List<String> tokenizeString(String inString) {
		inString = inString.replaceAll(tokenizationParameters.ignoreRegex, "");
		List<String> tokens = List.of(inString.split(tokenizationParameters.delimiterRegex));
		return tokens.stream().filter(tokenString -> !tokenString.isEmpty()).toList();
	}
}
