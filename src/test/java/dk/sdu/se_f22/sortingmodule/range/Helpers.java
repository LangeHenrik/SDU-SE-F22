package dk.sdu.se_f22.sortingmodule.range;

import dk.sdu.se_f22.productmodule.management.ProductAttribute;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Helpers {
    public static List<String> readFromCSV(String fileName) {
        List<String> out = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/test/resources/dk/sdu/se_f22/SortingModule/Range/" + fileName))) {
            while (scanner.hasNextLine()) {
                out.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return out;
    }

    public static List<ProductHit> readMockResultsFromFile(String fileName, boolean removeHeaders) {
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
     * @return a completed list of ProductHit objects
     */
    public static List<ProductHit> createMockResultsFromStringList(List<String> products) {
        List<ProductHit> mockResults = new ArrayList<>();

        for (String productString : products) {
            String[] attributes = productString.split(",");

            // We choose to create the product this way,
            // since we can utilize the parser already written in the ProductHit class
            Product product = new Product();
            product.set(ProductAttribute.ID, attributes[0]);
            product.set(ProductAttribute.AVERAGE_USER_REVIEW, attributes[1]);
            product.set(ProductAttribute.EAN, attributes[2]);
            product.set(ProductAttribute.PRICE, attributes[3]);
            product.set(ProductAttribute.PUBLISHED_DATE, attributes[4]);
            product.set(ProductAttribute.EXPIRATION_DATE, attributes[5]);
            product.set(ProductAttribute.CATEGORY, attributes[6]);
            product.set(ProductAttribute.NAME, attributes[7]);
            product.set(ProductAttribute.DESCRIPTION, attributes[8]);
            // optional attributes
            product.set(ProductAttribute.SIZE, attributes[9]);
            product.set(ProductAttribute.CLOCKSPEED, attributes[10]);
            product.set(ProductAttribute.WEIGHT, attributes[11]);


            mockResults.add(new ProductHit(product));
        }

        return mockResults;
    }


    public static String formatArrays(Object[] expected, Object[] actual) {
        return "\nexpected\n" + Arrays.toString(expected) + "\n" +
                "actual:\n" + Arrays.toString(actual) + "\n";
    }
}
