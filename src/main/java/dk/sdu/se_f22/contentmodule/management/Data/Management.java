package dk.sdu.se_f22.contentmodule.management.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dk.sdu.se_f22.sharedlibrary.models.Content;

public class Management {
    static Database dat;

    static {
        try {
            dat = Database.getDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int Create(String html) throws SQLException {
        Scanner s;

        try {
            var res = dat.Execute("INSERT INTO pages (html, timestamp) VALUES ( '" + html + "', NOW()) RETURNING id;");
            res.next();
            return res.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            /*StringBuilder scriptString = new StringBuilder();
            try {
                s = new Scanner(new FileInputStream("src/main/resources/dk/sdu/se_f22/contentmodule/management/PostgresScript.txt"));

                while (s.hasNext()) {
                    scriptString.append(s.nextLine());
                }
                dat.executeVoidReturn(scriptString.toString());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            */

        }

        return 0;
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

    public static Content[] GetArrayOfContent(int[] ids) {
        List<Content> contents = new ArrayList<Content>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM pages WHERE id IN (");
        for (int i = 1; i < ids.length; i++) {
            sb.append(", ").append(ids[i]);
        }
        sb.append(");");

        try {
            ResultSet rs = dat.Execute(sb.toString());
            while (rs.next()) {
                var title = Jsoup.parse(rs.getString(2)).getElementsByTag("title");
                contents.add(new Content(rs.getInt(1), rs.getString(2), title.get(0).text(), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (Content[]) contents.toArray();
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

    private static ResultSet GetResultSetFromId(int id) throws SQLException {
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
