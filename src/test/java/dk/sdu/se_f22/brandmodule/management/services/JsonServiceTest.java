package dk.sdu.se_f22.brandmodule.management.services;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonServiceTest {

    @Test
    void deserializeBrand() {
        Brand known = new Brand();
        known.name = "Acer";
        JsonService s = new JsonService();
        assertEquals(known.name,s.deserializeBrand().get(0).name);

    }
}