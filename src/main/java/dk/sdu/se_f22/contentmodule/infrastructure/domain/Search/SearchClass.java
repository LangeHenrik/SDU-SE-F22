package dk.sdu.se_f22.contentmodule.infrastructure.domain.Search;


import dk.sdu.se_f22.contentmodule.infrastructure.domain.Mock.ContentManagement;
import dk.sdu.se_f22.contentmodule.infrastructure.domain.Mock.InterfaceOutgoing;
import dk.sdu.se_f22.sharedlibrary.models.Content;

import java.util.ArrayList;
import java.util.List;

public class SearchClass implements IsearchIndexedHTML {
    InterfaceOutgoing CMSIndexModule = new InterfaceOutgoing();
    ContentManagement CMSModule = new ContentManagement();


    @Override
    public List<Content> queryIndex(List<String> searchTokens) {

        ArrayList<Integer> searchResultHTMLIds = CMSIndexModule.search(searchTokens);

        if(!searchResultHTMLIds.isEmpty()){
            return CMSModule.searchResults(searchResultHTMLIds);
        } else {
            return null;
        }

    }

}
