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
        if (tokens.size() == 0)
            return brandList;

        List<Integer> tokenIDList = getTokenIDs(tokens);
        String finalQuery = createFinalQueryForIndex(tokenIDList);

        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryStatement = connection.prepareStatement(finalQuery);

            for (int i = 0; i < tokenIDList.size(); i++) {
                queryStatement.setInt(i + 1, tokenIDList.get(i));
            }

            queryStatement.setInt(tokenIDList.size() + 1, tokenIDList.size());

            ResultSet queryResultSet = queryStatement.executeQuery();
            while (queryResultSet.next()) {
                brandList.add(getBrandFromID(queryResultSet.getInt("brandId")));
            }
            queryResultSet.close();
            queryStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brandList;
    }

    public Brand getBrandFromID(int id){
        // Queries the database using a brand ID to get the whole brand
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
        // Creates the final query statement based on the amount of tokens
        String finalQueryPart1 = "SELECT brandId FROM TokenBrandMap WHERE tokenId = ?";
        String finalQueryPart2 = "GROUP BY brandId HAVING COUNT (brandId) = ?";

        for (int i = 1; i < productIDS.size(); i++) {
            finalQueryPart1 += " OR tokenId = ? ";
        }

        return finalQueryPart1 + finalQueryPart2;
    }

    public List<Integer> getTokenIDs(List<String> tokens){
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
        if (tokens.size() == 0 || brand == null || brand.getName() == null)
            return;

        tokens = new ArrayList<>(tokens);
        insertTokens(tokens);

        List<Integer> tokenIDs = getTokenIDs(tokens);
        mapTokenBrandRelations(brand.getId(), tokenIDs);
    }

    public void mapTokenBrandRelations(int brandID, List<Integer> tokenIDs){
        try (Connection DBConn = DBConnection.getPooledConnection()) {
            PreparedStatement mapInsert = DBConn.prepareStatement("INSERT INTO tokenbrandmap(brandid, tokenid) VALUES (?,?) ON CONFLICT DO NOTHING");

            // Inserts into tokenBrandMap, making sure each token points to its specific brand and inserts all tokens to tokens table
            for (int i = 0; i < tokenIDs.size(); i++) {
                mapInsert.setInt(1, brandID);
                mapInsert.setInt(2, tokenIDs.get(i));
                mapInsert.execute();
            }
            // Closing preparedStatements
            mapInsert.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void insertTokens(List<String> tokens){
        try (Connection DBConn = DBConnection.getPooledConnection()) {
            PreparedStatement tokenInsert = DBConn.prepareStatement("INSERT INTO tokens(token) VALUES (?) ON CONFLICT DO NOTHING");

            // Inserts into tokenBrandMap
            for (int i = 0; i < tokens.size(); i++) {
                tokenInsert.setString(1, tokens.get(i));
                tokenInsert.execute();
            }
            // Closing preparedStatements
            tokenInsert.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
