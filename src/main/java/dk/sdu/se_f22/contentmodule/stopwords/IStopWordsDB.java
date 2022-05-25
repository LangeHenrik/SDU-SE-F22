package dk.sdu.se_f22.contentmodule.stopwords;

import java.util.HashMap;
import java.util.List;

public interface IStopWordsDB {
    List<String> filter(List<String> unfilteredTokens);
    void addStopWord(String stopWord);
    void removeStopWord(String stopWord);
    List<String> queryStopWord();
    boolean updateStopWord(String oldStopWord,String newStopWord);

}
