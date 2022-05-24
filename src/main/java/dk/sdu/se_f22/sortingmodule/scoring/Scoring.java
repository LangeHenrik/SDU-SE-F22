package dk.sdu.se_f22.sortingmodule.scoring;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.sql.*;
import java.time.Instant;
import java.util.*;

public class Scoring implements IScoring {
    private void price(List<ProductScore> input) {
        for (ProductScore product : input) {
            double price = product.getProduct().getPrice();
            try (var connection = DBConnection.getPooledConnection()) {
                 var statement = connection.prepareStatement("SELECT * FROM scores WHERE type = 'price' ORDER BY bracket");
                 var sqlReturnValues = statement.executeQuery();
                while (sqlReturnValues.next()) {
                    double priceCompare = sqlReturnValues.getDouble(3);
                    if (price <= priceCompare) {
                        product.setScore(-sqlReturnValues.getInt(4) + product.getScore());
                        break;
                    } else if (sqlReturnValues.isLast()) {
                        product.setScore(-sqlReturnValues.getInt(4) + product.getScore());
                    }
                }
                statement.close();
                sqlReturnValues.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void review(List<ProductScore> input) {
        for (ProductScore product : input) {
            double review = product.getProduct().getAverageUserReview();
            try (var connection = DBConnection.getPooledConnection()) {
                 var statement = connection.prepareStatement("SELECT * FROM scores WHERE type = 'review';");
                 var sqlReturnValues = statement.executeQuery();
                while (sqlReturnValues.next()){
                    if (review <= sqlReturnValues.getDouble(3)) {
                        product.setScore(sqlReturnValues.getInt(4)+product.getScore());
                        break;
                    } else if (sqlReturnValues.isLast()) {
                        product.setScore(sqlReturnValues.getInt(4)+product.getScore());
                    }
                }
                statement.close();
                sqlReturnValues.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void stock(List<ProductScore> input) {
        for (ProductScore product : input) {
            int stock = product.getProduct().getInStock().size();
            try (var connection = DBConnection.getPooledConnection()) {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM scores WHERE type = 'stock'");
                var sqlReturnValues = statement.executeQuery();
                while (sqlReturnValues.next()){
                    if (stock <= sqlReturnValues.getInt(3)) {
                        product.setScore(sqlReturnValues.getInt(4));
                        break;
                    } else if (sqlReturnValues.isLast()) {
                        product.setScore(sqlReturnValues.getInt(4));
                    }
                }
                statement.close();
                sqlReturnValues.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void date(List<ProductScore> input) {
        for (ProductScore product : input) {
            Instant date = product.getProduct().getPublishedDate();
            try (var connection = DBConnection.getPooledConnection()) {
                var statement = connection.prepareStatement("SELECT * FROM scores WHERE type = 'date'");
                var sqlReturnValues = statement.executeQuery();
                while (sqlReturnValues.next()) {
                    Instant newInstant = Instant.now().minusSeconds((long) sqlReturnValues.getInt(3) *60*60*24*365);
                    if (date.isAfter(newInstant)) {
                        product.setScore(-sqlReturnValues.getInt(4)+product.getScore());
                        break;
                    } else if (sqlReturnValues.isLast()) {
                        product.setScore(-sqlReturnValues.getInt(4)+product.getScore());
                    }
                }
                statement.close();
                sqlReturnValues.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private List<ProductScore> wrapProduct (Collection<Product> input) {
        List<ProductScore> products = new ArrayList<>();

        for (Product product : input) {
            ProductScore productScore = new ProductScore(product);
            products.add(productScore);
        }

        return products;
    }

    private List<Product> unWrapProduct (List<ProductScore> input) {
        List<Product> products = new ArrayList<>();

        for (ProductScore productScore : input) {
            products.add(productScore.getProduct());
        }

        return products;
    }

    @Override
    public SearchHits scoreSort(SearchHits input, ScoreSortType type) {
        switch (type) {
            case ALL -> input.setProducts(scoreSortAll(input.getProducts()));
            case PRICE -> input.setProducts(scoreSortPrice(input.getProducts()));
            case REVIEW -> input.setProducts(scoreSortReview(input.getProducts()));
            case STOCK -> input.setProducts(scoreSortStock(input.getProducts()));
            case DATE -> input.setProducts(scoreSortDate(input.getProducts()));
            default -> System.out.println("error: invalid sort type");
        }

        return input;
    }

    @Override
    public Collection<Product> scoreSortAll(Collection<Product> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));

        price(products);
        review(products);
        stock(products);
        date(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public Collection<Product> scoreSortPrice(Collection<Product> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));

        price(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public Collection<Product> scoreSortReview(Collection<Product> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));

        review(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public Collection<Product> scoreSortStock(Collection<Product> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));

        stock(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public Collection<Product> scoreSortDate(Collection<Product> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));

        date(products);
        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public List<String> readTable() {
        try (var connection = DBConnection.getPooledConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM scores ORDER BY type,bracket");
            var sqlReturnValues = statement.executeQuery();
            List<String> returnValue = new ArrayList<>();
            while (sqlReturnValues.next()){
                returnValue.add(
                        "Id: "+sqlReturnValues.getInt(1)+
                        " Type: "+sqlReturnValues.getString(2)+
                        " Bracket: "+sqlReturnValues.getDouble(3)+
                        " Weight: "+sqlReturnValues.getInt(4)+"\n");
            }
            statement.close();
            sqlReturnValues.close();
            return returnValue;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateRow(int id, Object newValue, String column) {
        try (var connection = DBConnection.getPooledConnection()) {
            PreparedStatement statement;
            switch (column) {
                case "type" -> statement = connection.prepareStatement("Update scores SET type = ? WHERE id = ?");
                case "bracket" -> statement = connection.prepareStatement("Update scores SET bracket = ? WHERE id = ?");
                case "weight" -> statement = connection.prepareStatement("Update scores SET weight = ? WHERE id = ?");
                default -> {
                    System.out.println("Column name doesn't exist");
                    return;
                }
            }
            if (column.equals("bracket")) {
                statement.setDouble(1,Double.parseDouble((String) newValue));
            } else if (column.equals("weight")) {
                statement.setInt(1,Integer.parseInt((String) newValue));
            } else {
                statement.setObject(1,newValue);
            }
            statement.setInt(2,id);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteRow(int id) {
        try (var connection = DBConnection.getPooledConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM scores WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createRow(String type, double bracket, int weight) {
        try (var connection = DBConnection.getPooledConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO scores (type, bracket, weight) VALUES(?,?,?)");
            statement.setString(1,type);
            statement.setDouble(2,bracket);
            statement.setInt(3,weight);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

