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

    private Connection DBConn = DBConnection.getConnection();

    @Test
    void searchBrandIndex() throws SQLException {
        Brand knownBrand = new Brand(1, null, null, null, null, null);
        knownBrand.setName("HP");
        knownBrand.setDescription(
                "HP Inc. is an American multinational information technology company headquartered in Palo Alto, California, that develops personal computers (PCs), printers and related supplies, as well as 3D printing solutions.");
        knownBrand.setFounded("January 1, 1939");
        knownBrand.setHeadquarters("Palo Alto, California, United States");
        knownBrand.setProducts(new ArrayList<>());

        String[] tokens = { "Personal computers", "printers", "digital press", "3D printers", "scanners", "copiers",
                "monitors" };
        ArrayList<String> tempList = new ArrayList<>();
        tempList.addAll(List.of(tokens));
        // knownBrand.setProducts(tempList);

        try (Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement brandInsert = connection.prepareStatement(
                    "INSERT INTO brand (name, description, founded, headquarters) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING");
            brandInsert.setString(1, knownBrand.getName());
            brandInsert.setString(2, knownBrand.getDescription());
            brandInsert.setString(3, knownBrand.getFounded());
            brandInsert.setString(4, knownBrand.getHeadquarters());
            brandInsert.execute();

            PreparedStatement prod1 = connection
                    .prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod1.setString(1, tokens[0]);
            prod1.execute();
            prod1.close();

            PreparedStatement prod2 = connection
                    .prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod2.setString(1, tokens[1]);
            prod2.execute();
            prod2.close();

            PreparedStatement prod3 = connection
                    .prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod3.setString(1, tokens[2]);
            prod3.execute();
            prod3.close();

            PreparedStatement prod4 = connection
                    .prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod4.setString(1, tokens[3]);
            prod4.execute();
            prod4.close();

            PreparedStatement prod5 = connection
                    .prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod5.setString(1, tokens[4]);
            prod5.execute();
            prod5.close();

            PreparedStatement prod6 = connection
                    .prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod6.setString(1, tokens[5]);
            prod6.execute();
            prod6.close();

            PreparedStatement prod7 = connection
                    .prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod7.setString(1, tokens[6]);
            prod7.execute();
            prod7.close();

            PreparedStatement TBM1 = connection.prepareStatement(
                    "INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM1.setInt(1, 1);
            TBM1.setInt(2, 1);
            TBM1.execute();
            TBM1.close();

            PreparedStatement TBM2 = connection.prepareStatement(
                    "INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM2.setInt(1, 1);
            TBM2.setInt(2, 2);
            TBM2.execute();
            TBM2.close();

            PreparedStatement TBM3 = connection.prepareStatement(
                    "INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM3.setInt(1, 1);
            TBM3.setInt(2, 3);
            TBM3.execute();
            TBM3.close();

            PreparedStatement TBM4 = connection.prepareStatement(
                    "INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM4.setInt(1, 1);
            TBM4.setInt(2, 4);
            TBM4.execute();
            TBM4.close();

            PreparedStatement TBM5 = connection.prepareStatement(
                    "INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM5.setInt(1, 1);
            TBM5.setInt(2, 5);
            TBM5.execute();
            TBM5.close();

            PreparedStatement TBM6 = connection.prepareStatement(
                    "INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM6.setInt(1, 1);
            TBM6.setInt(2, 6);
            TBM6.execute();
            TBM6.close();

            PreparedStatement TBM7 = connection.prepareStatement(
                    "INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM7.setInt(1, 1);
            TBM7.setInt(2, 7);
            TBM7.execute();
            TBM7.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        BrandIndex brandIndex = new BrandIndex();
        List<Brand> resultSearchList = brandIndex.searchBrandIndex(tempList);
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
        Brand brand = new Brand(1, null, null, null, null, null);
        brand.setName("Apple");
        brand.setDescription(
                "Apple Inc (Apple) designs, manufactures, and markets smartphones, tablets, personal computers (PCs), portable and wearable devices. The company also offers software and related services, accessories, networking solutions, and third-party digital content and applications.");
        brand.setFounded("April 1, 1976");
        brand.setHeadquarters("Cupertino, California, USA");
        String[] Products = { "Personal computers", "Smartphones", "Smartwatches", "Earbuds", "Headphones" };
        ArrayList<String> tempList = new ArrayList<>();
        tempList.addAll(List.of(Products));
        brand.setProducts(tempList);

        // Creating the different List<String> with the tokens for the
        // indexBrandInformation method
        List<String> firstTokens = List.of("Quality", "Phones", "Computers", "Watches");
        List<String> duplicateTokens = List.of("Phones", "Tv-Controller", "Computers");
        List<String> finalTokens = List.of("Quality", "Phones", "Computers", "Watches", "Tv-Controller");

        // Creating instance of BrandIndex class
        BrandIndex index = new BrandIndex();

        // Inserting brand into DB
        try {
            PreparedStatement ps1 = DBConn.prepareStatement(
                    "INSERT INTO brand(name,description,founded,headquarters) VALUES (?,?,?,?) ON CONFLICT DO NOTHING");
            ps1.setString(1, brand.getName());
            ps1.setString(2, brand.getDescription());
            ps1.setString(3, brand.getFounded());
            ps1.setString(4, brand.getHeadquarters());
            ps1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calling indexBrandInformation with brand and tokens
        index.indexBrandInformation(brand, firstTokens);
        index.indexBrandInformation(brand, duplicateTokens);

        // Checking if the code has correctly added the tokens in the ArrayLists above,
        // which means adding new tokens
        // and not adding any duplicates.
        try {
            PreparedStatement query = DBConn.prepareStatement("SELECT token FROM tokens WHERE id=?");
            ResultSet queryRs = null;
            for (int i = 0; i < finalTokens.size(); i++) {
                query.setInt(1, i + 8);
                queryRs = query.executeQuery();
                if (queryRs.next()) {
                    assertEquals(finalTokens.get(i), queryRs.getString(1));
                }
            }
            query.close();
            queryRs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void tokenBrandMapInsert() {
        try {
            // Checking if the tokens are inserted properly into the tokenBrandMap and
            // associated with the right brand.
            PreparedStatement query = DBConn
                    .prepareStatement("SELECT tokenid FROM tokenbrandmap WHERE brandid=1 AND id=?");
            ResultSet queryRs = null;
            for (int i = 0; i < 5; i++) {
                query.setInt(1, i);
                queryRs = query.executeQuery();
                if (queryRs.next()) {
                    assertEquals(i, queryRs.getInt(1));
                }
            }
            query.close();
            queryRs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
