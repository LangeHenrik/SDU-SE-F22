package dk.sdu.se_f22.sortingmodule.scoring;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class test {

    public static void main(String[] args) throws ParseException {
        /*
        List<Product> products = new ArrayList<>();
        products.add(new Product(new UUID(63,31),3.9,new ArrayList<>(Arrays.asList(new String[11])),0,1500, Instant.parse("2018-04-09T10:15:30.00Z"),null, null, null, null,null,0,0));
        products.add(new Product(new UUID(63,31),4.7,new ArrayList<>(Arrays.asList(new String[100])),0,5500,Instant.parse("2023-10-09T10:15:30.00Z"),null, null, null, null,null,0,0));

*//*
        products.add(new Product("Pizza",1895,2.3,1,new SimpleDateFormat("dd/MM/yyyy").parse("02/22/2020")));
        products.add(new Product("Stikkontakt",3000,3.6,17,new SimpleDateFormat("dd/MM/yyyy").parse("05/22/2028")));
        products.add(new Product("Burger",859,1,50,new SimpleDateFormat("dd/MM/yyyy").parse("05/22/1996")));
        products.add(new Product("Chocolate",6879,3.2,23,new SimpleDateFormat("dd/MM/yyyy").parse("03/22/1972")));
        products.add(new Product("Apple",4632,4.9,150,new SimpleDateFormat("dd/MM/yyyy").parse("09/22/2019")));
        products.add(new Product("French Fries",465,4.7,2,new SimpleDateFormat("dd/MM/yyyy").parse("02/22/2018")));
        products.add(new Product("Tacos",1895,2.2,102,new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2016")));
        products.add(new Product("Waffles",1995,17,8,new SimpleDateFormat("dd/MM/yyyy").parse("12/22/2017")));
        products.add(new Product("Pancakes",2895,4.5,77,new SimpleDateFormat("dd/MM/yyyy").parse("08/22/2018")));
        products.add(new Product("Syrup",3895,3,46,new SimpleDateFormat("dd/MM/yyyy").parse("01/22/2002")));
        products.add(new Product("Oatmeals",2895,1.3,200,new SimpleDateFormat("dd/MM/yyyy").parse("06/22/2018")));
        products.add(new Product("Cornflakes",2395,2.2,5,new SimpleDateFormat("dd/MM/yyyy").parse("02/22/2019")));
        products.add(new Product("Cake",3395,4.7,9,new SimpleDateFormat("dd/MM/yyyy").parse("02/22/2017")));

 *//*

        IScoring scoring = new Scoring();
        //System.out.println(scoring.scoreSort(products));
        //System.out.println(scoring.readTable());
       // scoring.updateRow(16,"1500","bracket");
        //scoring.deleteRow(28);
        //scoring.createRow("price",2000,2);
        //System.out.println(scoring.readTable());
        //System.out.println(scoring.scoreSortPrice(products));
        products = (List<Product>) scoring.scoreSortStock(products);
        //System.out.println(scoring.scoreSortPrice(products));

        for (Product product : products) {
            System.out.println("Price: "+product.getPrice()+" Review: "+product.getAverageUserReview()+" Date: "+product.getPublishedDate()+" Stock: "+product.getInStock().size());
        }
        */
    }
}
