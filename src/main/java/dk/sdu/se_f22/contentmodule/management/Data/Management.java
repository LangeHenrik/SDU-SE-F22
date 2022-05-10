package dk.sdu.se_f22.contentmodule.management.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Management {
    static Database dat;

    static {
        try {
            dat = Database.getDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Create(String html) throws SQLException {
        Scanner s;

        try {
            dat.executeVoidReturn("INSERT INTO pages (html, timestamp) VALUES ( '" + html + "', NOW());");
        } catch (Exception e) {
            StringBuilder scriptString = new StringBuilder();
            try {
                s = new Scanner(new FileInputStream("dk/sdu/se_f22/contentmodule/management/PostgresScript.txt"));

                while (s.hasNext()) {
                    scriptString.append(s.nextLine());
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            dat.executeVoidReturn(scriptString.toString());
        }
    }

    public static String getPageString(int id) throws SQLException {
        var result = GetResultSetFromId(id);
        try {
            result.next();
            return result.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Document GetPageDocument(int id) throws SQLException {
        var result = GetResultSetFromId(id);
        try {
            result.next();
            return Jsoup.parse(result.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ResultSet GetResultSetFromId(int id) throws SQLException{
        return dat.Execute("SELECT * FROM pages WHERE id = " + id);
    }

    public static void Update(int id, String html) {
        try {
            var dat = Database.getDatabase();
            dat.executeVoidReturn("UPDATE pages SET html = '" + html + "', timestamp = NOW() WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateIndexInterval(long interval) {
        Indexing.scheduleTimer(interval);
    }

    public static void Delete(int id) {
        try {
            dat.executeVoidReturn("DELETE FROM pages WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
