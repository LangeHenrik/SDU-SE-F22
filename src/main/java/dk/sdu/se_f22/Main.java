package dk.sdu.se_f22;

import dk.sdu.se_f22.brandmodule.management.persistence.IPersistence;
import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IPersistence p = new Persistence();
        //p.seedDatabase();

        var f = new ArrayList<String>(List.of("F1", "F2", "F3"));
        Brand brandToInsert = new Brand("TestProduct", "TestDescriptionnn", "TestFounded", "TestHeadquarters", f);

        // Insert a brand
        var brandsToInsert = new ArrayList<Brand>();
        brandsToInsert.add(brandToInsert);

        p.addOrUpdateBrands(brandsToInsert);
        p.addOrUpdateBrands(brandsToInsert);
    }
}
