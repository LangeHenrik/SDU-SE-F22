package dk.sdu.se_f22.sortingmodule.Scoring;

import java.util.ArrayList;

public class Scoring implements IScoring{

    private void price(ArrayList<Object> input) {
        for (Object o : input) {
            int price = o.getPrice();

            for (int j = 0; j < db.size(); j++) {
                if (price < db(j).get(Price.bracket)) {
                    o.setScore(o.getScore() + db(j).get(Price.weight));
                }
            }
        }
    }

    private void review(ArrayList<Object> input) {
        for (Object o : input) {
            int review = o.getReview();

            for (int j = 0; j < db.size(); j++) {
                if (review < db(j).get(Review.bracket)) {
                    o.setScore(o.getScore() + db(j).get(Review.weight));
                }
            }
        }
    }
    
    private void stock(ArrayList<Object> input) {
        for (Object o : input) {
            int stock = o.getStock();

            for (int j = 0; j < db.size(); j++) {
                if (stock < db(j).get(Stock.bracket)) {
                    o.setScore(o.getScore() + db(j).getStock.weight));
                }
            }
        }
    }

    private void releaseDate(ArrayList<Object> input) {
        for (Object o : input) {
            int releaseDate = o.getReleaseDate();

            for (int j = 0; j < db.size(); j++) {
                if (releaseDate < db(j).get(ReleaseDate.bracket)) {
                    o.setScore(o.getScore() + db(j).get(ReleaseDate.weight));
                }
            }
        }
    }

    @Override
    public ArrayList<Object> scoreSort(ArrayList<Object> input) {
        this.price(input);
        this.review(input);
        this.stock(input);
        this.releaseDate(input);

        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public ArrayList<Object> scoreSortPrice(ArrayList<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public ArrayList<Object> scoreSortReview(ArrayList<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public ArrayList<Object> scoreSortStock(ArrayList<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public ArrayList<Object> scoreSortReleaseDate(ArrayList<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public void update() {
    }
}
