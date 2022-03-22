package dk.sdu.se_f22.contentmodule.infrastructure.domain;

import dk.sdu.se_f22.contentmodule.infrastructure.data.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchTokens {


    ArrayList<String> tokens;

    //Method for getting filtered tokens from sometable and added to an arrayList
    /*
    List<FilteredTokens> getFilteredTokens(FilteredTokens ftoken){
        try {
            PreparedStatement filt = conn.prepareStatement("SELECT idTokens, filteredTokens FROM sometablename WHERE id = ? ");
            filt.setInt(1, idTokens);
            ResultSet filtResultSet = filt.executeQuery();
            List<FilteredTokens> filteredTokensArray = new ArrayList<>();
            while (filtResultSet.next()){
                filteredTokensArray.add(new FilteredTokens(filtResultSet.getInt(1), filtResultSet.getString(2)));
            }
            return filteredTokensArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }*/

}
