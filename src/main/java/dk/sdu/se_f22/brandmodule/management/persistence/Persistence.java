package dk.sdu.se_f22.brandmodule.management.persistence;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.Connection;
import java.sql.DriverManager;

public class Persistence implements IPersistence {
    private Connection c = null;

    public Persistence() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/semesterprojekt",
                            "postgres", "new_password");
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }

        // Make sure tables are always created
        setupDatabase();
    }

    @Override
    public void createBrand(Brand brand) {
        try {
            var stmt = c.createStatement();

            var insert =  "INSERT INTO brand (name, description , founded, headquarters)" +
                    String.format("VALUES (%s, %s, %s,%s, %s);", brand.name, brand.description, brand.founded, brand.headquarters);

            for (var product : brand.products) {
                stmt.addBatch("INSERT INTO Product VALUES()");
            }

            stmt.executeUpdate(insert);
            stmt.close();
            c.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Brand getBrand(int id) {
        return null;
    }

    @Override
    public void deleteBrand(int id) {

    }

    @Override
    public void updateBrand(int id, Brand brand) {

    }

    @Override
    public void setupDatabase() {
        try {
            var stmt = c.createStatement();

            var brandTableSql =  "CREATE TABLE IF NOT EXISTS Brand " +
                    "(id serial PRIMARY KEY," +
                    "name VARCHAR(255) UNIQUE NOT NULL, " +
                    "description VARCHAR(10000), " +
                    "founded VARCHAR(255), " +
                    "headquarters VARCHAR(255));";

            var productTableSql = "CREATE TABLE IF NOT EXISTS Product(" +
                    "id serial PRIMARY KEY," +
                    "name VARCHAR(255) UNIQUE NOT NULL);";

            var brandProductTableSql = "CREATE TABLE IF NOT EXISTS BrandProduct(" +
                    "Brandid INTEGER REFERENCES Brand(id)," +
                    "Productid INTEGER REFERENCES Product(id));";

            var configTableSql = "CREATE TABLE IF NOT EXISTS config(" +
                    "indexinterval INTEGER NOT NULL" +
                    ");";

            var sql = brandTableSql +
                    productTableSql +
                    brandProductTableSql +
                    configTableSql;

            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}