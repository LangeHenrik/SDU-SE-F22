package dk.sdu.se_f22.contentmodule.infrastructure.data;


import java.sql.*;
import java.util.ArrayList;

public class Database implements DatabaseInterface {
    private static Database instance;
    private Connection conn = null;

    private Database(){setupDatabase();}

    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }


    public void setupDatabase() {
        //fill in database name and password
        final String url = "jdbc:postgresql://localhost:5432/testdb"; //
        final String user = "postgres";
        final String password = "2406";

        try {
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Successfully connected to test database.");

            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to test database.");
            e.printStackTrace();
        } finally {
            if(conn == null){System.exit(-1);}
        }
    }

    public void executeQuery(String query) {
        PreparedStatement insertstatement;
        try{
            //String query="create table "+table_name+" (token_id SERIAL, token char(20), primary key(token_id));";
            System.out.println(query);
            insertstatement=conn.prepareStatement(query);
            insertstatement.execute();
            insertstatement.close();
            System.out.println("Query success");
        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}