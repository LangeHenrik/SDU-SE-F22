package dk.sdu.se_f22.sortingmodule.category.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CategoryDBConnection {

    public static CategoryDBConnection shared = new CategoryDBConnection();
    private static String URL = "jdbc:postgresql://localhost:5432/SOM3";
    private static String username = "CategoryAdmin";
    private static String password = "CategoryAdmin";
    private static Connection connie = null;

    public Connection connect(){
        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
            connie = DriverManager.getConnection(this.URL,this.username,this.password);
            return connie;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection(){
            try {
                connie.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public static Connection getConnie() {
        return connie;
    }
}
