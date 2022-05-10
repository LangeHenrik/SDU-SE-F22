package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.Map;

public interface IseachIndexedHTML {
    HTMLSite searchHTMLSite(int htmlId);
    Token searchToken(int tokenId);
}
