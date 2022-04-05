package dk.sdu.se_f22.productmodule.index;


// Requires ProductHit from group 4.4 and works it gets approved and merged

import java.sql.*;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import dk.sdu.se_f22.sharedlibrary.models.ProductHit;

public class ProductIndex implements IProductIndex{

    private int categoryHits = 0;
    private int nameHits = 0;
    private int descriptionHits = 0;
    private List<ProductHit> sortedList = new ArrayList<>();

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

    public static CreateReadClass getInstance(){
        if (instance == null) {
            instance = new CreateReadClass();
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

    // Method for finding amount of hits within a product by a token, then returning an indexed list by the hits
    public List<ProductHit> indexProductsByToken(List<ProductHit> products, List<String> token) {


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
            /*
            System.out.printf("Product: " + i +
                            "\nCategory hit counter: %4d " +
                            "\nName hit counter: %8d " +
                            "\nDescription hit counter: %d " +
                            "\nTotal: %19d\n\n",
                            categoryHits, nameHits, descriptionHits, total);
            */
            products.get(i).setHitNum(total);
            sortedList.add(products.get(i));
            Collections.sort(sortedList);


            categoryHits = 0;
            nameHits = 0;
            descriptionHits = 0;
        }
        return sortedList;
    }
    public void updateProduct(String id, ProductHit product){
        try {
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE product set averageUserReview = ?, instock = ?, ean = ?, price = ?, publisheddate = ?, expirationdate = ?, category = ?, name = ?, description = ?, weight = ? WHERE id = ?");
            updateStatement.setDouble(1, product.getAverageUserReview());
            updateStatement.setArray(2, (Array) product.getInStock());
            updateStatement.setLong(3, product.getEan());
            updateStatement.setDouble(4, product.getPrice());
            updateStatement.setString(5, String.valueOf(product.getPublishedDate()));
            updateStatement.setString(6, String.valueOf(product.getExpirationDate()));
            updateStatement.setString(7, product.getCategory());
            updateStatement.setString(8, product.getName());
            updateStatement.setString(9, product.getDescription());
            updateStatement.setDouble(10, product.getWeight());
            updateStatement.setString(11, String.valueOf(id));
            updateStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String id){
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
            deleteStatement.setString(1, id);
            deleteStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void createProduct(ProductHit product){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT into products (uuid,averageuserreview,price,clockspeed,weight,ean,size,category, name,description,publisheddate,expirationdate,inStock) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,product.uuid);
            preparedStatement.setDouble(2,product.averageUserReview);
            preparedStatement.setDouble(3,product.price);
            preparedStatement.setDouble(4,product.clockspeed);
            preparedStatement.setDouble(5,product.weight);
            preparedStatement.setString(6,product.ean);
            preparedStatement.setString(7,product.size);
            preparedStatement.setString(8,product.category);
            preparedStatement.setString(9,product.name);
            preparedStatement.setString(10,product.description);
            preparedStatement.setTimestamp(11,Timestamp.from(product.publishedDate));
            preparedStatement.setTimestamp(12,Timestamp.from(product.expirationDate));
            preparedStatement.setArray(13, connection.createArrayOf("VARCHAR",
                    product.inStock.toArray()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<ProductHit> getProducts(){
        List<ProductHit> productHitList = new ArrayList<>();
        try {
            PreparedStatement p = connection.prepareStatement("select * from products");
            ResultSet resultSet = p.executeQuery();
            while(resultSet.next()){
                productHitList.add(new ProductHit(UUID.fromString(resultSet.getString(2)),
                        resultSet.getDouble(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getDouble(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getTimestamp(12).toInstant(),
                        resultSet.getTimestamp(13).toInstant(),
                        Arrays.asList(resultSet.getArray(14).toString())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productHitList;
    }
}