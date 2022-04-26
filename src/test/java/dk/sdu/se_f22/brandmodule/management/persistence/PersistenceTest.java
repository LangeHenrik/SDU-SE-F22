package dk.sdu.se_f22.brandmodule.management.persistence;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTest {
    private IPersistence p;

    @BeforeEach
    void setup() {
        p = new Persistence();
    }

    @Test
    void getBrandByName() {
        // Assert: Insert entry into database, and fetch it back
        var brandsToInsert = new ArrayList<Brand>();

        // Insert a brand
        Brand brandToInsert = new Brand("TestProduct", "TestDescription", "TestFounded", "TestHeadquarters");
        brandsToInsert.add(brandToInsert);
        p.addOrUpdateBrands(brandsToInsert);

        // Get fetch it back
        var fetchedBrand = p.getBrand("TestProduct");

        // Delete the brand from the database again
        p.deleteBrand(fetchedBrand);

        // Test before and after brands against each other
        assertEquals(brandsToInsert.get(0).getName(), fetchedBrand.getName());
        assertEquals(brandsToInsert.get(0).getDescription(), fetchedBrand.getDescription());
        assertEquals(brandsToInsert.get(0).getFounded(), fetchedBrand.getFounded());
        assertEquals(brandsToInsert.get(0).getHeadquarters(), fetchedBrand.getHeadquarters());
    }

    @Test
    void deleteBrand() {
        // Assert: Insert and delete a brand. Check if it's gone
        Brand brandToInsert = new Brand("TestProduct", "TestDescription", "TestFounded", "TestHeadquarters");

        // Insert a brand
        var brandsToInsert = new ArrayList<Brand>();
        brandsToInsert.add(brandToInsert);
        p.addOrUpdateBrands(brandsToInsert);

        // Delete brand
        p.deleteBrand(p.getBrand("TestProduct"));

        // Fetch brand to check it's gone
        var fetchedBrand = p.getBrand("TestProduct");

        // Check if it's gone
        assertNull(fetchedBrand);
    }

    @Test
    void addOrUpdateBrands() {
        // Assert: Insert and delete a brand. Check if it's inserted underway
        Brand brandToInsert = new Brand("TestProduct", "TestDescription", "TestFounded", "TestHeadquarters");

        // Insert a brand
        var brandsToInsert = new ArrayList<Brand>();
        brandsToInsert.add(brandToInsert);
        p.addOrUpdateBrands(brandsToInsert);

        // Fetch brand
        var fetchedBrand = p.getBrand("TestProduct");
        assertEquals(fetchedBrand.getName(), "TestProduct");
        assertEquals(fetchedBrand.getDescription(), "TestDescription");
        assertEquals(fetchedBrand.getFounded(), "TestFounded");
        assertEquals(fetchedBrand.getHeadquarters(), "TestHeadquarters");

        // Delete it again
        p.deleteBrand(fetchedBrand);
    }

    @Test
    void getAllBrands() {
        // Assert: Check if getAllBrands returns a list of brands
        var allBrands = p.getAllBrands();

        if (allBrands.size() > 0) {
            assertTrue(allBrands.get(0) instanceof Brand);
        }
        else {
            // List can possibly be empty, so we can't test it
            assertTrue(true);
        }
    }

    @Test
    void databaseIndexer() {
        // Assert: methods returns true if the database is indexed
        assertTrue(p.databaseIndexer());
    }

    @Test
    void getsetIndexingInterval(){
        //Save old indexing interval into a variable
        int OLD_indexing_interval = p.getIndexingInterval();

        //get indexinterval and assert expected
        p.setIndexingInterval(100);
        assertEquals(100, p.getIndexingInterval());

        //Set indexing interval to the same as before the test
        p.setIndexingInterval(OLD_indexing_interval);
    }
}
