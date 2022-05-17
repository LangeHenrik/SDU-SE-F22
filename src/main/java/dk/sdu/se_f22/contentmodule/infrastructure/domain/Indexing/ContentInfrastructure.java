package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContentInfrastructure implements IContentInfrastructre{
    //ClassCallOutgoing CMSIndexModule = new ClassCallOutgoing;
    HTMLParser parser = new HTMLParser();
    Tokenizer tokenizer = new Tokenizer();
    FilteredTokens filterTokens = new FilteredTokens();

    @Override
    public void createHTMLSite(int htmlId, String htmlCode) throws IOException {
        


        //Guidelines
        HTMLSite newSite = new HTMLSite(htmlId, htmlCode);
        
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO cms_htmlpages (html_id) VALUES (?)");
            stmt.setInt(1, htmlId);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            // TODO: Handle exception   
        }

        parser.parseHTML(newSite);
        tokenizer.tokenizeHTMLBodyText(newSite);
        filterTokens.siteWithFilteredTokens(newSite);

        //CMSIndexModule.index(newSite.getFilteredTokensArray(), newSite.getId());
    }

    //When an htmlpage is updated
    @Override
    public void updateHTMLSite(int htmlId, String htmlCode) throws IOException {

        HTMLSite newSite = new HTMLSite(htmlId, htmlCode);

        try (Connection connection = DBConnection.getPooledConnection()) {

            //Statement som tømmer en side
//            PreparedStatement s1 = connection.prepareStatement("Something");
//            s1.execute();
//            s1.close();
//
//            //Statement som opdaterer den tomme side med nyt info
//            PreparedStatement s2 = connection.prepareStatement("Something");
//            s2.execute();
//
//            s2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }





        parser.parseHTML(newSite);
        tokenizer.tokenizeHTMLBodyText(newSite);
        filterTokens.siteWithFilteredTokens(newSite);

        //CMSIndexModule.updateSingular(newSite.getFilteredTokens(), newSite.getId());
    }

    @Override
    public void deleteHTMLSite(int htmlId) {

        try (Connection connection = DBConnection.getPooledConnection()) {


            //Remove site from database
//            PreparedStatement stmt = connection.prepareStatement("Something");
//            stmt.setInt(1, htmlId);
//            stmt.execute();
//            stmt.close();
        } catch (SQLException e) {
            // TODO: Handle exception
        }




//        database.setupDatabase();
//        database.executeQuery("DELETE FROM cms_htmlpages WHERE html_id = ("+htmlId+")");
//        //CMSIndexModule.deleteSingular(htmlId);

    }
}
