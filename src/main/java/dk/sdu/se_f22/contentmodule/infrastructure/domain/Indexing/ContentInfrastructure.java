package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

public class ContentInfrastructure implements IContentInfrastructre{



    @Override
    public HTMLSite createHTMLSite(int htmlId, String htmlCode) {
        HTMLSite newSite = new HTMLSite(htmlId, htmlCode);

        return newSite;
    }

    @Override
    public String readHTMLSite(int htmlId) {

        return null;
        //Kald metode fra index
    }

    @Override
    public HTMLSite updateHTMLSite(int htmlId, String htmlCode) {
        //Opdater site

        return null;
    }

    @Override
    public void deleteHTMLSite(int htmlId) {
        //Slet site fra DB

    }
}
