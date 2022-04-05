package dk.sdu.se_f22.sortingmodule.scoring;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoringTest {
    Scoring scoring = Scoring.getInstance();
    ArrayList<TestProduct> products;
    {
        try {
            products = new ArrayList<>(Arrays.asList(
                    new TestProduct("Pizza",2000,5,10,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/05/2022")),
                    new TestProduct("Apple",1000,3,20,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022")),
                    new TestProduct("Cheese",3000,4,30,
                            new SimpleDateFormat("dd/MM/yyyy").parse("01/03/2022")))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    void setUp() {
        scoring.setUrl("");
        scoring.setPort(0);
        scoring.setUsername("");
        scoring.setPassword("");
        scoring.setConnection(null);

    }

    @AfterAll
    void tearDown() {

        try {
            scoring.getConnection().close();

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    //TODO: Sorter produkter i rækkefølge efter score points.
    @Test
    void scoreSort() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();
        {
            try {
                controlProducts = new ArrayList<>(Arrays.asList(
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

        assertArrayEquals(scoring.scoreSort(products).toArray(), controlProducts.toArray(),"Succeeded");
    }

    @Test
    void scoreSortPrice() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();
        {
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
        }

        assertArrayEquals(scoring.scoreSort(products).toArray(), controlProducts.toArray(),"Succeeded");
    }

    @Test
    void scoreSortReview() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();
        {
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
        }

        assertArrayEquals(scoring.scoreSort(products).toArray(), controlProducts.toArray(),"Succeeded");
    }

    @Test
    void scoreSortStock() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();
        {
            try {
                controlProducts = new ArrayList<>(Arrays.asList(
                        new TestProduct("Cheese",3000,4,30,
                                new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2017")),
                        new TestProduct("Apple",1000,3,20,
                                new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022")),
                        new TestProduct("Pizza",2000,5,10,
                                new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2012")))
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        assertArrayEquals(scoring.scoreSort(products).toArray(), controlProducts.toArray(),"Succeeded");
    }

    @Test
    void scoreSortDate() {
        ArrayList<TestProduct> controlProducts = new ArrayList<>();
        {
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
        }

        assertArrayEquals(scoring.scoreSort(products).toArray(), controlProducts.toArray(),"Succeeded");
    }

    @Test
    void readTable() {
    }

    @Test
    void updateRow() {
    }

    @Test
    void deleteRow() {
    }

    @Test
    void createRow() {
    }
}