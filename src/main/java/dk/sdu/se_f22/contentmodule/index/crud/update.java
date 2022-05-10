package dk.sdu.se_f22.contentmodule.index.crud;

import dk.sdu.se_f22.contentmodule.index.DB.Database;
import dk.sdu.se_f22.contentmodule.index.crud.addPage;
import dk.sdu.se_f22.contentmodule.index.crud.delete;

import java.util.ArrayList;
import java.util.HashMap;

public class update {
    private Database db;


    public update(Database db){
        this.db = db;
    }

    public void updateSingular(int fID, String[] words){
        String SQL = "SELECT page_id FROM pages WHERE foreign_id = '" + fID + "'";
        int page_id = db.executeQuery(SQL);

        delete.delWords(db,page_id);
        ArrayList<Integer> word_ids = addPage.addWords(db, words);

        for (int i : word_ids){
            System.out.println(i);
        }

        String tagstest = word_ids.toString().charAt(0) + " " + word_ids.toString().substring(1, word_ids.toString().length() - 1) + "," + word_ids.toString().substring(word_ids.toString().length() - 1);
        SQL = "UPDATE tags SET tags = '"+tagstest+"' WHERE page_id_ref = '"+page_id+"'";
        db.execute(SQL);

    }

    public void updateMulti(HashMap<Integer, String[]> temp){
        for (int i : temp.keySet()){
            updateSingular(i, temp.get(i));
        }
    }
}
