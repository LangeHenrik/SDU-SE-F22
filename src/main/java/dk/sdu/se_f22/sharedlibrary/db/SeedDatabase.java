package dk.sdu.se_f22.sharedlibrary.db;

import dk.sdu.se_f22.brandmodule.management.persistence.IPersistence;
import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SeedDatabase {
    public static void main(String[] args) {
        // BIM 1
        IPersistence BIMPersistence = new Persistence();
        BIMPersistence.seedDatabase();

        // BIM 2....
    }
}
