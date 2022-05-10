package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexHTML implements IindexHTML {
    private Map<Integer, Token> tokenMap;
    private Map<Integer, HTMLSite> htmlMap;

    //Denne metode tager vores htmlsite og laver det om til en token og keyen er html id'et
    @Override
    public Map<Integer, Token> indexHTMLDocsToken(List<HTMLSite> htmlSites) {
        tokenMap = new HashMap<>();
        for(HTMLSite htmlSite : htmlSites) {
            tokenMap.put(htmlSite.getId(), new Token(htmlSite.getDocumentText(), htmlSite.getId()));
        }
        return tokenMap;
    }

    //Denne metode putter vores hmltsite ind og keyen er html id'et
    @Override
    public Map<Integer, HTMLSite> indexHTMLDocs(List<HTMLSite> htmlSites) {
        htmlMap = new HashMap<>();
        for(HTMLSite htmlSite : htmlSites) {
            htmlMap.put(htmlSite.getId(),htmlSite);
        }
        return htmlMap;
    }

    public Map<Integer, Token> getTokenMap() {
        return tokenMap;
    }

    public Map<Integer, HTMLSite> getHtmlMap() {
        return htmlMap;
    }
}
