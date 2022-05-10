package dk.sdu.se_f22.contentmodule.infrastructure.domain.Search;

import dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing.HTMLSite;

public interface IseachIndexedHTML {
    HTMLSite searchHTMLSite(int htmlId);

}
