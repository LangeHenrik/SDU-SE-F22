package dk.sdu.se_f22.sortingmodule.scoring;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Scoring implements IScoring {
    private static Scoring instance;
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "testprojectdb";
    private String username = "postgres";
    private String password = "postgres582";
    private Connection connection = null;

    private Scoring(){
        initializePostgresqlDatabase();
    }

    public static Scoring getInstance(){
        if (instance == null) {
            instance = new Scoring();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    private void price(List<ProductScore> input) {
        for (ProductScore product : input) {
            double price = product.getProduct().getPrice();
                try {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM prices ORDER BY bracket");
                    ResultSet sqlReturnValues = stmt.executeQuery();

                    while (sqlReturnValues.next()){
                        if (price <= sqlReturnValues.getDouble(2)) {
                            product.setScore(-sqlReturnValues.getInt(3)+product.getScore());
                            break;
                        } else if (sqlReturnValues.isLast()) {
                            product.setScore(-sqlReturnValues.getInt(3)+product.getScore());
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        }
    }

    private void review(List<ProductScore> input) {
        for (ProductScore product : input) {
            double review = product.getProduct().getReview();
            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM reviews");
                ResultSet sqlReturnValues = stmt.executeQuery();
                while (sqlReturnValues.next()){
                    if (review <= sqlReturnValues.getDouble(2)) {
                        product.setScore(sqlReturnValues.getInt(3)+product.getScore());
                        break;
                    } else if (sqlReturnValues.isLast()) {
                        product.setScore(sqlReturnValues.getInt(3)+product.getScore());
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void stock(List<ProductScore> input) {
        for (ProductScore product : input) {
            int stock = product.getProduct().getStock();
            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM stocks");
                ResultSet sqlReturnValues = stmt.executeQuery();

                while (sqlReturnValues.next()){
                    if (stock <= sqlReturnValues.getInt(2)) {
                        product.setScore(sqlReturnValues.getInt(3)+product.getScore());
                        break;
                    } else if (sqlReturnValues.isLast()) {
                        product.setScore(sqlReturnValues.getInt(3)+product.getScore());
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void date(List<ProductScore> input) {
        for (ProductScore product : input) {
            Date newDate = new Date();
            int date = (int)(((newDate.getTime()-product.getProduct().getReleaseDate().getTime())/31556736)/1000);

            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dates");
                ResultSet sqlReturnValues = stmt.executeQuery();

                while (sqlReturnValues.next()) {
                    if (date <= sqlReturnValues.getInt(2)) {
                        product.setScore(-sqlReturnValues.getInt(3)+product.getScore());
                        break;
                    } else if (sqlReturnValues.isLast()) {
                        product.setScore(-sqlReturnValues.getInt(3)+product.getScore());
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<ProductScore> wrapProduct (List<TestProduct> input) {
        List<ProductScore> products = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ProductScore productScore = new ProductScore(input.get(i));
            products.add(productScore);
        }
        return products;
    }

    public List<TestProduct> unWrapProduct (List<ProductScore> input) {
        List<TestProduct> products = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            products.add(input.get(i).getProduct());
        }
        return products;
    }

    @Override
    public List<TestProduct> scoreSort(List<TestProduct> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));
        price(products);
        review(products);
        stock(products);
        date(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public List<TestProduct> scoreSortPrice(List<TestProduct> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));
        price(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public List<TestProduct> scoreSortReview(List<TestProduct> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));
        review(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public List<TestProduct> scoreSortStock(List<TestProduct> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));
        stock(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public List<TestProduct> scoreSortDate(List<TestProduct> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));
        date(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public List<String> readTable() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM scores ORDER BY type,bracket");
            ResultSet sqlReturnValues = stmt.executeQuery();
            List<String> returnValue = new ArrayList<>();
            while (sqlReturnValues.next()){
                returnValue.add(
                        "Id: "+sqlReturnValues.getInt(1)+
                        " Type: "+sqlReturnValues.getString(2)+
                        " Bracket: "+sqlReturnValues.getDouble(3)+
                        " Weight: "+sqlReturnValues.getInt(4)+"\n");
            }
            return returnValue;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateRow(int id, Object newValue, String column) {
        try {
            PreparedStatement stmt;
            switch (column) {
                case "type" -> stmt = connection.prepareStatement("Update scores SET type = ? WHERE id = ?");
                case "bracket" -> stmt = connection.prepareStatement("Update scores SET bracket = ? WHERE id = ?");
                case "weight" -> stmt = connection.prepareStatement("Update scores SET weight = ? WHERE id = ?");
                default -> {
                    System.out.println("Column name doesn't exist");
                    return;
                }
            }
            stmt.setObject(1,newValue);
            stmt.setInt(2,id);
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteRow(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM scores WHERE id = ?");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createRow(String type, double bracket, int weight) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO scores (type, bracket, weight) VALUES(?,?,?)");
            stmt.setString(1,type);
            stmt.setDouble(2,bracket);
            stmt.setInt(3,weight);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

