package dk.sdu.se_f22.searchmodule.infrastructure.interfaces;

import dk.sdu.se_f22.sharedlibrary.SearchHits;

import java.util.ArrayList;
import java.util.List;

public interface SearchModule {
    SearchHits search(String query);



}
