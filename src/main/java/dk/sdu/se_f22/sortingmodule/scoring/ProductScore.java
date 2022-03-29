package dk.sdu.se_f22.sortingmodule.scoring;

public class ProductScore implements Comparable<ProductScore>{
    private Product product;
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
    public int compareTo(ProductScore productScore) {
        return Integer.compare(getScore(),productScore.getScore());
        /*
        if (this.getScore() > productScore.getScore() ){
            return 1;
        } else if (this.getScore() == productScore.getScore()) {
            return 0;
        } else {
            return -1;
        }

         */
    }
}
