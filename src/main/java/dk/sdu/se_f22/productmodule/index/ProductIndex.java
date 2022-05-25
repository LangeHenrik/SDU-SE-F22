package dk.sdu.se_f22.productmodule.index;


// Requires Product from group 4.4 and works it gets approved and merged

import java.sql.*;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Product;

public class ProductIndex implements IProductIndex, IProductIndexDataAccess {

    private int categoryHits = 0;
    private int nameHits = 0;
    private int descriptionHits = 0;

    private List<Product> sortedList = new ArrayList<>();

    private List<Product> searchedList = new ArrayList<>();


    private static ProductIndex instance;
    private ProductIndex(){

    }

    public static ProductIndex getInstance(){
        if (instance == null) {
            instance = new ProductIndex();
        }
        return instance;
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
    public void indexProducts(List<Product> products){
        Collections.sort(products);

        try (Connection connection = DBConnection.getPooledConnection();
             CallableStatement cs = connection.prepareCall("CALL deleteIndex()");
        PreparedStatement insertProducts = connection.prepareStatement("INSERT INTO IndexProducts(sortedIds) VALUES (?)");) {
            for (Product p : products) {
                insertProducts.setString(1, String.valueOf(p.getUuid()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


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




    public void updateProduct(int id, Product product, int productID, int specID, int storageID){
        try
                (Connection connection = DBConnection.getPooledConnection();
                 PreparedStatement updateProduct = connection.prepareStatement(
                         "UPDATE product SET name = ?," +
                                 "averageuserreview = ?," +
                                 "publisheddate = ?," +
                                 "expirationdate = ?," +
                                 "price = ?," +
                                 "description = ?," +
                                 "ean = ?," +
                                 "weight = ?" +
                                 "WHERE productID = ?");
                 PreparedStatement updateProductStock = connection.prepareStatement("UPDATE productStock SET productID = ?, stockID = ? WHERE productStockID = ?");
                 PreparedStatement updateProductSpecs = connection.prepareStatement("UPDATE productSpecs SET specID = ?, productID = ? WHERE uniqueID = ?");
                 PreparedStatement updateProductStorage = connection.prepareStatement("UPDATE productStorage SET productid = ?, storageid = ? WHERE uniqueID = ?");)
        {

            updateProduct.setString(1, product.getName());
            updateProduct.setDouble(2, product.getAverageUserReview());
            updateProduct.setTimestamp(3, Timestamp.from(product.getPublishedDate()));
            updateProduct.setTimestamp(4, Timestamp.from(product.getExpirationDate()));
            updateProduct.setDouble(5, product.getPrice());
            updateProduct.setString(6, product.getDescription());
            updateProduct.setLong(7,product.getEan());
            updateProduct.setDouble(8, product.getWeight());
            updateProduct.setInt(9, id);

            updateProductStock.setInt(1, productID);
            updateProductStock.setInt(2, specID);
            updateProductStock.setInt(3, id);

            updateProductSpecs.setInt(1, specID);
            updateProductSpecs.setInt(2, productID);
            updateProductSpecs.setInt(3, id);

            updateProductStorage.setInt(1, productID);
            updateProductStorage.setInt(2, storageID);
            updateProductStorage.setInt(3, id);


            updateProduct.execute();
            updateProductStock.execute();
            updateProductSpecs.execute();
            updateProductStorage.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id){
        try
                (Connection connection = DBConnection.getPooledConnection();
                 PreparedStatement deleteProduct = connection.prepareStatement("DELETE FROM product WHERE productID = ?");
                 PreparedStatement deleteStock = connection.prepareStatement("DELETE FROM productStock WHERE productStockID = ?");
                 PreparedStatement deleteSpecs = connection.prepareStatement("DELETE FROM productStorage WHERE uniqueID = ?");
                 PreparedStatement deleteStorage = connection.prepareStatement("DELETE FROM productSpecs WHERE uniqueID = ?");)
        {

            deleteProduct.setInt(1, id);
            deleteStock.setInt(1, id);
            deleteSpecs.setInt(1, id);
            deleteStorage.setInt(1, id);

            deleteProduct.execute();
            deleteStock.execute();
            deleteSpecs.execute();
            deleteStorage.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // method that creates and stores products in DB with timestamp of search.
    public void createSearchProduct(List<Product> searchedProducts){
        try
            (Connection connection = DBConnection.getPooledConnection();
            )
        {
            // insert all searched products with timestamp created in a table.
        for (Product p : searchedProducts) {
            PreparedStatement searchStatement = connection.prepareStatement("INSERT INTO searches VALUES(?,?)");
            searchStatement.setString(1,p.getUuid().toString());
            searchStatement.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            searchStatement.execute();
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // method that gets all products searched for in a descending order of the creationdate. Showing all newly
    // searches first.
    public List<Product> getProductSearched(){
        List<Product> productList = new ArrayList<>();

        try
                (Connection connection = DBConnection.getPooledConnection();)
        {
            // query all products

            PreparedStatement p = connection.prepareStatement("select product.productid,averageuserreview,ean," +
                    "price,publisheddate,expirationdate,categories.category,name,description,size,clockspeed,weight," +
                    "searches.creationdate from searches\n" +
                    "LEFT JOIN product on product.productid = searches.productid\n" +
                    "LEFT JOIN  productspecs on product.productid = productspecs.productid\n" +
                    "LEFT JOIN  specs on productspecs.specid = specs.specid\n" +
                    "LEFT JOIN  productstorage on productstorage.productid = product.productid\n" +
                    "LEFT JOIN  storage on productstorage.storageid = storage.storageid\n" +
                    "LEFT JOIN categories on categories.categoryid = product.categoryid\n" +
                    "ORDER BY searches.creationdate DESC;");
            p.executeQuery();
            productListCreator(productList, connection, p);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    private void productListCreator(List<Product> productList, Connection connection, PreparedStatement p) throws SQLException {
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
    }

    public List<Product> getProducts(){
        List<Product> productList = new ArrayList<>();

        try
                (Connection connection = DBConnection.getPooledConnection();)
        {
            // query all products
            PreparedStatement p = connection.prepareStatement("select product.productid," +
                    "averageuserreview,ean,price,publisheddate,expirationdate,categories.category," +
                    "name,description,storage.size,specs.clockspeed,product.weight from product\n" +
                    "LEFT JOIN  productspecs on product.productid = productspecs.productid\n" +
                    "LEFT JOIN  specs on productspecs.specid = specs.specid\n" +
                    "LEFT JOIN  productstorage on productstorage.productid = product.productid\n" +
                    "LEFT JOIN  storage on productstorage.storageid = storage.storageid\n" +
                    "LEFT JOIN categories on categories.categoryid = product.categoryid;");
            productListCreator(productList, connection, p);
            p.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void createProduct(Product product){
        // calling stored procedure within db
        try
                (Connection connection = DBConnection.getPooledConnection();
                        CallableStatement cs = connection.prepareCall("CALL insertproduct(?,?,?,?,?,?,?,?,?,?,?,?,?)");)
        {

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
}
