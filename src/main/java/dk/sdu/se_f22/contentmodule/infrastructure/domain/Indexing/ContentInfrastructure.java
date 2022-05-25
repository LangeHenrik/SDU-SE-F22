package dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing;


import dk.sdu.se_f22.contentmodule.infrastructure.domain.Mock.InterfaceOutgoing;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContentInfrastructure implements IContentInfrastructre{
    InterfaceOutgoing CMSIndexModule = new InterfaceOutgoing();
    HTMLParser parser = new HTMLParser();
    Tokenizer tokenizer = new Tokenizer();
    FilteredTokens filterTokens = new FilteredTokens();

    @Override
    public void createHTMLSite(int htmlId, String htmlCode) throws IOException {

        HTMLSite newSite = new HTMLSite(htmlId, htmlCode);
        
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO cms_htmlpages (html_id) VALUES (?)");
            stmt.setInt(1, htmlId);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        parser.parseHTML(newSite);
        tokenizer.tokenizeHTMLBodyText(newSite);
        filterTokens.siteWithFilteredTokens(newSite);

        CMSIndexModule.index(newSite.getFilteredTokens(), newSite.getId());
    }

    //When an htmlpage is updated
    @Override
    public void updateHTMLSite(int htmlId, String htmlCode) throws IOException {

        HTMLSite newSite = new HTMLSite(htmlId, htmlCode);

        try (Connection connection = DBConnection.getPooledConnection()) {

            //Empties the specific row
            PreparedStatement s1 = connection.prepareStatement("DELETE FROM cms_htmlpages WHERE VALUES (?)");
            s1.setInt(1,htmlId);

            s1.execute();
            s1.close();

            //Adds a value to the now empty row
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO cms_htmlpages (html_id) VALUES (?)");
            stmt.setInt(1, htmlId);
            stmt.execute();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        parser.parseHTML(newSite);
//      tokenizer.tokenizeHTMLBodyText(newSite);
        filterTokens.siteWithFilteredTokens(newSite);

        CMSIndexModule.updateSingular(newSite.getFilteredTokens(), newSite.getId());
    }

    @Override
    public void deleteHTMLSite(int htmlId) {

        try (Connection connection = DBConnection.getPooledConnection()) {

            //Removes site from database
            PreparedStatement s1 = connection.prepareStatement("DELETE FROM cms_htmlpages WHERE VALUES (?)");
            s1.setInt(1,htmlId);
            s1.execute();
            s1.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        CMSIndexModule.delete(htmlId);
    }

    @Override
    public void addDelimiter(char character) {

        try (Connection connection = DBConnection.getPooledConnection()) {

            //Adds delimiter to the token parameters table
            PreparedStatement s1 = connection.prepareStatement("INSERT INTO cms_tokenparameters (limitedchar) VALUES ('"+character+"')");
            s1.execute();
            s1.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDelimiter(char character) {

        try (Connection connection = DBConnection.getPooledConnection()) {

            //Deletes delimiter from token parameters table
            PreparedStatement s1 = connection.prepareStatement("DELETE FROM cms_tokenparameters WHERE VALUES ('"+character+"')");
            s1.execute();
            s1.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
