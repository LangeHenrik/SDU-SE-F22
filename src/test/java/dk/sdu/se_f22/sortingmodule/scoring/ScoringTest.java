package dk.sdu.se_f22.sortingmodule.scoring;

import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.models.Product;
import dk.sdu.se_f22.sharedlibrary.utils.ScoreSortType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static dk.sdu.se_f22.sharedlibrary.utils.ScoreSortType.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScoringTest {
    private final Scoring scoring;
    private final ArrayList<Product> products;
    private final Product product1 = new Product(new UUID(63,31),3.9,new ArrayList<>(Arrays.asList(new String[10])),0,1500, Instant.parse("2018-08-09T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product2 = new Product(new UUID(63,31),3.5,new ArrayList<>(Arrays.asList(new String[29])),0,2000,Instant.parse("2021-07-29T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product3 = new Product(new UUID(63,31),2.2,new ArrayList<>(Arrays.asList(new String[1])),0,3000,Instant.parse("2020-04-11T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product4 = new Product(new UUID(63,31),4.2,new ArrayList<>(Arrays.asList(new String[7])),0,2500,Instant.parse("2019-04-09T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product5 = new Product(new UUID(63,31),2.7,new ArrayList<>(Arrays.asList(new String[17])),0,4500,Instant.parse("2023-04-09T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product6 = new Product(new UUID(63,31),2.9,new ArrayList<>(Arrays.asList(new String[2])),0,16500, Instant.parse("2018-07-09T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product7 = new Product(new UUID(63,31),2.5,new ArrayList<>(Arrays.asList(new String[19])),0,2340,Instant.parse("2021-03-09T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product8 = new Product(new UUID(63,31),1.2,new ArrayList<>(),0,3050,Instant.parse("2018-01-09T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product9 = new Product(new UUID(63,31),3.2,new ArrayList<>(Arrays.asList(new String[7])),0,2576,Instant.parse("2019-12-31T10:15:30.00Z"),null, null, null, null,null,0,0);
    private final Product product10 = new Product(new UUID(63,31),4.7,new ArrayList<>(Arrays.asList(new String[100])),0,5500,Instant.parse("2023-04-09T10:15:30.00Z"),null, null, null, null,null,0,0);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    {
        scoring = new Scoring();
        products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
    }

    @BeforeAll
    void setup() {
        String sql = """
                CREATE TABLE scores(
                   id serial PRIMARY KEY,
                   type varchar(6),
                   bracket DOUBLE PRECISION,
                   weight int
                );

                INSERT INTO scores (type, bracket, weight) VALUES
                    ('price',1000,1),
                    ('price',2000,2),
                    ('price',3000,3),
                    ('price',4000,4),
                    ('price',5000,5),
                    ('review',2.5,1),
                    ('review',3.3,2),
                    ('review',3.9,3),
                    ('review',4.3,4),
                    ('review',4.7,5),
                    ('stock',0,1),
                    ('stock',3,2),
                    ('stock',10,3),
                    ('stock',20,4),
                    ('stock',50,5),
                    ('date',1,1),
                    ('date',2,2),
                    ('date',3,3),
                    ('date',4,4),
                    ('date',5,5);""";
        try (var connection = DBConnection.getPooledConnection()) {
            var statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS scores;");
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    void tryAgain() {
        setup();
        System.setOut(originalOut);
    }

    @Order(1)
    @ParameterizedTest(name = "{0}")
    @MethodSource("generateData")
    void scoreSort(ScoreSortType scoreSortType, List<Product> controlProducts) {
        SearchHits searchHits = new SearchHits();
        searchHits.setProducts(products);
        scoring.scoreSort(searchHits, scoreSortType);
        assertEquals(controlProducts,searchHits.getProducts());
    }

    Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(ALL, Arrays.asList(product2,product10,product5,product7,product9,product1,product3,product4,product6,product8)),
                Arguments.of(PRICE, Arrays.asList(product1,product2,product3,product4,product7,product9,product8,product5,product6,product10)),
                Arguments.of(REVIEW, Arrays.asList(product10,product4,product1,product2,product5,product6,product9,product3,product7,product8)),
                Arguments.of(STOCK, Arrays.asList(product2,product10,product5,product7,product1,product4,product9,product3,product6,product8)),
                Arguments.of(DATE, Arrays.asList(product2,product5,product10,product7,product3,product9,product1,product4,product6,product8))
        );
    }

    @Test
    @Order(2)
    void scoreSortAll() {
        List<Product> controlProducts = new ArrayList<>();
        controlProducts.add(product2);
        controlProducts.add(product10);
        controlProducts.add(product5);
        controlProducts.add(product7);
        controlProducts.add(product9);
        controlProducts.add(product1);
        controlProducts.add(product3);
        controlProducts.add(product4);
        controlProducts.add(product6);
        controlProducts.add(product8);

        assertEquals(scoring.scoreSortAll(products),controlProducts);
    }

    @Test
    @Order(3)
    void scoreSortPrice() {
        List<Product> controlProducts = new ArrayList<>();
        controlProducts.add(product1);
        controlProducts.add(product2);
        controlProducts.add(product3);
        controlProducts.add(product4);
        controlProducts.add(product7);
        controlProducts.add(product9);
        controlProducts.add(product8);
        controlProducts.add(product5);
        controlProducts.add(product6);
        controlProducts.add(product10);

        assertEquals(scoring.scoreSortPrice(products), controlProducts);
    }

    @Test
    @Order(4)
    void scoreSortReview() {
        List<Product> controlProducts = new ArrayList<>();
        controlProducts.add(product10);
        controlProducts.add(product4);
        controlProducts.add(product1);
        controlProducts.add(product2);
        controlProducts.add(product5);
        controlProducts.add(product6);
        controlProducts.add(product9);
        controlProducts.add(product3);
        controlProducts.add(product7);
        controlProducts.add(product8);

        assertEquals(scoring.scoreSortReview(products), controlProducts);
    }

    @Test
    @Order(5)
    void scoreSortStock() {
        List<Product> controlProducts = new ArrayList<>();
        controlProducts.add(product2);
        controlProducts.add(product10);
        controlProducts.add(product5);
        controlProducts.add(product7);
        controlProducts.add(product1);
        controlProducts.add(product4);
        controlProducts.add(product9);
        controlProducts.add(product3);
        controlProducts.add(product6);
        controlProducts.add(product8);

        assertEquals(scoring.scoreSortStock(products), controlProducts);
    }

    @Test
    @Order(6)
    void scoreSortDate() {
        List<Product> controlProducts = new ArrayList<>();
        controlProducts.add(product2);
        controlProducts.add(product5);
        controlProducts.add(product10);
        controlProducts.add(product7);
        controlProducts.add(product3);
        controlProducts.add(product9);
        controlProducts.add(product1);
        controlProducts.add(product4);
        controlProducts.add(product6);
        controlProducts.add(product8);

        assertEquals(scoring.scoreSortDate(products), controlProducts);
    }

    @Test
    @Order(7)
    void readTable() {
        String[] input = scoring.readTable().toArray(new String[0]);
        StringBuilder words = new StringBuilder();

        for (String s : input) {
            words.append(s);
        }
        assertEquals(words.toString(), """
                Id: 16 Type: date Bracket: 1.0 Weight: 1
                Id: 17 Type: date Bracket: 2.0 Weight: 2
                Id: 18 Type: date Bracket: 3.0 Weight: 3
                Id: 19 Type: date Bracket: 4.0 Weight: 4
                Id: 20 Type: date Bracket: 5.0 Weight: 5
                Id: 1 Type: price Bracket: 1000.0 Weight: 1
                Id: 2 Type: price Bracket: 2000.0 Weight: 2
                Id: 3 Type: price Bracket: 3000.0 Weight: 3
                Id: 4 Type: price Bracket: 4000.0 Weight: 4
                Id: 5 Type: price Bracket: 5000.0 Weight: 5
                Id: 6 Type: review Bracket: 2.5 Weight: 1
                Id: 7 Type: review Bracket: 3.3 Weight: 2
                Id: 8 Type: review Bracket: 3.9 Weight: 3
                Id: 9 Type: review Bracket: 4.3 Weight: 4
                Id: 10 Type: review Bracket: 4.7 Weight: 5
                Id: 11 Type: stock Bracket: 0.0 Weight: 1
                Id: 12 Type: stock Bracket: 3.0 Weight: 2
                Id: 13 Type: stock Bracket: 10.0 Weight: 3
                Id: 14 Type: stock Bracket: 20.0 Weight: 4
                Id: 15 Type: stock Bracket: 50.0 Weight: 5
                """);
    }

    @Test
    @Order(8)
    void createRow() {
        String type = "price";
        double bracket = 1500;
        int weight = 2;
        scoring.createRow(type,bracket,weight);

        try (var connection = DBConnection.getPooledConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM scores WHERE id = ?");
            statement.setInt(1,21);
            var sqlReturnValues = statement.executeQuery();

            while (sqlReturnValues.next()) {
                assertEquals(type+bracket+weight,sqlReturnValues.getString(2)+sqlReturnValues.getDouble(3)+sqlReturnValues.getInt(4));
            }
            statement.close();
            sqlReturnValues.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(9)
    void deleteRow() {
        scoring.deleteRow(8);
        scoring.deleteRow(12);
        scoring.deleteRow(9);
        scoring.deleteRow(1);
        scoring.deleteRow(21);

        String[] input = scoring.readTable().toArray(new String[0]);
        StringBuilder words = new StringBuilder();

        for (String s : input) {
            words.append(s);
        }
        assertEquals(words.toString(), """
                Id: 16 Type: date Bracket: 1.0 Weight: 1
                Id: 17 Type: date Bracket: 2.0 Weight: 2
                Id: 18 Type: date Bracket: 3.0 Weight: 3
                Id: 19 Type: date Bracket: 4.0 Weight: 4
                Id: 20 Type: date Bracket: 5.0 Weight: 5
                Id: 2 Type: price Bracket: 2000.0 Weight: 2
                Id: 3 Type: price Bracket: 3000.0 Weight: 3
                Id: 4 Type: price Bracket: 4000.0 Weight: 4
                Id: 5 Type: price Bracket: 5000.0 Weight: 5
                Id: 6 Type: review Bracket: 2.5 Weight: 1
                Id: 7 Type: review Bracket: 3.3 Weight: 2
                Id: 10 Type: review Bracket: 4.7 Weight: 5
                Id: 11 Type: stock Bracket: 0.0 Weight: 1
                Id: 13 Type: stock Bracket: 10.0 Weight: 3
                Id: 14 Type: stock Bracket: 20.0 Weight: 4
                Id: 15 Type: stock Bracket: 50.0 Weight: 5
                """);
    }

    @Order(10)
    @ParameterizedTest(name = "{2}: {0} {1}")
    @MethodSource("generateDataUpdateRow")
    void updateRow(int id, Object newValue, String column) {
        scoring.updateRow(id, newValue, column);

        try (var connection = DBConnection.getPooledConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM scores WHERE id = ?");
            statement.setInt(1, id);
            var sqlReturnValues = statement.executeQuery();

            while (sqlReturnValues.next()) {
                switch (column) {
                    case "bracket" -> assertEquals(sqlReturnValues.getDouble(3), Double.parseDouble((String) newValue));
                    case "type" -> assertEquals(sqlReturnValues.getString(2), newValue);
                    case "weight" -> assertEquals(sqlReturnValues.getInt(4), Integer.parseInt((String) newValue));
                    default -> assertEquals("Column name doesn't exist", outContent.toString());}
            }
            statement.close();
            sqlReturnValues.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Stream<Arguments> generateDataUpdateRow() {
        return Stream.of(
                Arguments.of(20,"1700.0","bracket"),
                Arguments.of(20,"date","type"),
                Arguments.of(20,"2","weight"),
                Arguments.of(20,"2","weght")
        );
    }
}

