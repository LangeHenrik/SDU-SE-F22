package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

public interface IContentInfrastructre {

    void createHTMLSite(int htmlId, String htmlCode);

    void updateHTMLSite(int htmlId, String htmlCode);

    void deleteHTMLSite(int htmlId);

//    void setDelimiter();

}


