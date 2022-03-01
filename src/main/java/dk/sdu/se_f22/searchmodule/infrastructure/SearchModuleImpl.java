package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.util.ArrayList;

public class SearchModuleImpl implements SearchModule{

    @Override
    public SearchResult search(String query) {
        SearchResult searchResult = new SearchResult(/*new ArrayList<Content>(), new ArrayList<Product>(),*/ new ArrayList<Brand>());
        return searchResult;
    }
}
