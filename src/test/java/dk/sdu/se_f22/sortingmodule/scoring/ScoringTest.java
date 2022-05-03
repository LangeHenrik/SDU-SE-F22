package dk.sdu.se_f22.sortingmodule.scoring;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
//hello
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScoringTest {
    Scoring scoring = new Scoring();
    ArrayList<TestProduct> products;

    {
        try {
            products = new ArrayList<>(Arrays.asList(
                    new TestProduct("Pizza",2000,5,10,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012")),
                    new TestProduct("Apple",1000,3,20,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022")),
                    new TestProduct("Cheese",3000,4,30,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017")))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                    ('price',3000,4),
                    ('price',4000,5),
                    ('price',5000,6),
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
        try {
            var connection = DBConnection.getPooledConnection();
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DROP TABLE scores; ");
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void scoreSort() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();

        try {
            controlProducts = new ArrayList<>(Arrays.asList(
                    new TestProduct("Apple",1000,3,20,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022")),
                    new TestProduct("Pizza",2000,5,10,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012")),
                    new TestProduct("Cheese",3000,4,30,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017")))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals(Arrays.toString(scoring.scoreSort(products).toArray()), Arrays.toString(controlProducts.toArray()));
    }

    @Test
    void scoreSortPrice() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();

        try {
            controlProducts = new ArrayList<>(Arrays.asList(
                    new TestProduct("Apple",1000,3,20,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022")),
                    new TestProduct("Pizza",2000,5,10,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012")),
                    new TestProduct("Cheese",3000,4,30,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017"))
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals(Arrays.toString(scoring.scoreSortPrice(products).toArray()), Arrays.toString(controlProducts.toArray()));
    }

    @Test
    void scoreSortReview() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();

        try {
            controlProducts = new ArrayList<>(Arrays.asList(
                    new TestProduct("Pizza",2000,5,10,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012")),
                    new TestProduct("Cheese",3000,4,30,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017")),
                    new TestProduct("Apple",1000,3,20,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022")))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals(Arrays.toString(scoring.scoreSortReview(products).toArray()), Arrays.toString(controlProducts.toArray()));
    }

    @Test
    void scoreSortStock() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();

        try {
            controlProducts = new ArrayList<>(Arrays.asList(
                    new TestProduct("Cheese",3000,4,30,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017")),
                    new TestProduct("Apple",1000,3,20,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022")),
                    new TestProduct("Pizza",2000,5,10,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012"))
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals(Arrays.toString(scoring.scoreSortStock(products).toArray()), Arrays.toString(controlProducts.toArray()));
    }

    @Test
    void scoreSortDate() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();

        try {
            controlProducts = new ArrayList<>(Arrays.asList(
                    new TestProduct("Apple",1000,3,20,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022")),
                    new TestProduct("Pizza",2000,5,10,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012")),
                    new TestProduct("Cheese",3000,4,30,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017")))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals(Arrays.toString(scoring.scoreSortDate(products).toArray()), Arrays.toString(controlProducts.toArray()));
    }


    @Test
    void createRow() {
        String type = "price";
        double bracket = 1500;
        int weight = 2;
        scoring.createRow(type,bracket,weight);

        try {
            var connection = DBConnection.getPooledConnection();
            var statement = connection.prepareStatement("SELECT * FROM scores WHERE id = ?");
            statement.setInt(1,21);
            var sqlReturnValues = statement.executeQuery();

            while (sqlReturnValues.next()) {
                assertEquals(type+bracket+weight,sqlReturnValues.getString(2)+sqlReturnValues.getDouble(3)+sqlReturnValues.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
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
                Id: 3 Type: price Bracket: 3000.0 Weight: 4
                Id: 4 Type: price Bracket: 4000.0 Weight: 5
                Id: 5 Type: price Bracket: 5000.0 Weight: 6
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
    void updateRow() {
        double newValue = 1700;
        scoring.updateRow(21,"1700","bracket");

        try {
            var connection = DBConnection.getPooledConnection();
            var statement = connection.prepareStatement("SELECT * FROM scores WHERE id = 21");
            var sqlReturnValues = statement.executeQuery();

            while (sqlReturnValues.next()) {
                assertEquals(sqlReturnValues.getDouble(3),newValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteRow() {
        scoring.deleteRow(8);
        scoring.deleteRow(12);
        scoring.deleteRow(9);
        scoring.deleteRow(1);

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
                Id: 3 Type: price Bracket: 3000.0 Weight: 4
                Id: 4 Type: price Bracket: 4000.0 Weight: 5
                Id: 5 Type: price Bracket: 5000.0 Weight: 6
                Id: 6 Type: review Bracket: 2.5 Weight: 1
                Id: 7 Type: review Bracket: 3.3 Weight: 2
                Id: 10 Type: review Bracket: 4.7 Weight: 5
                Id: 11 Type: stock Bracket: 0.0 Weight: 1
                Id: 13 Type: stock Bracket: 10.0 Weight: 3
                Id: 14 Type: stock Bracket: 20.0 Weight: 4
                Id: 15 Type: stock Bracket: 50.0 Weight: 5
                """);
    }
}