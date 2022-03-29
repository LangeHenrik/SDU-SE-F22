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
            System.out.println(connection.getClass().getName());
            // Log products
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO searches(searchString) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, search);
            insertStatement.execute();

            Integer id = null;
            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating search failed, no ID obtained.");
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
    public List<Searches> getSearches() {
        List<String> brands = new ArrayList<>();
        List<String> products = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        List<Searches> searchList = new ArrayList<>();

        try(Connection connection = DBConnection.getPooledConnection()) {
            PreparedStatement queryBrandSearches = connection.prepareStatement("SELECT * FROM BrandSearches");
            PreparedStatement queryProductSearches = connection.prepareStatement("SELECT * FROM ProductSearches");
            PreparedStatement queryContentSearches = connection.prepareStatement("SELECT * FROM ContentSearches");
            PreparedStatement querySearches = connection.prepareStatement("SELECT * FROM Searches");

            ResultSet queryBrandSearchesResultSet = queryBrandSearches.executeQuery();
            ResultSet queryProductSearchesResultSet = queryProductSearches.executeQuery();
            ResultSet queryContentSearchesResultSet = queryContentSearches.executeQuery();
            ResultSet querySearchesResultSet = querySearches.executeQuery();

            while (queryBrandSearchesResultSet.next()) {
                brands.add(queryBrandSearchesResultSet.getString("brandID"));
            }

            while(queryProductSearchesResultSet.next()) {
                products.add(queryProductSearchesResultSet.getString("productID"));
            }

            while(queryContentSearchesResultSet.next()) {
                contents.add(queryContentSearchesResultSet.getString("contentID"));
            }

            while(querySearchesResultSet.next()) {
                Searches search = new Searches(
                        querySearchesResultSet.getInt("id"),
                        querySearchesResultSet.getString("searchString"),
                        querySearchesResultSet.getString("timeSearched"),
                        brands,
                        products,
                        contents
                );
                searchList.add(search);
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return searchList;
    }
}
