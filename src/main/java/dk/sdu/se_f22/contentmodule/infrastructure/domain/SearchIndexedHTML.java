package dk.sdu.se_f22.contentmodule.infrastructure.domain;

public class SearchIndexedHTML implements IseachIndexedHTML{
    private IndexHTML indexHTML;

    @Override
    public HTMLSite searchHTMLSite(int htmlId) {
        indexHTML = new IndexHTML();
        return indexHTML.getHtmlMap().get(htmlId);
    }

    @Override
    public Token searchToken(int tokenId) {
        indexHTML = new IndexHTML();
        return indexHTML.getTokenMap().get(tokenId);
    }
}
