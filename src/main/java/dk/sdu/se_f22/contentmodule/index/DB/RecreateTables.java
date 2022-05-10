package dk.sdu.se_f22.contentmodule.index.DB;

public class RecreateTables {
    // Opretter alle tables
    public static void recreate(Database db) {
        droptables(db);
        wordsTable(db);
        pagesTable(db);
        tagsTable(db);
    }

    private static void droptables(Database db) {
        String SQL = "drop table tags;" +
                "drop table pages;" +
                "drop table words;";
        db.execute(SQL);
    }

    private static void wordsTable(Database db) {
        String SQL = "" +
                "CREATE TABLE words(" +
                "word_id serial PRIMARY KEY," +
                "word varchar(50) UNIQUE NOT NULL," +
                "usage int DEFAULT '1');";

        db.execute(SQL);
    }

    private static void tagsTable(Database db) {
        String SQL = "" +
                "CREATE TABLE tags(" +
                " page_id_ref integer PRIMARY KEY NOT NULL REFERENCES pages(page_id), " +
                "tags TEXT);";
        db.execute(SQL);
    }

    private static void pagesTable(Database db) {
        String SQL =
                "CREATE TABLE pages(" +
                        "page_id serial PRIMARY KEY, " +
                        "Foreign_id int DEFAULT '-1');";
        db.execute(SQL);
    }
}
