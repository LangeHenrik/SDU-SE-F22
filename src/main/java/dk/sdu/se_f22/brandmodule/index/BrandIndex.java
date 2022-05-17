package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BrandIndex implements IndexInterface {

    @Override
    public List<Brand> searchBrandIndex(List<String> tokens) {
        var DBConn = DBConnection.getConnection();
        Map<Integer, Brand> brandList = new HashMap<>();
        String finalQuery = "SELECT * FROM TokenBrandMap " +
                "LEFT JOIN Brand ON Brand.id = brandId " +
                "LEFT JOIN tokens on tokens.id = tokenbrandmap.tokenid " +
                "WHERE token = ?";

        try {
            for (int i = 0; i < tokens.size() - 1; i++) {
                finalQuery += " OR token = ? ";
            }

            PreparedStatement queryStatement = DBConn.prepareStatement(finalQuery);

            for (int i = 0; i < tokens.size(); i++) {
                queryStatement.setString(i + 1, tokens.get(i));
            }

            ResultSet queryResultSet = queryStatement.executeQuery();

            while (queryResultSet.next()) {
                brandList.put(Integer.valueOf(queryResultSet.getString("brandId")) ,new Brand(Integer.valueOf(queryResultSet.getString("brandId")),
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

        return brandList.values().stream().toList();
    }

    private Map<Integer, String> getAllTokens() throws SQLException {
        try(var connection = DBConnection.getPooledConnection();
            var stmt = connection.prepareStatement("SELECT * FROM tokens");
            var rs = stmt.executeQuery()) {
            Map<Integer, String> existingTokens = new HashMap<>();
            while (rs.next()) {
                existingTokens.put(rs.getInt("id"), rs.getString("token"));
            }
            return existingTokens;
        }
    }

    private Map<Integer, List<Integer>> getAllTokenBrandMaps() throws SQLException {
        try(var connection = DBConnection.getPooledConnection();
            var stmt = connection.prepareStatement("SELECT * FROM tokenbrandmap");
            var rs = stmt.executeQuery()) {
            Map<Integer, List<Integer>> existingTokenBrandMap = new HashMap<>();
            while (rs.next()) {
                if(!existingTokenBrandMap.containsKey(rs.getInt("brandid"))) {
                    existingTokenBrandMap.put(rs.getInt("brandid"), new ArrayList<>(
                         List.of(rs.getInt("tokenid"))
                    ));
                } else {
                    existingTokenBrandMap.get(rs.getInt("brandid")).add(rs.getInt("tokenid"));

                }
            }
            return existingTokenBrandMap;
        }
    }

    @Override
    public void indexBrandInformation(Brand brand, List<String> tokens) {
        try(var connection = DBConnection.getPooledConnection()) {
            PreparedStatement mapInsert = connection.prepareStatement("INSERT INTO tokenbrandmap(brandid, tokenid) VALUES (?,?)");
            PreparedStatement tokenInsert = connection.prepareStatement("INSERT INTO tokens(token) VALUES (?)");

            var existingTokens = getAllTokens();
            var existingTokenBrandMap = getAllTokenBrandMaps();

            for (int i = 0; i < tokens.size(); i++) {
                if (!existingTokens.containsValue(tokens.get(i))) {
                    tokenInsert.setString(1, tokens.get(i));
                    tokenInsert.execute();
                }

                var queryTokenId = connection.prepareStatement("SELECT id FROM tokens where token = ?");
                queryTokenId.setString(1, tokens.get(i));
                var rsTokenId = queryTokenId.executeQuery();

                while (rsTokenId.next()) {
                    if(existingTokenBrandMap.containsKey(brand.getId()) && existingTokenBrandMap.get(brand.getId()).contains(rsTokenId.getInt(1)))
                        continue;
                    mapInsert.setInt(1, brand.getId());
                    mapInsert.setInt(2, rsTokenId.getInt(1));
                    mapInsert.execute();
                }

                queryTokenId.close();
                rsTokenId.close();
            }
            //preparedStatements
            mapInsert.close();
            tokenInsert.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

