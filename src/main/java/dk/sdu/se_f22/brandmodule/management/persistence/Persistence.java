package dk.sdu.se_f22.brandmodule.management.persistence;
import dk.sdu.se_f22.brandmodule.management.services.IJsonService;
import dk.sdu.se_f22.brandmodule.management.services.JsonService;
import dk.sdu.se_f22.sharedlibrary.models.Brand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Persistence implements IPersistence {
    private Connection c = null;
    private IJsonService jsonService = null;

    public Persistence() {
        jsonService = new JsonService();

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

        // Seed database
        seedDatabase();
    }

    @Override
    public Brand getBrand(int id) {
        return null;
    }

    @Override
    public Brand getBrand(Brand brand) {
        return null;
    }

    @Override
    public void deleteBrand(int id) {

    }

    @Override
    public void deleteBrand(Brand brand) {
        try {
            var stmt = c.createStatement();

            // Delete from both Brand and Junction table
            // Products may be used by another brand, so it won't be deleted
            stmt.addBatch(String.format("DELETE FROM Brand where id = '%s'", brand.getId()));
            stmt.addBatch(String.format("DELETE FROM BrandProductTypeJunction where id = '%s'", brand.getId()));

            stmt.executeBatch();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addOrUpdateBrands(List<Brand> brands) {
        try {
            /* ------ Insert all products ------ */
            var insertStmt = c.createStatement();

            // Keep no duplicate products
            Set<String> products = new HashSet<>();
            for (var brand : brands) {
                products.addAll(brand.getProducts());
            }

            // Insert products into database
            for (var product : products) {
                insertStmt.addBatch(String.format("INSERT INTO ProductType (name) VALUES ('%s') ON CONFLICT DO NOTHING;", product));
            }

            /* ------ Insert all brands ------ */
            for (var brand : brands) {
                insertStmt.addBatch(String.format("INSERT INTO Brand (name, description, founded, headquarters) VALUES ('%s','%s','%s','%s') ON CONFLICT DO NOTHING;",
                        brand.getName(),
                        brand.getDescription().replaceAll("'", "''"),
                        brand.getFounded(),
                        brand.getHeadquarters()));
            }

            // Run all insert statements
            insertStmt.executeBatch();

            /* ------ Insert into junction table ------ */
            var fetchStmt = c.createStatement();

            Integer brandId = null;
            Integer productId = null;
            for (var brand : brands) {
                ResultSet brandResult = fetchStmt.executeQuery(String.format("SELECT id FROM Brand WHERE name = '%s';", brand.getName()));

                // Get brand id, for junction table
                if (brandResult.next()) {
                    brandId = brandResult.getInt("id");
                }

                // Get product id, for junction table
                for (var product : products) {
                    ResultSet productResult = fetchStmt.executeQuery(String.format("SELECT id FROM ProductType WHERE name = '%s';", product));
                    if (productResult.next()) {
                        productId = productResult.getInt("id");

                        // With both brand and product id, junction table can be filled
                        insertStmt.addBatch(String.format("INSERT INTO BrandProductTypeJunction (Brandid, Productid) values ('%s', '%s')", brandId, productId));
                    }
                }
            }

            // Insert into junction table, and close statements
            insertStmt.executeBatch();
            insertStmt.close();
            fetchStmt.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void setupDatabase() {
        try {
            var stmt = c.createStatement();

            var brandTableSql =  "CREATE TABLE IF NOT EXISTS Brand " +
                    "(id serial PRIMARY KEY," +
                    "name VARCHAR(255) UNIQUE NOT NULL, " +
                    "description TEXT, " +
                    "founded VARCHAR(255), " +
                    "headquarters VARCHAR(255));";

            var productTableSql = "CREATE TABLE IF NOT EXISTS ProductType(" +
                    "id serial PRIMARY KEY," +
                    "name VARCHAR(255) UNIQUE NOT NULL);";

            var brandProductTableSql = "CREATE TABLE IF NOT EXISTS BrandProductTypeJunction(" +
                    "Brandid INTEGER REFERENCES Brand(id)," +
                    "Productid INTEGER REFERENCES ProductType(id));";

            var configTableSql = "CREATE TABLE IF NOT EXISTS config(" +
                    "brandindexinterval INTEGER NOT NULL" +
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

    @Override
    public void seedDatabase() {
        // Get brands from file
        List<Brand> brands = jsonService.deserializeBrand();
        addOrUpdateBrands(brands);
    }
}