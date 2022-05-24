package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import java.io.IOException;

public interface IContentInfrastructre {

    void createHTMLSite(int htmlId, String htmlCode) throws IOException;

    void updateHTMLSite(int htmlId, String htmlCode) throws IOException;

    void deleteHTMLSite(int htmlId);

    //TODO: implement setDelimtier (adds new delimiter char to table)

//    void setDelimiter();

}


