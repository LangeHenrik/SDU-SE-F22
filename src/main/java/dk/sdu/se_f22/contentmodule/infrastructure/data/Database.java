package dk.sdu.se_f22.contentmodule.infrastructure.data;

import java.sql.*;
import java.util.ArrayList;

public class Database implements DatabaseInterface {

    private Connection conn = null;

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
        }
    }

    public void createTable(String table_name) {
        Statement statement;
        try{
            //String query="create table "+table_name+" (token_id SERIAL, token char(20), primary key(token_id));";
            String query="create table "+table_name+" (token char(20));";
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");
        }catch (Exception e) {
            //System.out.println(e);
        }
    }

    public void saveTokens(ArrayList<String> tokens) {
        Statement statement;
        String token;
        try {
            statement=conn.createStatement();
            statement.addBatch("delete from tokens");
            for (int i = 0; i < tokens.toArray().length; i++) {
                token = tokens.get(i);
                statement.addBatch(String.format("insert into %s(token) values('%s');", "tokens", token));
            }
            statement.executeBatch();
            statement.close();
        } catch(Exception e){
            //System.out.println(e);
        }
    }

    public ArrayList<String> loadTokens() {
        Statement statement;
        ArrayList<String> tokens = new ArrayList<>();
        try {
            String query = String.format("select * from tokens");
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                tokens.add(rs.getString("token"));
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return tokens;
    }

    public void saveFilteredTokens(ArrayList<String> tokens) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public ArrayList<String> loadFilteredTokens() {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void printTokens(ArrayList<String> tokens) {
        for (int i = 0; i < tokens.toArray().length; i++) {
            System.out.println(tokens.get(i));
        }
    }
}
