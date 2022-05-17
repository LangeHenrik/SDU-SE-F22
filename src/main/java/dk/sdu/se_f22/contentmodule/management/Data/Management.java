package dk.sdu.se_f22.contentmodule.management.Data;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.db.DBMigration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dk.sdu.se_f22.sharedlibrary.models.Content;

public class Management {

    public static int Create(String html) throws SQLException {
        Scanner s;

        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement("INSERT INTO pages (html, timestamp ) VALUES (?, NOW()) RETURNING id;")) {
            var res = ps.executeQuery();
            return res.getInt(1);
        } catch (Exception e) {
            DBMigration dbm = new DBMigration();
            dbm.runSQLFromFile(DBConnection.getPooledConnection(), "src/main/resources/dk/sdu/se_f22/contentmodule/management/PostgresScript.txt");

       }

        return 0;
    }

    public static String getPageString(int id) throws SQLException {
        var result = GetResultSetFromId(id);
        try {
            result.next();
            return result.getString(2);
        } catch (SQLException e) {;
            return null;
        }
    }

    public static Content[] GetArrayOfContent(int[] ids) {
        List<Content> contents = new ArrayList<Content>();
        StringBuilder sb = new StringBuilder();
        StringBuilder builder = new StringBuilder();

        for( int i = 0 ; i < ids.length; i++ ) {
            builder.append("?,");
        }

        String placeHolders =  builder.deleteCharAt( builder.length() -1 ).toString();
        String st = "SELECT * FROM pages WHERE id in ("+ placeHolders + ")";
        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement(st)) {
            for (int i = 0; i < ids.length; i++) {
                ps.setInt(i+1, ids[i]);
            }
            ResultSet rs = ps.executeQuery();

            //Parses to Content class
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
            return Jsoup.parse(result.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ResultSet GetResultSetFromId(int id) {
        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement("SELECT * FROM pages WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeQuery();
        }
        catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static void Update(int id, String html) {
        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement("UPDATE pages SET html = ? WHERE id = ?")) {
            ps.setInt(1, id);
            ps.setString(2, html);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateIndexInterval(long interval) {
        Indexing.scheduleTimer(interval);
    }

    public static void Delete(int id) {
        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement("DELETE FROM pages WHERE id = ?")) {
            ps.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
