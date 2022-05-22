package dk.sdu.se_f22;

import dk.sdu.se_f22.brandmodule.management.services.IndexingService;
import dk.sdu.se_f22.sharedlibrary.db.DBMigration;

import java.security.Provider;


public class Main {
    public static void main(String[] args) {

        DBMigration db = new DBMigration();
        db.migrate();
    }
}
