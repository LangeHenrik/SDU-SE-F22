package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

public class ContentInfrastructure implements IContentInfrastructre{
    //InterfaceOutgoing CMSIndexModule = new InterfaceOutgoing;
    Tokenizer tokenizer = new Tokenizer();
    FilteredTokens filterTokens = new FilteredTokens();



    @Override
    public void createHTMLSite(int htmlId, String htmlCode) {
        HTMLSite newSite = new HTMLSite(htmlId, htmlCode);
        //htmlparser.parse(newSite);
        tokenizer.tokenizeHTMLBodyText(newSite);
        filterTokens.siteWithFilteredTokens(newSite);

        //CMSIndexModule.index(newSite.getFilteredTokensArray(), newSite.getId());
    }

    //When an htmlpage is updated
    @Override
    public void updateHTMLSite(int htmlId, String htmlCode) {
        HTMLSite newSite = new HTMLSite(htmlId, htmlCode, true);
        //htmlparser.parse(newSite);
        tokenizer.tokenizeHTMLBodyText(newSite);
        filterTokens.siteWithFilteredTokens(newSite);

        //CMSIndexModule.index(newSite.getFilteredTokensArray(), newSite.getId(), newSite.isUpdated());
    }

    @Override
    public void deleteHTMLSite(int htmlId) {
        //CMSIndexModule.index(htmlId);

    }
}
