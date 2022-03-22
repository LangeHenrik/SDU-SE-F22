package dk.sdu.se_f22.sharedlibrary.db;

import org.flywaydb.core.Flyway;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBMigration {
    private String url;
    private String user;
    private String password;

    public static void main(String[] args) {
        DBMigration migration = new DBMigration();
        migration.migrate();
    }

    public void migrate() {
        this.loadConfig();
        Flyway flyway = Flyway.configure().dataSource(this.url, this.user, this.password).schemas("public").load();
        flyway.migrate();
    }

    private void loadConfig() {
        try (InputStream inputStream = new FileInputStream("config.properties")) {
            Properties props = new Properties();
            props.load(inputStream);

            this.url = props.getProperty("db_url");
            this.user = props.getProperty("db_user");
            this.password = props.getProperty("db_password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
