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

    public BrandIndex(){}


    @Override
    public List<Brand> searchBrandIndex(List<String> tokens) {
        List<Brand> brandList = new ArrayList<>();
        List<Integer> productIDList = getProductIDS(tokens);
        String finalQuery = createFinalQueryForIndex(productIDList);

        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryStatement = connection.prepareStatement(finalQuery);

            //Might be this
            for (int i = 0; i < productIDList.size(); i++) {
                queryStatement.setString(i + 1, String.valueOf(productIDList.get(i)));
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

    //public List<Brand> getBrandsFromIndex


    public String createFinalQueryForIndex(List<Integer> productIDS){
        String tempFinalQuery = "SELECT * FROM TokenBrandMapTest LEFT JOIN BrandTest ON BrandTest.id = brandId WHERE productId = ?";
        for (int i = 0; i < productIDS.size(); i++) {
            tempFinalQuery += " AND name = ? ";
        }

        return tempFinalQuery;
    }

    public List<Integer> getProductIDS(List<String> tokens){
        List<Integer> tempList = new ArrayList<>();

        try(Connection connection = DBConnection.getPooledConnection()) {
            for (int i = 0; i < tokens.size(); i++) {
                PreparedStatement queryStatement = connection.prepareStatement("SELECT id FROM productType WHERE name = ?");

                queryStatement.setString(1, tokens.get(i));

                ResultSet queryResultSet = queryStatement.executeQuery();

                while(queryResultSet.next()) {
                    tempList.add(queryResultSet.getInt("id"));
                }

                queryResultSet.close();
                queryStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tempList;
    }


    @Override
    public void indexBrandInformation(Brand brand, List<String> tokens) {
        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement mapInsert = connection.prepareStatement("INSERT INTO tokenbrandmap(brandid, productid) VALUES (?,?)");
            PreparedStatement tokenInsert = connection.prepareStatement("INSERT INTO tokens(token) VALUES (?)");
            PreparedStatement queryToken = connection.prepareStatement("SELECT token FROM tokens");

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
                queryTokenId = connection.prepareStatement("SELECT id FROM tokens where token = ?");
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

