package dk.sdu.se_f22.sortingmodule.scoring;

import java.util.List;

public interface IScoring {

    /**
     <p> Returns a list of products that is sorted by price, reviews, stock and date. </p>
     */
    List<TestProduct> scoreSort(List<TestProduct> input);

    /**
     <p> Returns a list of products that is sorted by price. </p>
     */
    List<TestProduct> scoreSortPrice(List<TestProduct> input);

    /**
     <p> Returns a list of products that is sorted by reviews. </p>
     */
    List<TestProduct> scoreSortReview(List<TestProduct> input);

    /**
     <p> Returns a list of products that is sorted by stock. </p>
     */
    List<TestProduct> scoreSortStock(List<TestProduct> input);

    /**
     <p> Returns a list of products that is sorted by date </p>
     */
    List<TestProduct> scoreSortDate(List<TestProduct> input);

    /**
     <p> Returns the scores table </p>
     */
    List<String> readTable();

    /**
     <p> Updates the row with the new data.
     column is which type of data to input either "type", "bracket" or "weight".
     newValue is the value you want to input, the right datatype is int for weight, double for price, and String for type </p>
     */
    void updateRow(int id, Object newValue, String column);

    /**
     <p> Deletes the row from the database </p>
     */
    void deleteRow(int id);

    /**
     <p> Creates a new row in the database </p>
     */
    void createRow(String type, double bracket, int weight);



}
