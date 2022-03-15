package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import dk.sdu.se_f22.sharedlibrary.SearchHits;

public interface SearchModule {
    SearchHits search(String query);
}
