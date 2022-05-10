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
            PreparedStatement brandInsert = connection.prepareStatement("INSERT INTO brand (name, description, founded, headquarters) VALUES (?, ?, ?, ?)");
            brandInsert.setString(1, knownBrand.getName());
            brandInsert.setString(2, knownBrand.getDescription());
            brandInsert.setString(3, knownBrand.getFounded());
            brandInsert.setString(4, knownBrand.getHeadquarters());

            PreparedStatement prod1 = connection.prepareStatement("INSERT  INTO producttype (name) VALUES (?)");
            prod1.setString(1, Products[0]);

            PreparedStatement prod2 = connection.prepareStatement("INSERT  INTO producttype (name) VALUES (?)");
            prod2.setString(1, Products[1]);

            PreparedStatement prod3 = connection.prepareStatement("INSERT  INTO producttype (name) VALUES (?)");
            prod3.setString(1, Products[2]);

            PreparedStatement prod4 = connection.prepareStatement("INSERT  INTO producttype (name) VALUES (?)");
            prod4.setString(1, Products[3]);

            PreparedStatement prod5 = connection.prepareStatement("INSERT  INTO producttype (name) VALUES (?)");
            prod5.setString(1, Products[4]);

            PreparedStatement prod6 = connection.prepareStatement("INSERT  INTO producttype (name) VALUES (?)");
            prod6.setString(1, Products[5]);

            PreparedStatement prod7 = connection.prepareStatement("INSERT  INTO producttype (name) VALUES (?)");
            prod7.setString(1, Products[6]);

            PreparedStatement TBM1 = connection.prepareStatement("INSERT INTO tokenbrandmap (brand, product VALUES (?, ?))");
            TBM1.setInt(1, 1);
            TBM1.setInt(2, 1);

            PreparedStatement TBM2 = connection.prepareStatement("INSERT INTO tokenbrandmap (brand, product VALUES (?, ?))");
            TBM2.setInt(1, 1);
            TBM2.setInt(2, 2);

            PreparedStatement TBM3 = connection.prepareStatement("INSERT INTO tokenbrandmap (brand, product VALUES (?, ?))");
            TBM3.setInt(1, 1);
            TBM3.setInt(2, 3);

            PreparedStatement TBM4 = connection.prepareStatement("INSERT INTO tokenbrandmap (brand, product VALUES (?, ?))");
            TBM4.setInt(1, 1);
            TBM4.setInt(2, 4);

            PreparedStatement TBM5 = connection.prepareStatement("INSERT INTO tokenbrandmap (brand, product VALUES (?, ?))");
            TBM5.setInt(1, 1);
            TBM5.setInt(2, 5);

            PreparedStatement TBM6 = connection.prepareStatement("INSERT INTO tokenbrandmap (brand, product VALUES (?, ?))");
            TBM6.setInt(1, 1);
            TBM6.setInt(2, 6);

            PreparedStatement TBM7 = connection.prepareStatement("INSERT INTO tokenbrandmap (brand, product VALUES (?, ?))");
            TBM7.setInt(1, 1);
            TBM7.setInt(2, 7);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    void indexBrandInformation() {

    }
}
