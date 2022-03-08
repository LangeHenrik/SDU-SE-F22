package dk.sdu.se_f22.sortingmodule.scoring;

import java.util.List;

public interface IScoring {

    /**
    <p> Returns weight value sum for multiple categories </p>
    */
    ArrayList<Object> scoreSort(ArrayList<Object> input);

    /**
    <p> Returns weight value for price </p>
     * @return
     */
    ArrayList<Object> scoreSortPrice(ArrayList<Object> input);

    /**
    <p> Returns weight value for review </p>
    */
    ArrayList<Object> scoreSortReview(ArrayList<Object> input);

    /**
    <p> Returns weight value for stock </p>
    */
    ArrayList<Object> scoreSortStock(ArrayList<Object> input);

    /**
    <p> Returns weight value for release date </p>
    */
    ArrayList<Object> scoreSortReleaseDate(ArrayList<Object> input);

    /**
    <p> ??? </p>
    */
    void update();
}
