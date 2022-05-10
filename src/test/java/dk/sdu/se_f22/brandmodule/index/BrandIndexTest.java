package dk.sdu.se_f22.brandmodule.index;

import dk.sdu.se_f22.brandmodule.management.services.JsonService;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BrandIndexTest {

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

    }
}
