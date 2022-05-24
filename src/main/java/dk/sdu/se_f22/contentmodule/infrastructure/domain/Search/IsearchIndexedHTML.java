package dk.sdu.se_f22.contentmodule.infrastructure.domain.Search;


import dk.sdu.se_f22.sharedlibrary.models.Content;

import java.util.List;

public interface IsearchIndexedHTML {

    List<Content> queryIndex(List<String> searchTokens);

}
