package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.IndexingModule;
import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.sharedlibrary.models.*;
import dk.sdu.se_f22.sharedlibrary.SearchHits;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchModuleImpl implements SearchModule {
    private final Set<Filterable> filteringModules;
    private final Set<IndexingModule<?>> indexingModules;

    public SearchModuleImpl() {
        this.indexingModules = new HashSet<>();
        this.filteringModules = new HashSet<>();
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
        BiFunction<Type, String, Boolean> interfaceGenericEquals = (Type genericInterface, String target) -> {
            Pattern pattern = Pattern.compile("<([^>]+)>");
            Matcher matcher = pattern.matcher(genericInterface.getTypeName());
            return matcher.find() && Objects.equals(matcher.group(1), target);
        };

        for (var index : indexingModules) {
            for (Type genericInterface : index.getClass().getGenericInterfaces()) {
                if (interfaceGenericEquals.apply(genericInterface, clazz.getTypeName())) {
                    return (List<T>) index.queryIndex(tokens);
                }
            }
        }

        throw new NoSuchElementException();
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

        return searchHits;
    }


}
