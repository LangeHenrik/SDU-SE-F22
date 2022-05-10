package dk.sdu.se_f22.contentmodule.index.crud;

import dk.sdu.se_f22.contentmodule.index.DB.Database;
import dk.sdu.se_f22.contentmodule.index.DB.RecreateTables;

import java.util.ArrayList;

public class addPage {

    public static void add(Database db, String[] words, int fID) {
        //vores magi index
        String SQL = "INSERT INTO pages (foreign_id) VALUES ('" + fID + "');";
        int id = db.execute(SQL);
        ArrayList<Integer> tags = addWords(db, words);
        String tagstest = tags.toString().charAt(0) + " " + tags.toString().substring(1, tags.toString().length() - 1) + "," + tags.toString().substring(tags.toString().length() - 1);
        SQL = "INSERT INTO tags (page_id_ref, tags) VALUES ('" + id + "', '" + tagstest + "')";
        db.execute(SQL);
    }

    public static ArrayList<Integer> addWords(Database db, String[] split) {
        // Tilføjer et ord til words-tabellen hvis ordet ikke allerede ekisterer.
        String SQL;
        int State;
        ArrayList<Integer> tags = new ArrayList<>();
        for (String i : split) {
            SQL = "SELECT word_id FROM words WHERE word = '" + i + "';";
            //State = Execute(db, SQL);  £test
            State = db.executeQuery(SQL);

            if (State == -1) {
                SQL = "INSERT INTO words (word) Values ('" + i.trim() + "');";
                //State = Execute(db,SQL);  £test
                State = db.execute(SQL);
            }else{
                SQL = "update words SET usage = usage + 1 WHERE word_id = '"+State+"'";
                db.execute(SQL);
            }

            if (!(State == -1)) {
                tags.add(State);
            }
        }
        return tags;
    }

//public static int addmetode(int a, int b){
//        return a+b;
//
//}

    public static void main(String[] args) {
        Database database = new Database("localhost", "5432", "TESTDB", "postgres", "1234");
        RecreateTables.recreate(database);

          //addPage.add(database, "bilka");
//        addPage.add(database, "jem og fix");
//        addPage.add(database, "silvan");


    }
}
