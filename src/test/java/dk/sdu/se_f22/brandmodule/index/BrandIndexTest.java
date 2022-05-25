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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BrandIndexTest {

    @Test
    void searchBrandIndex() throws SQLException {
        Brand knownBrand = new Brand(1, null, null, null, null, null);
        knownBrand.setName("HP");
        knownBrand.setDescription("HP Inc. is an American multinational information technology company headquartered in Palo Alto, California, that develops personal computers (PCs), printers and related supplies, as well as 3D printing solutions.");
        knownBrand.setFounded("January 1, 1939");
        knownBrand.setHeadquarters("Palo Alto, California, United States");
        knownBrand.setProducts(new ArrayList<>());

        String[] tokens = {"Personal computers", "printers", "digital press", "3D printers", "scanners", "copiers", "monitors"};
        ArrayList<String> tempList = new ArrayList<>();
        tempList.addAll(List.of(tokens));
        //knownBrand.setProducts(tempList);

        try (Connection connection = DBConnection.getPooledConnection()){
            PreparedStatement brandInsert = connection.prepareStatement("INSERT INTO brand (name, description, founded, headquarters) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING");
            brandInsert.setString(1, knownBrand.getName());
            brandInsert.setString(2, knownBrand.getDescription());
            brandInsert.setString(3, knownBrand.getFounded());
            brandInsert.setString(4, knownBrand.getHeadquarters());
            brandInsert.execute();

            PreparedStatement prod1 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod1.setString(1, tokens[0]);
            prod1.execute();
            prod1.close();

            PreparedStatement prod2 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod2.setString(1, tokens[1]);
            prod2.execute();
            prod2.close();

            PreparedStatement prod3 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod3.setString(1, tokens[2]);
            prod3.execute();
            prod3.close();

            PreparedStatement prod4 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod4.setString(1, tokens[3]);
            prod4.execute();
            prod4.close();

            PreparedStatement prod5 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod5.setString(1, tokens[4]);
            prod5.execute();
            prod5.close();

            PreparedStatement prod6 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod6.setString(1, tokens[5]);
            prod6.execute();
            prod6.close();

            PreparedStatement prod7 = connection.prepareStatement("INSERT  INTO Tokens (token) VALUES (?) ON CONFLICT DO NOTHING");
            prod7.setString(1, tokens[6]);
            prod7.execute();
            prod7.close();

            PreparedStatement TBM1 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM1.setInt(1, 1);
            TBM1.setInt(2, 1);
            TBM1.execute();
            TBM1.close();

            PreparedStatement TBM2 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM2.setInt(1, 1);
            TBM2.setInt(2, 2);
            TBM2.execute();
            TBM2.close();

            PreparedStatement TBM3 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM3.setInt(1, 1);
            TBM3.setInt(2, 3);
            TBM3.execute();
            TBM3.close();

            PreparedStatement TBM4 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM4.setInt(1, 1);
            TBM4.setInt(2, 4);
            TBM4.execute();
            TBM4.close();

            PreparedStatement TBM5 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM5.setInt(1, 1);
            TBM5.setInt(2, 5);
            TBM5.execute();
            TBM5.close();

            PreparedStatement TBM6 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
            TBM6.setInt(1, 1);
            TBM6.setInt(2, 6);
            TBM6.execute();
            TBM6.close();

            PreparedStatement TBM7 = connection.prepareStatement("INSERT INTO tokenbrandmap (brandid, tokenid) VALUES (?, ?) ON CONFLICT DO NOTHING");
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
            if (resultSearchList.get(i).equals(knownBrand)){
                searchResultBrand = resultSearchList.get(i);
                break;
            }
        }

        assertEquals(searchResultBrand, knownBrand);
    }


    @Test
    void indexBrandInformation() {

    }
}
