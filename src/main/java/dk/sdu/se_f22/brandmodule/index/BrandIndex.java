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

    public BrandIndex(){}


    @Override
    public List<Brand> searchBrandIndex(List<String> tokens) {
        List<Brand> brandList = new ArrayList<>();
        if (tokens.size() == 0){
            return brandList;
        }
        List<Integer> productIDList = getTokenIDS(tokens);
        String finalQuery = createFinalQueryForIndex(productIDList);

        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryStatement = connection.prepareStatement(finalQuery);

            for (int i = 0; i < productIDList.size(); i++) {
                queryStatement.setInt(i + 1, productIDList.get(i));
            }

            queryStatement.setInt(productIDList.size() + 1, productIDList.size());

            ResultSet queryResultSet = queryStatement.executeQuery();
            while (queryResultSet.next()) {

                brandList.add(getBrandFromId(queryResultSet.getInt("brandId")));

            }
            queryResultSet.close();
            queryStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brandList;
    }

    //public List<Brand> getBrandsFromIndex

    public Brand getBrandFromId(int id){
        Brand resultingBrand = null;
        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * From brand WHERE id = ?");

            queryStatement.setInt(1, id);

            ResultSet queryResultSet = queryStatement.executeQuery();

            queryResultSet.next();
            resultingBrand = new Brand(Integer.valueOf(queryResultSet.getString("id")),
                    queryResultSet.getString("name"),
                    queryResultSet.getString("description"),
                    queryResultSet.getString("founded"),
                    queryResultSet.getString("headquarters"), new ArrayList<String>());

            queryResultSet.close();
            queryStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultingBrand;
    }

    public String createFinalQueryForIndex(List<Integer> productIDS){
        String part1FinalQuery = "SELECT brandId FROM TokenBrandMap WHERE tokenId = ?";
        String part2FinalQuery = "GROUP BY brandId HAVING COUNT (brandId) = ?";

        for (int i = 1; i < productIDS.size(); i++) {
            part1FinalQuery += " OR tokenId = ? ";
        }

        return part1FinalQuery + part2FinalQuery;
    }

    public List<Integer> getTokenIDS(List<String> tokens){
        List<Integer> tempList = new ArrayList<>();

        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryStatement = null;
            ResultSet queryResultSet = null;
            for (int i = 0; i < tokens.size(); i++) {
                queryStatement = connection.prepareStatement("SELECT id FROM tokens WHERE token = ?");

                queryStatement.setString(1, tokens.get(i));

                queryResultSet = queryStatement.executeQuery();

                while(queryResultSet.next()) {
                    tempList.add(queryResultSet.getInt("id"));
                }
            }
            queryResultSet.close();
            queryStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempList;
    }


    @Override
    public void indexBrandInformation(Brand brand, List<String> tokens) {
        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement mapInsert = connection.prepareStatement("INSERT INTO tokenbrandmap(brandid, tokenid) VALUES (?,?)");
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

