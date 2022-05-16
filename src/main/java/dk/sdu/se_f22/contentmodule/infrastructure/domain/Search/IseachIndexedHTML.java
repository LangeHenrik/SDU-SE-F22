package dk.sdu.se_f22.contentmodule.infrastructure.domain.Search;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing.HTMLSite;

import java.util.List;

public interface IseachIndexedHTML {

    List<Content> queryIndex(List<String> searchTokens);

}
