package dk.sdu.se_f22.Bim.src.persistence;

import models.Brand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Persistence implements IPersistence {

    @Override
    public void createBrand(Brand brand) {

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
        //fill in database name and password
        final String url = "jdbc:postgresql://localhost:5432/Database_Name"; //
        final String user = "postgres";
        final String password = "";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Successfully connected to brand test database.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to brand test database.");
            e.printStackTrace();
        }
    }

    // Testing connection to Postgres database
    public static void main(String[] args) {
        Persistence persistence = new Persistence();
        persistence.setupDatabase();
    }
}
