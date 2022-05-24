package dk.sdu.se_f22.sharedlibrary.db;

/**
 * Migrate database from a fresh start
 * 
 * @author Mikkel Albrechtsen (The0mikkel)
 */
public class DBMigrationFresh {

    public static void main(String[] args) throws MigrationException {
        DBMigration migrator = new DBMigration();
        migrator.migrateFresh();
    }

}
