package dk.sdu.se_f22.contentmodule.index.crud.GruSearch;

import dk.sdu.se_f22.contentmodule.index.DB.Database;

import java.util.ArrayList;

public class Search {

    public static void SearchMethode(String[] searchWords, Database db){
        ArrayList<Integer> wordsId = WordsTable.wordIndexes(searchWords, db);
        ArrayList<tagsTable> sortedTags = tagsTable.TagIndexes(wordsId, db);
        pages.finish(sortedTags, db);
    }
}
