package dk.sdu.se_f22.contentmodule.index.ui.mock;

import dk.sdu.se_f22.contentmodule.index.DB.*;
import dk.sdu.se_f22.contentmodule.index.crud.GruSearch.Search;
import dk.sdu.se_f22.contentmodule.index.crud.delete;
import dk.sdu.se_f22.contentmodule.index.crud.addPage;
import dk.sdu.se_f22.contentmodule.index.crud.update;

import java.util.HashMap;

public class crud {
    Database db;
    delete del;
    update up;

    public crud(){
        db = new Database();
        del = new delete(db);
        up = new update(db);
    }

    public void CreateTable(){
        mockTable.createTable(db);
    }

    public void DropTable(){
        mockTable.dropTable(db);
    }

    public void addPage(String HTML_Page, String[] tokens){
        String SQL = "INSERT INTO foreign_table (HTML_Page) VALUES ('" + HTML_Page + "');";
        int id = db.execute(SQL);
        System.out.println(id);
        addPage.add(db, tokens, id);
    }

    public void deletePage(int i){
        del.delSingular(i);
    }

    public void deletePages(int[] i){
        del.delList(i);
    }

    public void updatePage(int fId, String[] words){
        up.updateSingular(fId, words);
    }

    public void updatePages(HashMap<Integer, String[]> temp){
        up.updateMulti(temp);
    }

    public void search(String[] searchWords){
        Search.SearchMethode(searchWords, db);
    }
}
