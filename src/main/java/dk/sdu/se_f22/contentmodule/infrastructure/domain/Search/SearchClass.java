package dk.sdu.se_f22.contentmodule.infrastructure.domain.Search;


import dk.sdu.se_f22.sharedlibrary.models.Content;

import java.util.ArrayList;
import java.util.List;

public class SearchClass implements IsearchIndexedHTML {
    //InterfaceOutgoing CMSIndexModule = new InterfaceOutgoing;
    //CMSModulePublicInterface CMSModule = new CMSModulePublicInterface?;


    @Override
    public List<Content> queryIndex(List<String> searchTokens) {
        List<Content> nullToken = new ArrayList<>();
        /*
        ArrayList<Integer> searchResultHTMLIds = new ArrayList<Integer>(CMSIndexModule.search(searchTokens));
        //Search() returns Arraylist of Integers, takes Arraylist of Strings
        if(searchResultHTMLIds.isEmpty()){
            throw new NoResultsFoundException("No Search results found");
        } else {
            //ArrayList<Content> contents = new ArrayList<Content>(CMSModule.whatevertheirsearchmethodiscalled(searchResultHTMLIds));
            return contents;
        }
        */

        return nullToken;

    }

}
