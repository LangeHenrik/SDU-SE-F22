package dk.sdu.se_f22;

import dk.sdu.se_f22.brandmodule.management.persistence.IPersistence;
import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // Intentionally left blank, so we can test.
        IPersistence p = new Persistence();
        p.setupDatabase();
    }
}
