package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import dk.sdu.se_f22.searchmodule.infrastructure.SearchResult;

public interface SearchModule {
    SearchResult search(String query);
}
