package dk.sdu.se_f22.brandmodule.management.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTest {
    private IPersistence p;

    @BeforeEach
    void setup() {
        p = new Persistence();
    }

    @Test
    void getAllBrands() {
        var allBrands = p.getAllBrands();

        // Assert: Check the list contains brands
    }

    @Test
    void getBrandByName() {
        var allBrands = p.getAllBrands();

        // Get first element from all brands list

        // Get brand by name using name of the first element from the list

        // Assert: Check the object properties are "valid"
    }

    @Test
    void getBrandById() {
        // Get first element from all brands list

        // Get brand by name using id of the first element from the list

        // Assert: Check the object properties are "valid"
    }

}
