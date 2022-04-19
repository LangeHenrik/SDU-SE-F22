package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.sharedlibrary.models.*;
import dk.sdu.se_f22.sharedlibrary.SearchHits;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


public class SearchModuleImpl implements SearchModule {
    private final Set<Filterable> filteringModules;
    private final Set<IndexingModule<?>> indexingModules;
    private DelimiterSettings delimiterSettings = new DelimiterSettings();

    public SearchModuleImpl() {
        this.indexingModules = new HashSet<>();
        this.filteringModules = new HashSet<>();
        delimiterSettings.addDelimiter(" ");
    }

    public <T extends IndexingModule<?>> void addIndexingModule(T index) {
        indexingModules.add(index);
    }

    public <T extends IndexingModule<?>> void removeIndexingModule(T index) {
        indexingModules.remove(index);
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
        // We need to find the indexing module for the specified page type (i.e. content, brand, product or something else)
        for (var indexingModule : indexingModules) {
            // Using reflection we can get the generic type parameter for the indexing module,
            // meaning the class specified between the angle brackets.

            // Here we just get a list of all the parameterized interfaces
            // this indexing module implements.
            List<Type> moduleInterfaces = Arrays.stream(
                    indexingModule.getClass().getGenericInterfaces()
            ).toList();

            // Since we need to find the type parameter, we down-cast to a ParameterizedType list
            List<ParameterizedType> parameterizedModuleInterfaces = moduleInterfaces
                    .stream()
                    .map(ParameterizedType.class::cast)
                    .toList();

            // Now we can extract the type arguments for the interfaces of the indexing module
            List<Type[]> interfaceTypeParameters = parameterizedModuleInterfaces
                    .stream()
                    .map(ParameterizedType::getActualTypeArguments)
                    .toList();

            // We flatten this list, to make searching for matches easier
            List<Type> flattenedInterfaceTypeParameters = interfaceTypeParameters.stream()
                    .map(Arrays::asList) // Convert Type[] to List<Type>
                    .flatMap(List::stream) // Merge all the lists together
                    .toList(); // Collect

            // Now we just need to match the given class against the type arguments
            boolean foundMatchingTypeParameter = flattenedInterfaceTypeParameters
                    .stream()
                    .anyMatch(t -> Objects.equals(t.getTypeName(), clazz.getTypeName()));

            // This indexing module is the one matching the given class, so we query that
            if(foundMatchingTypeParameter) {
                return (List<T>) indexingModule.queryIndex(tokens);
            }
        }

        throw new NoSuchElementException();
    }

    @Override
    public SearchHits search(String query) {
        IllegalChars replaceForbiddenChars = new IllegalChars();
        String cleanedQuery = replaceForbiddenChars.removeForbiddenChars(query);
        List<String> tokens = new Tokenizer().tokenize(cleanedQuery);
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
