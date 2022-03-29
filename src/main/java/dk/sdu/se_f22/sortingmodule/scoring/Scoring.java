package dk.sdu.se_f22.sortingmodule.scoring;

import java.util.*;

public class Scoring implements IScoring {

    private void price(List<ProductScore> input) {
        for (ProductScore product : input) {
           int price = product.getProduct().getPrice();

            for (int j = 0; j < 5; j++) {
                if (price < j*1000) {
                    product.setScore(j);
                    break;
                } else if (price > 5000) {
                    product.setScore(5);
                }
            }
        }
    }

    private void review(List<ProductScore> input) {
        for (Object o : input) {
           /* int review = o.getReview();

            for (int j = 0; j < db.size(); j++) {
                if (review < db(j).get(Review.bracket)) {
                    o.setScore(o.getScore() + db(j).get(Review.weight));
                }
            }*/
        }
    }

    private void stock(List<ProductScore> input) {
        for (Object o : input) {
            /*int stock = o.getStock();

            for (int j = 0; j < db.size(); j++) {
                if (stock < db(j).get(Stock.bracket)) {
                    o.setScore(o.getScore() + db(j).getStock.weight));
                }
            }*/
        }
    }

    private void releaseDate(List<ProductScore> input) {
        for (Object o : input) {
            /*int releaseDate = o.getReleaseDate();

            for (int j = 0; j < db.size(); j++) {
                if (releaseDate < db(j).get(ReleaseDate.bracket)) {
                    o.setScore(o.getScore() + db(j).get(ReleaseDate.weight));
                }
            }*/
        }
    }

    public List<ProductScore> wrapProduct (List<Product> input) {
        List<ProductScore> products = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ProductScore productScore = new ProductScore(input.get(i));
            products.add(productScore);
        }
        return products;
    }

    public List<Product> unWrapProduct (List<ProductScore> input) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            products.add(input.get(i).getProduct());
        }
        return products;
    }

    @Override
    public List<Product> scoreSort(List<Product> input) {
        List<ProductScore> products = new ArrayList<>(this.wrapProduct(input));
        this.price(products);
        //this.review(products);
        //this.stock(products);
        //this.releaseDate(products);

        Collections.sort(products);

        return unWrapProduct(products);
    }

    @Override
    public List<Object> scoreSortPrice(List<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public List<Object> scoreSortReview(List<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public List<Object> scoreSortStock(List<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public List<Object> scoreSortReleaseDate(List<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public void update() {
    }
}
