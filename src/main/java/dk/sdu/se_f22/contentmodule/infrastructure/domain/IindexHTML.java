package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.List;
import java.util.Map;

public interface IindexHTML {
    Map<Integer,Token> indexHTMLDocsToken(List<HTMLSite> htmlSites);
    Map<Integer,HTMLSite> indexHTMLDocs(List<HTMLSite> htmlSites);
}
