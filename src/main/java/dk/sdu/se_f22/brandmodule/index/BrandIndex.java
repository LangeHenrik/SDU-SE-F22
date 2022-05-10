package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.List;

public class BrandIndex implements IndexInterface {

    Connection DBConn = DBConnection.getConnection();


    public BrandIndex(){}


    @Override
    public List<Brand> searchBrandIndex(List<String> tokens) {
        List<Brand> brandList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        String finalQuery = "";

        finalQuery = "SELECT * FROM TokenBrandMapTest LEFT JOIN BrandTest ON BrandTest.id = brandId WHERE productId = ?";


        try {
            for (int i = 0; i < tokens.size(); i++) {
                PreparedStatement queryStatement = null;

                queryStatement = DBConn.prepareStatement("SELECT id FROM productType WHERE name = ?");

                queryStatement.setString(1, tokens.get(i));
                ResultSet queryResultSet = queryStatement.executeQuery();
                while(queryResultSet.next()) {
                    tempList.add(Integer.valueOf(queryResultSet.getInt("id")));
                }
                System.out.println(tempList);
                queryResultSet.close();
                queryStatement.close();
            }

            for (int i = 0; i < tempList.size(); i++) {
                finalQuery += " AND name = ? ";
            }

            PreparedStatement queryStatement = DBConn.prepareStatement(finalQuery);
            //Might be this
            for (int i = 0; i < tempList.size(); i++) {
                queryStatement.setString(i + 1, String.valueOf(tempList.get(i)));
            }

            ResultSet queryResultSet = queryStatement.executeQuery();

            while (queryResultSet.next()) {
                brandList.add(new Brand(Integer.valueOf(queryResultSet.getString("id")),
                        queryResultSet.getString("name"),
                        queryResultSet.getString("description"),
                        queryResultSet.getString("founded"),
                        queryResultSet.getString("headquarters"), new ArrayList<String>()));
            }
            queryResultSet.close();
            queryStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brandList;
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
                queryTokenId = DBConn.prepareStatement("SELECT id FROM tokens where token = ?");
                queryTokenId.setString(1, newTokens.get(i));
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

