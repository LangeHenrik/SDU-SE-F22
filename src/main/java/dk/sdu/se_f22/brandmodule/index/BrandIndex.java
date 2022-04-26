package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandIndex implements IndexInterface {

    private Connection DBConn = DBConnection.getConnection();

    @Override
    public List<Brand> searchBrandIndex(List<String> tokens) {
        return null;
    }

    @Override
    public void indexBrandInformation(Brand brand, List<String> tokens) {
        try {
            PreparedStatement mapInsert = DBConn.prepareStatement("INSERT INTO tokenbrandmap(brandid, tokenid) VALUES (?,?)");
            PreparedStatement tokenInsert = DBConn.prepareStatement("INSERT INTO tokens(token) VALUES (?)");
            PreparedStatement queryToken = DBConn.prepareStatement("SELECT token FROM tokens");

            ResultSet rs = queryToken.executeQuery();

            ArrayList<String> newTokens = new ArrayList<>(tokens);

            String dbColumnToken = "token"; //

            PreparedStatement queryTokenId = null;
            ResultSet rsTokenId = null;


            for (int i = 0; i < tokens.size(); i++) {
                while (rs.next()) {
                    if (rs.getString(dbColumnToken) != newTokens.get(i)) {
                        tokenInsert.setString(1, newTokens.get(i));
                    }
                }
                tokenInsert.execute();
            }


            for (int j = 0; j < tokens.size(); j++) {
                queryTokenId = DBConn.prepareStatement("SELECT id FROM tokens where token = ?");
                queryTokenId.setString(1, newTokens.get(j));
                rsTokenId = queryTokenId.executeQuery();
                while (rsTokenId.next()) {
                    mapInsert.setInt(1, brand.getId());
                    mapInsert.setInt(2, rsTokenId.getInt(1));
                    mapInsert.execute();
                }
            }
            //preparedStatements
            mapInsert.close();
            tokenInsert.close();
            queryToken.close();
            queryTokenId.close();

            //resultSets
            rs.close();
            rsTokenId.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

