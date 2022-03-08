package dk.sdu.se_f22.sharedlibrary.db;


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
    private static DBConnection dbConnection;


    private DBConnection(){
        loadConfig("config.properties");

        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
        if (dbConnection == null){
            dbConnection = new DBConnection();
        }
        return conn;
    }

    private void loadConfig(String configFileName) {
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        url = prop.getProperty("url");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
    }

    //Sørger for at kalde hver gruppes seedDatabase metode:
    public static void main(String[] args) {
        System.out.println("Not yet implemented");


        //----------------Seeding af brands----------------------
        //Persistence brandPersistence = new Persistence();
        //brandPersistence.seedDatabase();

    }
}
