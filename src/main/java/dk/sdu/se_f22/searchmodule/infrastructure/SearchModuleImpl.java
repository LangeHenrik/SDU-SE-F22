package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.LoggingProvider;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.util.*;


public class SearchModuleImpl implements SearchModule {
    private static final Logger logger = LoggingProvider.getLogger(SearchModuleImpl.class);
    private final Set<Filterable> filteringModules;
    private final Map<Class<?>, IndexingModule<?>> indexingModules;
    private DelimiterSettings delimiterSettings = new DelimiterSettings();

    public SearchModuleImpl() {
        this.indexingModules = new HashMap<>();
        this.filteringModules = new HashSet<>();
    }

    public <T extends IndexingModule<?>> void addIndexingModule(T index) {
        // Get parameterized type i.e. the thing between the angle brackets
        // Example, here the found class would be Foo:
        //      class SomeIndexingModule implements IndexingModule<Foo>
        Class<?> indexDataType = Arrays.stream(index.getClass().getGenericInterfaces())
                .map(ParameterizedType.class::cast)
                .map(ParameterizedType::getActualTypeArguments)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .map(Class.class::cast)
                .findFirst()
                .orElseThrow();

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
        List<String> tokens = List.of();
        tokens = filterTokens(tokens);

        SearchHits searchHits = new SearchHits();
        searchHits.setContents(List.of());
        searchHits.setProducts(queryIndexOfType(Product.class, tokens));
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
