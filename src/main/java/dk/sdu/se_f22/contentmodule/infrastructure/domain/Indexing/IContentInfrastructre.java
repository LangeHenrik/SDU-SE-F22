package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

public interface IContentInfrastructre {

    HTMLSite createHTMLSite(int htmlId, String htmlCode);

    String readHTMLSite(int htmlId);

    HTMLSite updateHTMLSite(int htmlId, String htmlCode);

    void deleteHTMLSite(int htmlId);

//    void setDelimiter();

}


