package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.brandmodule.management.services.JsonService;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BrandIndexTest {

    @Test
    void searchBrandIndex() throws SQLException {
        Brand knownBrand = new Brand(0, null, null, null, null, null);
        knownBrand.setName("HP");
        knownBrand.setDescription("HP Inc. is an American multinational information technology company headquartered in Palo Alto, California, that develops personal computers (PCs), printers and related supplies, as well as 3D printing solutions.");
        knownBrand.setFounded("January 1, 1939");
        knownBrand.setHeadquarters("Palo Alto, California, United States");

        String[] Products = {"Personal computers", "printers", "digital press", "3D printers", "scanners", "copiers", "monitors"};
        ArrayList<String> tempList = new ArrayList<>();
        tempList.addAll(List.of(Products));
        knownBrand.setProducts(tempList);

        try (Connection connection = DBConnection.getPooledConnection()){
            PreparedStatement brandInsert = connection.prepareStatement("INSERT INTO brand (name, description, founded, headquarters) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING");
            brandInsert.setString(1, knownBrand.getName());
            brandInsert.setString(2, knownBrand.getDescription());
            brandInsert.setString(3, knownBrand.getFounded());
            brandInsert.setString(4, knownBrand.getHeadquarters());
            brandInsert.execute();

            PreparedStatement prod1 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod1.setString(1, Products[0]);
            prod1.execute();

            PreparedStatement prod2 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod2.setString(1, Products[1]);
            prod2.execute();

            PreparedStatement prod3 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod3.setString(1, Products[2]);
            prod3.execute();

            PreparedStatement prod4 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod4.setString(1, Products[3]);
            prod4.execute();

            PreparedStatement prod5 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod5.setString(1, Products[4]);
            prod5.execute();

            PreparedStatement prod6 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod6.setString(1, Products[5]);
            prod6.execute();

            PreparedStatement prod7 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod7.setString(1, Products[6]);
            prod7.execute();

            PreparedStatement TBM1 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM1.setInt(1, 1);
            TBM1.setInt(2, 1);
            TBM1.execute();

            PreparedStatement TBM2 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM2.setInt(1, 1);
            TBM2.setInt(2, 2);
            TBM2.execute();

            PreparedStatement TBM3 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM3.setInt(1, 1);
            TBM3.setInt(2, 3);
            TBM3.execute();

            PreparedStatement TBM4 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM4.setInt(1, 1);
            TBM4.setInt(2, 4);
            TBM4.execute();

            PreparedStatement TBM5 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM5.setInt(1, 1);
            TBM5.setInt(2, 5);
            TBM5.execute();

            PreparedStatement TBM6 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM6.setInt(1, 1);
            TBM6.setInt(2, 6);
            TBM6.execute();

            PreparedStatement TBM7 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM7.setInt(1, 1);
            TBM7.setInt(2, 7);
            TBM7.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        BrandIndex brandIndex = new BrandIndex();
        System.out.println("***");
        System.out.println(brandIndex.searchBrandIndex(tempList));
        System.out.println("***");
        System.out.println(knownBrand);
        System.out.println("***");

    }


    @Test
    void indexBrandInformation() {

    }
}
