package dk.sdu.se_f22.sortingmodule.scoring;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoringTest {
    List<TestProduct> products = new ArrayList<>();
/*
    @BeforeAll
    void createProducts() throws ParseException {

        products.add(new TestProduct("Pizza",1895,2.3,1,new SimpleDateFormat("dd/MM/yyyy").parse("02/22/2020")));
        products.add(new TestProduct("Stikkontakt",3000,3.6,17,new SimpleDateFormat("dd/MM/yyyy").parse("05/22/2028")));
        products.add(new TestProduct("Burger",859,1,50,new SimpleDateFormat("dd/MM/yyyy").parse("05/22/1996")));
        products.add(new TestProduct("Chocolate",6879,3.2,23,new SimpleDateFormat("dd/MM/yyyy").parse("03/22/1972")));
        products.add(new TestProduct("Apple",4632,4.9,150,new SimpleDateFormat("dd/MM/yyyy").parse("09/22/2019")));

    }

 */
    @Test
    void wrapProduct() {
    }

    @Test
    void unWrapProduct() {
    }
/*
    @Test
    void scoreSort() throws ParseException {

        Scoring.getInstance().scoreSort(products);
        ArrayList test = new ArrayList();
        products.add(new TestProduct("Pizza",1895,2.3,1,new SimpleDateFormat("dd/MM/yyyy").parse("02/22/2020")));
        products.add(new TestProduct("Stikkontakt",3000,3.6,17,new SimpleDateFormat("dd/MM/yyyy").parse("05/22/2028")));
        products.add(new TestProduct("Burger",859,1,50,new SimpleDateFormat("dd/MM/yyyy").parse("05/22/1996")));
        products.add(new TestProduct("Chocolate",6879,3.2,23,new SimpleDateFormat("dd/MM/yyyy").parse("03/22/1972")));
        products.add(new TestProduct("Apple",4632,4.9,150,new SimpleDateFormat("dd/MM/yyyy").parse("09/22/2019")));

        assertArrayEquals(products.toArray(),test.toArray());
    }

 */
    @Test
    void scoreSortPrice() {
    }

    @Test
    void scoreSortReview() {
    }

    @Test
    void scoreSortStock() {
    }

    @Test
    void scoreSortDate() {
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