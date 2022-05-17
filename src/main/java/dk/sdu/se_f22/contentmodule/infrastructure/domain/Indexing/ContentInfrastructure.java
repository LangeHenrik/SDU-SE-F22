package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import java.io.IOException;

public class ContentInfrastructure implements IContentInfrastructre{
    //ClassCallOutgoing CMSIndexModule = new ClassCallOutgoing;
    HTMLParser parser = new HTMLParser();
    Tokenizer tokenizer = new Tokenizer();
    FilteredTokens filterTokens = new FilteredTokens();



    @Override
    public void createHTMLSite(int htmlId, String htmlCode) throws IOException {
        HTMLSite newSite = new HTMLSite(htmlId, htmlCode);
        parser.parseHTML(newSite);
        tokenizer.tokenizeHTMLBodyText(newSite);
        filterTokens.siteWithFilteredTokens(newSite);

        //CMSIndexModule.index(newSite.getFilteredTokensArray(), newSite.getId());
    }

    //When an htmlpage is updated
    @Override
    public void updateHTMLSite(int htmlId, String htmlCode) throws IOException {
        HTMLSite newSite = new HTMLSite(htmlId, htmlCode);
        parser.parseHTML(newSite);
        tokenizer.tokenizeHTMLBodyText(newSite);
        filterTokens.siteWithFilteredTokens(newSite);

        //CMSIndexModule.updateSingular(newSite.getFilteredTokens(), newSite.getId());
    }

    @Override
    public void deleteHTMLSite(int htmlId) {
        //CMSIndexModule.deleteSingular(htmlId);

    }
}
