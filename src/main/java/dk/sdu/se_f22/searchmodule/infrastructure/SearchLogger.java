package dk.sdu.se_f22.searchmodule.infrastructure;


import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.db.LoggingProvider;
import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SearchLogger {
    private static final Logger logger = LoggingProvider.getLogger(SearchLogger.class);

    public static void logSearch(String search, SearchHits searchHits, List<String> filterTokens) {
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

            // Insert search result id's
            insertValues(products, id,
                    "INSERT INTO productsearches(productid, searchid) VALUES (?, ?);",
                    Product::toString);

            insertValues(brands, id,
                    "INSERT INTO brandsearches(brandid, searchid) VALUES (?, ?);",
                    (brand) -> Integer.toString(brand.getId()));

            // insertValues(contents, id, "INSERT INTO contentsearches(contentid, searchid) VALUES (?, ?);", (content) -> Content::toString));

            insertStatement.execute();
            insertStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static <T> void insertValues(List<T> elements, int id, String sql, Function<T, String> getElementID) {
        try(Connection connection = DBConnection.getPooledConnection()) {
            for (T elem : elements) {
                PreparedStatement brandInsertStatement = connection.prepareStatement(sql);
                brandInsertStatement.setString(1, getElementID.apply(elem));
                brandInsertStatement.setInt(2, id);
                brandInsertStatement.execute();
                brandInsertStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<SearchLog> getAllSearchLogs() {
        List<String> brands = new ArrayList<>();
        List<String> products = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        List<SearchLog> searchList = new ArrayList<>();

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

            /*while(queryContentSearchesResultSet.next()) {
                contents.add(queryContentSearchesResultSet.getString("contentID"));
            }*/

            while(querySearchesResultSet.next()) {
                SearchLog search = new SearchLog(
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
