package dk.sdu.se_f22.searchmodule.infrastructure;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import dk.sdu.se_f22.brandmodule.infrastructure.BrandInfrastructure;
import dk.sdu.se_f22.brandmodule.management.Bim;
import dk.sdu.se_f22.brandmodule.management.IBim;
import dk.sdu.se_f22.productmodule.infrastructure.domain.ProductInfSearchImpl;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;
import dk.sdu.se_f22.productmodule.management.domain_persistance.BaseProduct;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.searchmodule.infrastructure.logger.SearchLogger;
import dk.sdu.se_f22.searchmodule.infrastructure.tokenization.DelimiterSettings;
import dk.sdu.se_f22.searchmodule.infrastructure.tokenization.Tokenizer;
import dk.sdu.se_f22.searchmodule.twowaysynonyms.TwoWaySynonym;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.LoggingProvider;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


public class SearchModuleImpl implements SearchModule {
    private static final Logger logger = LoggingProvider.getLogger(SearchModuleImpl.class);
    private final Set<Filterable> filteringModules;
    private final Map<Class<?>, IndexingModule<?>> indexingModules;
    private final DelimiterSettings delimiterSettings = new DelimiterSettings();

    public SearchModuleImpl(Set<Filterable> filteringModules, Set<IndexingModule<?>> indexingModules) {
        this.indexingModules = new HashMap<>();
        this.filteringModules = new HashSet<>();

        filteringModules.forEach(this::addFilteringModule);
        indexingModules.forEach(this::addIndexingModule);

        delimiterSettings.addDelimiter(" ");
    }

    public SearchModuleImpl() {
        this(Set.of(), Set.of());
    }

    /**
     * This creates the search module prefilled with data
     * This could be extracted into a factory, since it isn't relevant to the search module
     */
    public static SearchModule createDefaultSearchModule() {
        /* Brand infrastructure */
        var brandInfra = new BrandInfrastructure();
        String reg = "[,\\.]";
        String del = "[\\ | \\-]";
        brandInfra.setTokenizationParameters(del,reg);
        Gson gson = new Gson();
        var filereader = new BufferedReader(new InputStreamReader(SearchModuleImpl.class.getClassLoader().getResourceAsStream("dk/sdu/se_f22/searchmodule/infrastructure/GUI/brands.json")));
        JsonReader reader = new JsonReader(filereader);
        Type empMapType = new TypeToken<Map<String, Brand>>() {}.getType();
        Map<String, Brand> data = gson.fromJson(reader, empMapType);
        List<Brand> dataList = new ArrayList<>();
        int id = 0;
        for(var entry : data.entrySet()) {
            entry.getValue().setId(id);
            entry.getValue().setName(entry.getKey());
            id++;
            dataList.add(entry.getValue());
        }

        IBim bim = new Bim();
        dataList.forEach(bim::createBrand);
        brandInfra.indexBrands(dataList);

        /* Product infrastructure */


        return new SearchModuleImpl(
                Set.of(TwoWaySynonym.getInstance()),
                Set.of(brandInfra, new ProductInfSearchImpl())
        );
    }

    /**
     * Get parameterized type i.e. the thing between the angle brackets
     * Example, here the found class would be "Foo":
     *         class SomeIndexingModule implements IndexingModule<Foo>
     * This is achieved through a recursive depth-first-search of the inheritance tree
     */
    private Class<?> getParameterizedTypeOfIndexingModule(Class<?> clazz) {
        for(var i : clazz.getGenericInterfaces()) {
            if(i instanceof Class) {
                if(getParameterizedTypeOfIndexingModule((Class<?>)i) != null)
                    return getParameterizedTypeOfIndexingModule((Class<?>)i);
            } else {
                var paramType = (ParameterizedType) i;
                if(paramType.getRawType().equals(IndexingModule.class)) {
                    return Arrays.stream(paramType.getActualTypeArguments())
                            .map(Arrays::asList)
                            .flatMap(List::stream)
                            .map(Class.class::cast)
                            .findFirst()
                            .orElseThrow();
                }
            }
        }
        return null;
    }

    public <T extends IndexingModule<?>> void addIndexingModule(T index) {
        Class<?> indexDataType = getParameterizedTypeOfIndexingModule(index.getClass());
        indexingModules.put(indexDataType, index);
    }

    public void removeIndexingModule(Class<?> clazz) {
        indexingModules.remove(clazz);
    }

    public void addFilteringModule(Filterable filteringModule) {
        filteringModules.add(filteringModule);
    }

    public void removeFilteringModule(Filterable filteringModule) {
        filteringModules.remove(filteringModule);
    }

    public List<String> filterTokens(List<String> tokens){
        for(Filterable module : filteringModules) {
            tokens = module.filter((ArrayList<String>) tokens);
        }
        return tokens;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> queryIndexOfType(Class<T> clazz, List<String> tokens) {
        var module = indexingModules.get(clazz);

        if(module == null) {
            logger.warn("Could not find indexing module: " + clazz.getName());
            return new ArrayList<>();
        }

        return (List<T>) module.queryIndex(tokens);
    }

    @Override
    public SearchHits search(String query) {
        IllegalChars replaceForbiddenChars = new IllegalChars();
        String cleanedQuery = replaceForbiddenChars.removeForbiddenChars(query);
        List<String> tokens = new Tokenizer().tokenize(cleanedQuery);
        tokens = filterTokens(tokens);

        SearchHits searchHits = new SearchHits();
        searchHits.setContents(List.of());
        searchHits.setProducts(queryIndexOfType(BaseProduct.class, tokens).stream().map(Product::new).toList());
        searchHits.setBrands(queryIndexOfType(Brand.class, tokens));
        //searchHits.setContents(queryIndexOfType(Content.class, tokens));

        SearchLogger.logSearch(query, searchHits, tokens);

        return searchHits;
    }

    @Override
    public List<String> getDelimiters() {
        return delimiterSettings.getDelimiters();
    }

    @Override
    public void addDelimiter(String delimiter) {
        delimiterSettings.addDelimiter(delimiter);
    }

    @Override
    public boolean removeDelimiter(String delim) {
        return delimiterSettings.removeDelimiter(delim);
    }

}
