package dk.sdu.se_f22.sortingmodule.scoring;


import dk.sdu.se_f22.sharedlibrary.models.Product;

public class ProductScore implements Comparable<ProductScore>{
    private final Product product;
    private int score;

    public ProductScore(Product product) {
        this.product = product;
        this.score = 0;
    }

    public Product getProduct() {
        return product;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ProductScore{" +
                "score=" + score +
                '}';
    }

    @Override
    public int compareTo(ProductScore productScore) {
        return -Integer.compare(getScore(),productScore.getScore());
    }
}
