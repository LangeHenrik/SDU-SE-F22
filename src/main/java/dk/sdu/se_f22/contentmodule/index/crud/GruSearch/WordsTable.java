package dk.sdu.se_f22.contentmodule.index.crud.GruSearch;

import dk.sdu.se_f22.contentmodule.index.DB.Database;

import java.util.ArrayList;

class WordsTable {

    static ArrayList<Integer> wordIndexes(String[] words, Database db){
        ArrayList<Integer> WordIndexesArray = new ArrayList<>();
        String SQL;
        int id;

        for(String word : words){
            SQL = "SELECT word_id FROM words WHERE word = '"+word+"';";
            System.out.println(SQL);
            id = db.executeQuery(SQL);

            if(id > -1){
                WordIndexesArray.add(id);
            }
        }
        return WordIndexesArray;
    }
}
