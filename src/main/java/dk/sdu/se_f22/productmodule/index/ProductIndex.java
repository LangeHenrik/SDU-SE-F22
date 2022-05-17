package dk.sdu.se_f22.productmodule.index;


// Requires Product from group 4.4 and works it gets approved and merged

import java.sql.*;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import dk.sdu.se_f22.sharedlibrary.models.Product;

public class ProductIndex implements IProductIndex, IProductIndexDataAccess {

    private int categoryHits = 0;
    private int nameHits = 0;
    private int descriptionHits = 0;
    private List<Product> searchedList = new ArrayList<>();

    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "xxxx";
    private String username = "xxxx";
    private String password = "xxxx";
    private Connection connection = null;
    private static ProductIndex instance;

    private ProductIndex(){
        initializePostgresqlDatabase();
    }

    public static ProductIndex getInstance(){
        if (instance == null) {
            instance = new ProductIndex();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url
                    + ":"
                    + port
                    + "/" + databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    // Method for finding amount of hits within a product by a token
    public List<Product> searchProducts(List<Product> products, List<String> tokens) {

        for(Product p : products){
            String[] categoryWords = p.getCategory().toLowerCase().split("/");
            String[] nameWords = p.getName().toLowerCase().split(" ");
            String[] descriptionWords = p.getDescription().toLowerCase().split(" ");

            int totalHits = 0;

            for(String s : tokens){
                s = s.toLowerCase();
                totalHits = findHits(categoryWords, s) + findHits(nameWords, s) + findHits(descriptionWords, s);
            }

            p.setHitNum(totalHits);
            searchedList.add(p);
        }
        return searchedList;

    }
    // Method for indexing products depending on amount of search hits
    public List<Product> indexProducts(List<Product> products){
        Collections.sort(products);
        return products;
    }

    public int findHits(String[] info, String token){
        int output = 0;
        for(String i : info){
            if(i == token){
                output++;
            }
        }
        return output;
    }




    public void updateProduct(String id, Product product){
        try {
            PreparedStatement updateCategory = connection.prepareStatement("UPDATE categories set category = ? WHERE id = ?");

            PreparedStatement updateStock = connection.prepareStatement("UPDATE stock set city = ? WHERE id = ?");
            PreparedStatement updateProduct = connection.prepareStatement(
                    "UPDATE product set name = ?," +
                            "averageuserreview = ?," +
                            "creationdate = ?," +
                            "publisheddate = ?," +
                            "expirationdate = ?," +
                            "price = ?," +
                            "description = ?," +
                            "ean = ?," +
                            "weight = ?," +
                            "WHERE id = ?");
            //PreparedStatement updateProductStock = connection.prepareStatement("UPDATE categories set category = ? WHERE id = ?");
            PreparedStatement updateSpecs = connection.prepareStatement("UPDATE specs set clockspeed = ? WHERE id = ?");
            PreparedStatement updateStorage = connection.prepareStatement("UPDATE storage set size = ? WHERE id = ?");
            //PreparedStatement updateProductStorage = connection.prepareStatement("UPDATE categories set category = ? WHERE id = ?");
            //PreparedStatement updateProductSpecs = connection.prepareStatement("UPDATE categories set category = ? WHERE id = ?");

            updateCategory.setString(1, product.getCategory());
            updateCategory.setString(2, id);

            updateStock.setArray(1, (Array) product.getInStock());
            updateStock.setString(2, id);

            updateProduct.setDouble(1, product.getAverageUserReview());
            updateProduct.setString(2, product.getName());
            updateProduct.setTimestamp(4, Timestamp.from(product.getPublishedDate()));
            updateProduct.setTimestamp(5, Timestamp.from(product.getExpirationDate()));
            updateProduct.setDouble(6, product.getPrice());
            updateProduct.setString(7, product.getDescription());
            updateProduct.setLong(8,product.getEan());
            updateProduct.setDouble(9, product.getWeight());
            updateProduct.setString(10, id);

            updateSpecs.setDouble(1, product.getClockspeed());
            updateSpecs.setString(2, id);

            updateStorage.setString(1, product.getSize());
            updateStorage.setString(2, id);

            updateCategory.execute();
            updateStock.execute();
            updateProduct.execute();
            updateSpecs.execute();
            updateStorage.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String id){
        try {
            PreparedStatement deleteCategory = connection.prepareStatement("DELETE FROM categories WHERE id = ?");
            PreparedStatement deleteProduct = connection.prepareStatement("DELETE FROM product WHERE id = ?");
            PreparedStatement deleteStock = connection.prepareStatement("DELETE FROM stock WHERE id = ?");
            PreparedStatement deleteSpecs = connection.prepareStatement("DELETE FROM specs WHERE id = ?");
            PreparedStatement deleteStorage = connection.prepareStatement("DELETE FROM storage WHERE id = ?");

            deleteCategory.setString(1, id);
            deleteProduct.setString(1, id);
            deleteStock.setString(1, id);
            deleteSpecs.setString(1, id);
            deleteStorage.setString(1, id);

            deleteCategory.execute();
            deleteProduct.execute();
            deleteStock.execute();
            deleteSpecs.execute();
            deleteStorage.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void createProduct(Product product){
        // calling stored procedure within db
        try {
            CallableStatement cs = connection.prepareCall("CALL insertproduct(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            cs.setString(1,product.getUuid().toString());
            cs.setDouble(2,product.getAverageUserReview());
            cs.setArray(3,connection.createArrayOf("VARCHAR",product.getInStock().toArray()));
            cs.setLong(4,product.getEan());
            cs.setDouble(5,product.getPrice());
            cs.setTimestamp(6,Timestamp.from(product.getPublishedDate()));
            cs.setTimestamp(7,Timestamp.from(product.getExpirationDate()));
            cs.setString(8,product.getCategory());
            cs.setString(9,product.getName());
            cs.setString(10,product.getDescription());
            cs.setString(11,product.getSize());
            cs.setDouble(12,product.getClockspeed());
            cs.setDouble(13,product.getWeight());

            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Product> getProducts(){
        List<Product> productList = new ArrayList<>();

        try {
            // query all products

            PreparedStatement p = connection.prepareStatement("select product.productid," +
                    "averageuserreview,ean,price,publisheddate,expirationdate,categories.category," +
                    "name,description,storage.size,specs.clockspeed,product.weight from product\n" +
                    "LEFT JOIN  productspecs on product.productid = productspecs.productid\n" +
                    "LEFT JOIN  specs on productspecs.specid = specs.specid\n" +
                    "LEFT JOIN  productstorage on productstorage.productid = product.productid\n" +
                    "LEFT JOIN  storage on productstorage.storageid = storage.storageid\n" +
                    "LEFT JOIN categories on categories.categoryid = product.categoryid;");
            ResultSet resultSet = p.executeQuery();

            while(resultSet.next()){
                List<String> citylist = new ArrayList<>();

                //iterate through cities in the city column of stock table for all products
                PreparedStatement pcity = connection.prepareStatement("select city from stock" +
                        " inner join productstock on productstock.stockid = stock.stockid " +
                        "INNER JOIN product on product.productid = productstock.productid" +
                        " WHERE product.productid = ?");
                pcity.setString(1,resultSet.getString(1));
                ResultSet citySet = pcity.executeQuery();

                while(citySet.next()){
                    citylist.add(citySet.getString(1));
                }

                // create product object
                productList.add(new Product(UUID.fromString(resultSet.getString(1)),
                        resultSet.getDouble(2), citylist,resultSet.getInt(3),
                        resultSet.getDouble(4),resultSet.getTimestamp(5).toInstant(),
                        resultSet.getTimestamp(6).toInstant(), resultSet.getString(7),
                        resultSet.getString(8), resultSet.getString(9),
                        resultSet.getString(10), resultSet.getDouble(11),
                        resultSet.getDouble(12)
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}






        /*
        for (int i = 0; i < products.size(); i++) {
            for (int n = 0; n < token.size(); n++) {
                String[] categoryWords = products.get(i).getCategory().toLowerCase().split("/");
                String[] nameWords = products.get(i).getName().toLowerCase().split(" ");
                String[] descriptionWords = products.get(i).getDescription().toLowerCase().split(" ");


                for (String cateElem : categoryWords) {
                    if (cateElem.contains(token.get(n).toLowerCase())) {
                        categoryHits += 1;
                    }
                }
                for (String nameElem : nameWords) {
                    if (nameElem.contains(token.get(n).toLowerCase())) {
                        nameHits += 1;
                    }
                }
                for (String descElem : descriptionWords) {
                    if (descElem.contains(token.get(n).toLowerCase())) {
                        descriptionHits += 1;
                    }
                }
            }
            int total = categoryHits + nameHits + descriptionHits;

            System.out.printf("Product: " + i +
                            "\nCategory hit counter: %4d " +
                            "\nName hit counter: %8d " +
                            "\nDescription hit counter: %d " +
                            "\nTotal: %19d\n\n",
                            categoryHits, nameHits, descriptionHits, total);

            products.get(i).setHitNum(total);
            sortedList.add(products.get(i));
            Collections.sort(sortedList);


            categoryHits = 0;
            nameHits = 0;
            descriptionHits = 0;
        }


        return sortedList;

        */