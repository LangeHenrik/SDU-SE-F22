package dk.sdu.se_f22.contentmodule.index.crud;

import dk.sdu.se_f22.contentmodule.index.DB.Database;

public class delete {
    private Database db;

    public delete(Database db){
        this.db = db;
    }

    public void delSingular(int fID){
        String SQL = "SELECT page_id FROM pages WHERE foreign_id = '" + fID + "'";
        int pageID = db.executeQuery(SQL);
        delWords(db, pageID);
        SQL = "" +
                "DELETE FROM tags WHERE page_id_ref='"+pageID+"'; " +
                "DELETE FROM pages WHERE page_id='"+pageID+"';";
        db.execute(SQL);
    }

    public static void delWords(Database db, int pageID){
        String SQL="Select tags FROM tags WHERE page_id_ref = '"+pageID+"'";
        String s = db.executeQueryResultset(SQL).get(0);
        String[] test = s.substring(2,(s.length()-2)).split(", ");

        int usage;

        for (String i : test){
            SQL = "UPDATE words SET usage = usage-1 WHERE word_id = '"+i+"'";
            db.execute(SQL);
            SQL = "SELECT usage FROM words WHERE word_id = '"+i+"'";
            usage = db.executeQuery(SQL);
            if(usage == 0){
                System.out.println("usage = 0");
                SQL="DELETE FROM words WHERE word_id = '"+i+"'";
                db.execute(SQL);
            }
        }
    }

    public void delList(int[] fids){
        for(int i : fids){
            delSingular(i);
        }
    }
}
