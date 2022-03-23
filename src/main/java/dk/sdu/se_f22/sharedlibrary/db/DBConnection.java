package dk.sdu.se_f22.sharedlibrary.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private String url;
    private String user;
    private String password;
    private static Connection conn;
    private static DBConnection dbConnection = null;

    private DBConnection(){
        loadConfig();

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try (InputStream inputStream = new FileInputStream("config.properties")) {
            Properties props = new Properties();
            props.load(inputStream);

            url = props.getProperty("db_url");
            user = props.getProperty("db_user");
            password = props.getProperty("db_password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            if (dbConnection == null || conn.isClosed() ){
                dbConnection = new DBConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
