package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.brandmodule.management.services.JsonService;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BrandIndexTest {

    @Test
    void searchBrandIndex() throws SQLException {
        Brand knownBrand = new Brand(null, null, null, null, null, null);
        knownBrand.setName("HP");
        knownBrand.setDescription("HP Inc. is an American multinational information technology company headquartered in Palo Alto, California, that develops personal computers (PCs), printers and related supplies, as well as 3D printing solutions.");
        knownBrand.setFounded("January 1, 1939");
        knownBrand.setHeadquarters("Palo Alto, California, United States");
        knownBrand.setProducts(new ArrayList<>());

        String[] tokens = {"Personal computers", "printers", "digital press", "3D printers", "scanners", "copiers", "monitors"};
        ArrayList<String> tokenList = new ArrayList<>();
        tokenList.addAll(List.of(tokens));

        // Inserting brand, then extracting and setting brand ID
        try (Connection connection = DBConnection.getPooledConnection()){
            PreparedStatement brandInsert = connection.prepareStatement("INSERT INTO brand (name, description, founded, headquarters) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING");
            brandInsert.setString(1, knownBrand.getName());
            brandInsert.setString(2, knownBrand.getDescription());
            brandInsert.setString(3, knownBrand.getFounded());
            brandInsert.setString(4, knownBrand.getHeadquarters());
            brandInsert.execute();

            PreparedStatement queryBrandId = connection.prepareStatement("SELECT id FROM Brand WHERE name = ?");
            queryBrandId.setString(1, knownBrand.getName());
            ResultSet queryResultSet = queryBrandId.executeQuery();
            queryResultSet.next();
            knownBrand.setId(queryResultSet.getInt(1));
            queryBrandId.execute();
            queryBrandId.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Inserting tokens and relations
        try (Connection connection = DBConnection.getPooledConnection()){
            PreparedStatement mapInsert = connection.prepareStatement("INSERT INTO tokenbrandmap(brandid, tokenid) VALUES (?,?) ON CONFLICT DO NOTHING");
            PreparedStatement tokenInsert = connection.prepareStatement("INSERT INTO tokens(token) VALUES (?) ON CONFLICT DO NOTHING");
            PreparedStatement queryTokenId = connection.prepareStatement("SELECT id FROM tokens where token = ?");

            ResultSet resultSetTokenId = null;

            // Inserts into tokenBrandMap, making sure each token points to its specific brand and inserts all tokens to tokens table
            if (tokenList.size() > 0) {
                for (int i = 0; i < tokenList.size(); i++) {
                    tokenInsert.setString(1, tokenList.get(i));
                    tokenInsert.execute();
                    queryTokenId.setString(1, tokenList.get(i));
                    resultSetTokenId = queryTokenId.executeQuery();
                    while (resultSetTokenId.next()) {
                        mapInsert.setInt(1, knownBrand.getId());
                        mapInsert.setInt(2, resultSetTokenId.getInt(1));
                        mapInsert.execute(); }
                }
            }
            // Closing preparedStatements
            mapInsert.close();
            tokenInsert.close();
            queryTokenId.close();

            // Closing resultSets
            resultSetTokenId.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Use and test the method
        BrandIndex brandIndex = new BrandIndex();
        List<Brand> resultSearchList = brandIndex.searchBrandIndex(tokenList);
        Brand searchResultBrand = null;

        for (int i = 0; i < resultSearchList.size(); i++) {
            if (resultSearchList.get(i).equals(knownBrand)) {
                searchResultBrand = resultSearchList.get(i);
                break;
            }
        }
        assertEquals(searchResultBrand, knownBrand);
    }

    @Test
    void indexBrandInformation() {
        // Creating the brand object for the indexBrandInformation method
        Brand brand = new Brand(null, null, null, null, null , null);
        brand.setName("Apple");
        brand.setDescription("Apple Inc (Apple) designs, manufactures, and markets smartphones, tablets, personal computers (PCs), portable and wearable devices. The company also offers software and related services, accessories, networking solutions, and third-party digital content and applications.");
        brand.setFounded("April 1, 1976");
        brand.setHeadquarters("Cupertino, California, USA");
        brand.setProducts(new ArrayList<>());

        // Creating the different List<String> with the tokens for the indexBrandInformation method
        List<String> tokens = List.of("Quality", "Phones", "Computers", "Watches");
        List<Integer> tokenIds = new ArrayList<>();

        // Creating instance of BrandIndex class
        BrandIndex index = new BrandIndex();

        // Inserting brand into DB
        try (Connection connection = DBConnection.getPooledConnection()){
            PreparedStatement brandInsert = connection.prepareStatement("INSERT INTO brand(name,description,founded,headquarters) VALUES (?,?,?,?) ON CONFLICT DO NOTHING");
            brandInsert.setString(1, brand.getName());
            brandInsert.setString(2, brand.getDescription());
            brandInsert.setString(3, brand.getFounded());
            brandInsert.setString(4, brand.getHeadquarters());
            brandInsert.execute();

            brandInsert.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Extracting and setting brand ID
        try (Connection connection = DBConnection.getPooledConnection()){
            PreparedStatement queryBrandId = connection.prepareStatement("SELECT id FROM Brand WHERE name = ?");
            queryBrandId.setString(1, brand.getName());
            ResultSet queryResultSet = queryBrandId.executeQuery();
            queryResultSet.next();
            brand.setId(queryResultSet.getInt(1));
            queryBrandId.execute();
            queryBrandId.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Calling indexBrandInformation with brand and tokens
        index.indexBrandInformation(brand, tokens);

        // Checking if the code has correctly added the tokens in the ArrayLists above, which means adding new tokens
        try (Connection connection = DBConnection.getPooledConnection()){
            PreparedStatement query = connection.prepareStatement("SELECT * FROM tokens WHERE token=?");
            ResultSet queryResultSet = null;
            for (int i = 0; i < tokens.size(); i++){
                query.setString(1, tokens.get(i));
                queryResultSet = query.executeQuery();
                if (queryResultSet.next()) {
                    assertEquals(tokens.get(i), queryResultSet.getString(2));
                    tokenIds.add(queryResultSet.getInt(1));
                }
            }
            query.close();
            queryResultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DBConnection.getPooledConnection()){
            // Checking if the tokens are inserted properly into the tokenBrandMap and associated with the right brand.
            PreparedStatement query = connection.prepareStatement("SELECT tokenid FROM TokenBrandMap WHERE brandid=(SELECT id FROM Brand WHERE name=?) AND tokenid=(SELECT id FROM tokens WHERE token=?)");
            query.setString(1, brand.getName());
            ResultSet queryResultSet = null;
            for (int i = 0; i < tokens.size(); i++){
                query.setString(2, tokens.get(i));
                queryResultSet = query.executeQuery();
                if (queryResultSet.next()){
                    assertEquals(tokenIds.get(i), queryResultSet.getInt(1));
                }
            }
            query.close();
            queryResultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
