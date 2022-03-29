package dk.sdu.se_f22.searchmodule.infrastructure;


import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchLogging {

    static Connection connection = null;

    public SearchLogging() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = DBConnection.getConnection();
    }


    public void loggingSearch(String search, SearchHits searchHits, List<String> filterTokens) {
        List<Product> products = (List<Product>) searchHits.getProducts();
        List<Brand> brands = (List<Brand>) searchHits.getBrands();
        //List<Content> contents = (List<Content>) searchHits.getContents();

        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO searches(searchString) VALUES (?);");
            insertStatement.setString(1, search);

            for (Product p : products) {
                insertStatement = connection.prepareStatement("INSERT INTO productsearches(productid, searchid) VALUES (?, ?);")
            }

            insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
