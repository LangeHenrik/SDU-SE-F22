package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.productmodule.management.domain_persistance.BaseProduct;
import dk.sdu.se_f22.productmodule.management.domain_persistance.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.db.DBMigration;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Helpers {
    public static void resetDB(){
        try(Connection connection = DBConnection.getPooledConnection()) {
            System.out.println("Resetting range database");
            new DBMigration().runSQLFromFile(connection, "src/main/resources/dk/sdu/se_f22/sharedlibrary/db/migrations/3.3_modifiedRangeFilters.sql");
        } catch (SQLException e) {
            System.out.println("error when resetting database state, pooled connection threw sql exception:");
            e.printStackTrace();
        }
    }

    public static List<String> readFromCSV(String fileName) {
        List<String> out = new ArrayList<>();
      
        try (Scanner scanner = new Scanner(new File("src/test/resources/dk/sdu/se_f22/sortingmodule/range/" + fileName))) {
            while (scanner.hasNextLine()) {
                out.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return out;
    }

    public static List<Product> readMockProductResultsFromFile(String fileName, boolean removeHeaders) {
        List<String> products = Helpers.readFromCSV(fileName);
        if (removeHeaders) {
            products.remove(0);
        }


        return createMockResultsFromStringList(products);
    }

    /**
     * Be aware that instock will always be an empty list
      * @param products a String List of products containing the attributes necessary to create a product.
     *                 Be aware that it must be in the correct order
     * @return a completed list of Product() objects
     */
    public static List<Product> createMockResultsFromStringList(List<String> products) {
        List<Product> mockResults = new ArrayList<>();

        for (String productString : products) {
            String[] attributes = productString.split(",", -1);

            // We choose to create the product this way,
            // since we can utilize the parser already written in the Product() class
            BaseProduct baseProduct = new BaseProduct();
            baseProduct.set(ProductAttribute.UUID, attributes[0]);
            baseProduct.set(ProductAttribute.AVERAGE_USER_REVIEW, attributes[1]);
            baseProduct.set(ProductAttribute.EAN, attributes[2]);
            baseProduct.set(ProductAttribute.PRICE, attributes[3]);
            baseProduct.set(ProductAttribute.PUBLISHED_DATE, attributes[4]);
            baseProduct.set(ProductAttribute.EXPIRATION_DATE, attributes[5]);
            baseProduct.set(ProductAttribute.CATEGORY, attributes[6]);
            baseProduct.set(ProductAttribute.NAME, attributes[7]);
            baseProduct.set(ProductAttribute.DESCRIPTION, attributes[8]);
            // optional attributes
            baseProduct.set(ProductAttribute.SIZE, attributes[9]);
            baseProduct.set(ProductAttribute.CLOCKSPEED, attributes[10]);
            baseProduct.set(ProductAttribute.WEIGHT, attributes[11]);

            ArrayList<String> locations = new ArrayList<>();
            locations.add("Charlottenlund");
            locations.add("Herning");
            baseProduct.setLocations(locations);


            mockResults.add(new Product(baseProduct));
        }

        return mockResults;
    }


    public static String formatArrays(Object[] expected, Object[] actual) {
        return "\nexpected\n" + Arrays.toString(expected) + "\n" +
                "actual:\n" + Arrays.toString(actual) + "\n";
    }
}
