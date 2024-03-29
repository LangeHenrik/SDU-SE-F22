package dk.sdu.se_f22.contentmodule.infrastructure.data;


import dk.sdu.se_f22.contentmodule.infrastructure.domain.Indexing.HTMLSite;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQueries  {

    //Logs paramitization
    public void logParameters(int pageId, int parameterId) throws IOException {
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO cms_usedparameters (page_id, parameter_id) VALUES (?,?)");
            stmt.setInt(1, pageId);
            stmt.setInt(2, parameterId);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //Returns a string containing all parameters
    public String getParameters(){
        String chars = "";
        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cms_tokenparameters");
            ResultSet queryResultSet = stmt.executeQuery();
            while (queryResultSet.next()) {
                chars = chars + queryResultSet.getString("limitedchar");
            }
            stmt.close();
            queryResultSet.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return chars;

    }


    //Searches pageID from html ID
   public int getPageID(int html_id) {
        int pageId = 0;

       try (Connection connection = DBConnection.getPooledConnection()) {
           PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cms_htmlpages WHERE html_id = ?");
           stmt.setInt(1, html_id);
           ResultSet queryResultSet = stmt.executeQuery();
           while (queryResultSet.next()) {
               pageId = queryResultSet.getInt("page_id");
           }
           stmt.close();
           queryResultSet.close();
       } catch (SQLException e) {
          e.printStackTrace();
           System.out.println(3);

       }
       return pageId;
}


//Searches parameterID from a given parameter
    public int getParameterID(char parameter) {
        int parameterId = 0;
        String holder = "";

        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cms_parameterslist WHERE parameter = ?");
            holder+=parameter;
            stmt.setString(1,holder);
            stmt.execute();
            ResultSet queryResultSet = stmt.executeQuery();



            while (queryResultSet.next()) {
                parameterId = queryResultSet.getInt("parameter_id");
            }

            stmt.close();
            queryResultSet.close();
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println(4);

        }
        return parameterId;

    }
}

