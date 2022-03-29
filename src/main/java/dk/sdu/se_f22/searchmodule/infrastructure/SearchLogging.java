package dk.sdu.se_f22.searchmodule.infrastructure;


import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchLogging {
    public static void loggingSearch(String search, SearchHits searchHits, List<String> filterTokens) {
        List<Product> products = (List<Product>) searchHits.getProducts();
        List<Brand> brands = (List<Brand>) searchHits.getBrands();
        //List<Content> contents = (List<Content>) searchHits.getContents();

        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO searches(searchString) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, search);
            insertStatement.execute();

            // Get id of search
            int id = -1;
            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
            }

            // Products
            for (Product p : products) {
                PreparedStatement productInsertStatement = connection.prepareStatement("INSERT INTO productsearches(productid, searchid) VALUES (?, ?);");
                productInsertStatement.setString(1, p.toString());
                productInsertStatement.setInt(2, id);
            }

            // Brands
            for (Brand b : brands) {
                PreparedStatement brandInsertStatement = connection.prepareStatement("INSERT INTO brandsearches(brandid, searchid) VALUES (?, ?);");
                brandInsertStatement.setString(1, String.valueOf(b.getId()));
                brandInsertStatement.setInt(2, id);
            }

            // Content
            // for (Content c : contents) {
            //     PreparedStatement contentInsertStatement = connection.prepareStatement("INSERT INTO contentsearches(contentid, searchid) VALUES (?, ?);");
            //     contentInsertStatement.setString(1, c.getId());
            //     contentInsertStatement.setInt(2, id);
            // }

            insertStatement.execute();
            insertStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
