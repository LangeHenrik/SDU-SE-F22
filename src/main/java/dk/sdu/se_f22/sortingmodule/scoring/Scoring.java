package dk.sdu.se_f22.sortingmodule.scoring;

import java.util.List;

public class Scoring implements IScoring{

    private void price(List<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
//
//        for (Object o : input) {
//            int price = o.getPrice();
//
//            for (int j = 0; j < db.size(); j++) {
//                if (price < db(j).get(Price.bracket)) {
//                    o.setScore(o.getScore() + db(j).get(Price.weight));
//                }
//            }
//        }
    }

    private void review(List<Object> input) {
        throw new UnsupportedOperationException("Unsupported");

//        for (Object o : input) {
//            int review = o.getReview();
//
//            for (int j = 0; j < db.size(); j++) {
//                if (review < db(j).get(Review.bracket)) {
//                    o.setScore(o.getScore() + db(j).get(Review.weight));
//                }
//            }
//        }
    }

    private void stock(List<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
//
//        for (Object o : input) {
//            int stock = o.getStock();
//
//            for (int j = 0; j < db.size(); j++) {
//                if (stock < db(j).get(Stock.bracket)) {
//                    o.setScore(o.getScore() + db(j).getStock.weight));
//                }
//            }
//        }
    }

    private void releaseDate(List<Object> input) {
        throw new UnsupportedOperationException("Unsupported");
//        for (Object o : input) {
//            int releaseDate = o.getReleaseDate();
//
//            for (int j = 0; j < db.size(); j++) {
//                if (releaseDate < db(j).get(ReleaseDate.bracket)) {
//                    o.setScore(o.getScore() + db(j).get(ReleaseDate.weight));
//                }
//            }
//        }
    }

    @Override
    public List<Object> scoreSort(List<Object> input) {
//        this.price(input);
//        this.review(input);
//        this.stock(input);
//        this.releaseDate(input);

        throw new UnsupportedOperationException("Unsupported");
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
