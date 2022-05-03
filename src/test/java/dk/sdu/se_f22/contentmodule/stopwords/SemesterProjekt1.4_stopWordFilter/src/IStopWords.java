import java.util.Collection;
import java.util.List;

public interface IStopWords {
    Collection<String> filter(List<String> unfilteredTokens);

    /*
    the filter-method needs an input of a list of strings, when used, this will remove any words that is within your
    stopword table on your database and return a list of filtered words
     */

//-----------------------------------------------------------------------------------------------------------------------
    void addStopWord(String stopWord);
    /*
    The addStopWord-method takes a string as an arg and is used to add a stopword to your table.

    The method will print to the console if the stopword has been added, and gives an error-msg if the stopword already
    exists
     */


//-----------------------------------------------------------------------------------------------------------------------
    void removeStopWord(String stopWord);
    /*
    The removeStopWord-method takes a string as an arg and is used to add a stopword to your table.

    The method will print to the console if the stopword has been removed, and gives an error-msg if the stopword
    does not exist.
     */

//-----------------------------------------------------------------------------------------------------------------------
    void queryStopWord();
    /*
    this method is used to get a list of all stopwords currently in your table, printed to the console.
     */

}
