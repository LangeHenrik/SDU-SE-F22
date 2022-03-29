package dk.sdu.se_f22.sortingmodule.scoring;

import java.util.Date;

public class Product {
    private String name;
    private int price;
    private double review;
    private int stock;
    private Date releaseDate;

    public Product(String name, int price, double review, int stock, Date releaseDate) {
        this.name = name;
        this.price = price;
        this.review = review;
        this.stock = stock;
        this.releaseDate = releaseDate;
    }

    public int getPrice() {
        return price;
    }

    public double getReview() {
        return review;
    }

    public int getStock() {
        return stock;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", review=" + getReview() +
                ", stock=" + getStock() +
                ", releaseDate=" + getReleaseDate() +
                '}'+"\n";
    }
}

