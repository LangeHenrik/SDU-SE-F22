package dk.sdu.se_f22.contentmodule.index.ui.mock;

import dk.sdu.se_f22.contentmodule.index.DB.*;

class mockTable {

    public static void createTable(Database db){
        String SQL = "CREATE TABLE foreign_table(" +
                "id serial PRIMARY KEY," +
                "HTML_Page TEXT UNIQUE NOT NULL);";
        db.execute(SQL);
    }

    public static void dropTable(Database db){
        String SQL = "drop table ForeignTable;";
        db.execute(SQL);
    }
}
