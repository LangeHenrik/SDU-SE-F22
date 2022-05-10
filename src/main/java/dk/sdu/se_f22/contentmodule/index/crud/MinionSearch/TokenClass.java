package crud.MinionSearch;

import DB.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class TokenClass {
    Database db;
    HashMap<Integer, SortingClass> map = new HashMap<>();


    TokenClass(Database db) {
        this.db = db;
    }

    ArrayList<String> tokenForhold(ArrayList<String> tokens) {
        /* Finder alle de ord som matcher search query
        Hvis ordet ekisterer (id>-1). Id kommer i arraylist ->
        For loop i arraylist gennemgår alle ord og finder de sider som indeholder tags som matcher token
        Ændrer hashmappet iforhold til antallet af forkomster

        * */
        int id = -1;
        ArrayList<Integer> word_ids = new ArrayList<>();
        for (String i : tokens) {
            String SQL = "SELECT word_id FROM words WHERE word = '"+i+"';"; //
            id = db.executeQuery(SQL);
            if (id > -1) {
                word_ids.add(id);
            }
        }
        System.out.println(word_ids);

        // Finder hvilket page_id som har følgende word_id

        for (int token : word_ids) {
            System.out.println(token);
            try {
                PreparedStatement statement = db.connection.prepareStatement("SELECT page_id_ref FROM tags WHERE tags LIKE '% "+token+",%'");
                ResultSet set = statement.executeQuery();

                while (set.next()) {
                    try {
                        map.get(set.getInt(1)).addUsage();

                    } catch (Exception e) {
                        map.put(set.getInt(1), new SortingClass(set.getInt(1)));
                    }
                }
                // Nu har vi et hashmap med page_id, antallet af forkomster (words_id vs search-string)
            } catch (SQLException e) {
                System.out.println("Der er sket en fejl page_id tag table" );
            }
        }
        // Rangering af page_id fra "set" ud fra forkomst af flest matches..
        ArrayList<SortingClass> list = new ArrayList<>();
        list.addAll(map.values());
        Collections.sort(list); // list er nu sorteret
        return getPageId(list, db);
    }

    //metode til at returnere pageid fra list!
    ArrayList<String> getPageId(ArrayList<SortingClass> list, Database db) {
        String SQL;
        String page_id = "";
        int pageIdLoc = 1;
        int page_name = 2;
        int foreign_Id = 4; // Evt connection til Sisses gruppe

        ArrayList<String> temp = new ArrayList<>();
        for (SortingClass i : list) {
            SQL = "SELECT * FROM pages WHERE page_id = '" + i.getId() + "'";
            try {
                PreparedStatement statement = db.connection.prepareStatement(SQL);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    temp.add(set.getArray(pageIdLoc).toString());
                    System.out.println(set.getArray(page_name).toString());
                }
            } catch (SQLException e) {
                System.out.println("Der er sket en fejl i get PageId metode");
            }
        }
        return temp;
    }

}