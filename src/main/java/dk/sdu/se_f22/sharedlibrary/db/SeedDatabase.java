package dk.sdu.se_f22.sharedlibrary.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SeedDatabase {
    public static void main(String[] args) {

        Connection c = DBConnection.getConnection();

        try {
            Statement statement = c.createStatement();
            statement.execute("INSERT INTO brand(name) VALUES ('Microsoft');");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
