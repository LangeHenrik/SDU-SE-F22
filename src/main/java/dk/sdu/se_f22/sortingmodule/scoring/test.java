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
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(new UUID(63,31),3.9,new ArrayList<>(Arrays.asList(new String[10])),0,1500, Instant.parse("2018-08-09T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product2 = new Product(new UUID(63,31),3.5,new ArrayList<>(Arrays.asList(new String[29])),0,2000,Instant.parse("2021-07-29T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product3 = new Product(new UUID(63,31),2.2,new ArrayList<>(Arrays.asList(new String[1])),0,3000,Instant.parse("2020-04-11T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product4 = new Product(new UUID(63,31),4.2,new ArrayList<>(Arrays.asList(new String[7])),0,2500,Instant.parse("2019-04-09T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product5 = new Product(new UUID(63,31),2.7,new ArrayList<>(Arrays.asList(new String[17])),0,4500,Instant.parse("2023-04-09T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product6 = new Product(new UUID(63,31),2.9,new ArrayList<>(Arrays.asList(new String[2])),0,16500, Instant.parse("2018-07-09T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product7 = new Product(new UUID(63,31),2.5,new ArrayList<>(Arrays.asList(new String[19])),0,2340,Instant.parse("2021-03-09T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product8 = new Product(new UUID(63,31),1.2,new ArrayList<>(Arrays.asList(new String[0])),0,3050,Instant.parse("2018-01-09T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product9 = new Product(new UUID(63,31),3.2,new ArrayList<>(Arrays.asList(new String[7])),0,2576,Instant.parse("2019-12-31T10:15:30.00Z"),null, null, null, null,null,0,0);
        Product product10 = new Product(new UUID(63,31),4.7,new ArrayList<>(Arrays.asList(new String[100])),0,5500,Instant.parse("2023-04-09T10:15:30.00Z"),null, null, null, null,null,0,0);

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

        IScoring scoring = new Scoring();
        //System.out.println(scoring.scoreSort(products));
        //System.out.println(scoring.readTable());
       // scoring.updateRow(16,"1500","bracket");
        //scoring.deleteRow(28);
        //scoring.createRow("price",2000,2);
        //System.out.println(scoring.readTable());
        //System.out.println(scoring.scoreSortPrice(products));
        products = (List<Product>) scoring.scoreSortPrice(products);
        //System.out.println(scoring.scoreSortPrice(products));

        for (Product product : products) {
            System.out.println("Price: "+product.getPrice()+" Review: "+product.getAverageUserReview()+" Date: "+product.getPublishedDate()+" Stock: "+product.getInStock().size());
        }
    }
}
