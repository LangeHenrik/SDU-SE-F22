package dk.sdu.se_f22;

import dk.sdu.se_f22.brandmodule.management.Bim;
import dk.sdu.se_f22.brandmodule.management.services.IndexingService;
import dk.sdu.se_f22.sharedlibrary.db.DBMigration;
import dk.sdu.se_f22.sharedlibrary.db.MigrationException;

import java.security.Provider;


public class Main {
    public static void main(String[] args) throws MigrationException {
        Bim b = new Bim();
        b.seedDatabase();
    }
}
