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

import static org.junit.jupiter.api.Assertions.*;

class BrandIndexTest {

    private Connection DBConn = DBConnection.getConnection();

    @Test
    void searchBrandIndex() {
        Brand knownBand = new Brand(0, null, null, null, null, null);
        knownBand.setName("HP");
        knownBand.setDescription("HP Inc. is an American multinational information technology company headquartered in Palo Alto, California, that develops personal computers (PCs), printers and related supplies, as well as 3D printing solutions.");
        knownBand.setFounded("January 1, 1939");
        knownBand.setHeadquarters("Palo Alto, California, United States");

        String[] Products = {"Personal computers", "printers", "digital press", "3D printers", "scanners", "copiers", "monitors"};
        ArrayList<String> tempList = new ArrayList<>();
        tempList.addAll(List.of(Products));
        knownBand.setProducts(tempList);


        JsonService s = new JsonService();

        System.out.println("test HP");

        int indexNumber = 4;
        assertEquals(knownBand.getName(), s.deserializeBrand().get(indexNumber).getName());
        assertEquals(knownBand.getDescription(), s.deserializeBrand().get(indexNumber).getDescription());
        assertEquals(knownBand.getFounded(), s.deserializeBrand().get(indexNumber).getFounded());
        assertEquals(knownBand.getHeadquarters(), s.deserializeBrand().get(indexNumber).getHeadquarters());
        for (int i = 0; i < knownBand.getProducts().size(); i++) {
            assertEquals(knownBand.getProducts().get(i), s.deserializeBrand().get(indexNumber).getProducts().get(i));
            //System.out.println(s.deserializeBrand().get(i).getName());
        }
    }

    @Test
    void indexBrandInformation() {
        //creating brand object -----------
        Brand brand = new Brand(1, null, null, null, null , null);
        brand.setName("Apple");
        brand.setDescription("Apple Inc (Apple) designs, manufactures, and markets smartphones, tablets, personal computers (PCs), portable and wearable devices. The company also offers software and related services, accessories, networking solutions, and third-party digital content and applications.");
        brand.setFounded("April 1, 1976");
        brand.setHeadquarters("Cupertino, California, USA");
        String[] Products = {"Personal computers", "Smartphones", "Smartwatches", "Earbuds", "Headphones"};
        ArrayList<String> tempList = new ArrayList<>();
        tempList.addAll(List.of(Products));
        brand.setProducts(tempList);

        //tokens ----------
        List<String> firstTokens = List.of("Quality", "Phones", "Computers", "Watches");
        List<String> duplicateTokens = List.of("Phones", "Tv-Controller", "Computers");

        //creating instance of BrandIndex class -----------------
        BrandIndex index = new BrandIndex();

        //Inserting brand into DB ----------------
        try {
            PreparedStatement ps1 = DBConn.prepareStatement("INSERT INTO brand(name,description,founded,headquarters) VALUES (?,?,?,?)");
            ps1.setString(1, brand.getName());
            ps1.setString(2, brand.getDescription());
            ps1.setString(3, brand.getFounded());
            ps1.setString(4, brand.getHeadquarters());
            ps1.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        //Calling indexBrandInformation with brand and tokens --------
        index.indexBrandInformation(brand, firstTokens);

        //Querying DB and checking if the tokens in the DB is equal to the firstTokens list -----------
        try {
            PreparedStatement query = DBConn.prepareStatement("SELECT token FROM tokens");
            ResultSet queryRs = query.executeQuery();
            for (int i = 0; i < query.getFetchSize(); i++){
                while (queryRs.next()){
                    assertEquals(firstTokens.get(i), queryRs.getString(1));
                    System.out.println("Checking if "+firstTokens.get(i)+" and "+queryRs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        index.indexBrandInformation(brand, duplicateTokens);

        //Checking if the code doesn't add duplicates and adds non-duplicate tokens -----------
        try {
            PreparedStatement query = DBConn.prepareStatement("SELECT token FROM tokens");
            ResultSet queryRs = query.executeQuery();
            for (int i = 0; i < query.getFetchSize(); i++){
                while (queryRs.next()){
                    assertEquals(duplicateTokens.get(i), queryRs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
