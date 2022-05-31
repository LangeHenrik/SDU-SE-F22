package dk.sdu.se_f22.contentmodule.management.Domain;

import dk.sdu.se_f22.contentmodule.management.Data.Database;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sharedlibrary.db.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dk.sdu.se_f22.sharedlibrary.models.*;

public class Management {

    public static int Create(int articleNr, String html, int employeeId)  {
        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement("INSERT INTO content_log (html, article_nr, modified) VALUES (?, ?, NOW()) RETURNING id;" +
                "INSERT INTO change_log (employee_id) VALUES (?);")) {
            ps.setInt(2, articleNr);
            ps.setString(1, html);
            ps.setInt(3, employeeId);
            var res = ps.executeQuery();
            return res.getInt(1);
        } catch (Exception e) {
            /*
            DBMigration dbm = new DBMigration();
            try {
                dbm.runSQLFromFile(DBConnection.getPooledConnection(), "src/main/resources/dk/sdu/se_f22/contentmodule/management/PostgresScript.txt");
            } catch (SQLException ex) {
                ex.printStackTrace();
            } */
        }

        return 0;
    }
    static Connection cn;

    public static ResultSet getAllEntries() {
        try {
            cn = Database.getDatabase().connection;
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM content_log");
             var res = ps.executeQuery();
            return res;
        }
        catch (SQLException e) { e.printStackTrace(); }
        return null;
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

    //DANIEL REF PLEASE FOR THE LOVE OF GOD MAKE SOME COMMENTS TO THIS
    public static Content[] GetArrayOfContent(int[] ids) {
        List<Content> contents = new ArrayList<Content>();
        StringBuilder builder = new StringBuilder();

        for( int i = 0 ; i < ids.length; i++ ) {
            builder.append("?,");
        }

        String placeHolders =  builder.deleteCharAt( builder.length() -1 ).toString();
        String st = "SELECT html FROM content_log WHERE id IN (?);";
        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement(st)) {
            for (int i = 0; i < ids.length; i++) {
                ps.setInt(i+1, ids[i]);
            }
            ps.setInt(ids.length + 1, ids.length);
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
        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement(
                "SELECT html FROM content_log WHERE id = ? ORDER BY modified LIMIT 1")) {
            ps.setInt(1, id);
            return ps.executeQuery();
        }
        catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static void Update(int articlenr, String html, int employeeId) {
        Create(articlenr, html, employeeId);
    }

    public static void RealUpdate(int htmlid, String html, int employeeId) {
        //maybe implement this?
    }

    public static void updateIndexInterval(long interval) {
        Indexing.scheduleTimer(interval);
    }

    public static void Delete(int id) {
        try (PreparedStatement ps = DBConnection.getPooledConnection().prepareStatement("DELETE FROM pages WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
