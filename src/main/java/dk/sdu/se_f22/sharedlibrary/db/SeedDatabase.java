package dk.sdu.se_f22.sharedlibrary.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SeedDatabase {
    public static void main(String[] args) {
        Connection c = DBConnection.getConnection();

        /*
        try {
            //Ensure that the database is up to date
            String currentLine;
            String sqlCode = "";
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/dk/sdu/se_f22/sharedlibrary/db/database.sql"));
            Statement stmt = c.createStatement();

            while((currentLine = br.readLine()) != null){
                //Safeguards commented code
                if(currentLine.contains("--")) continue;

                if(currentLine.contains(";")) {
                    sqlCode += currentLine+"\n";
                    System.out.println(sqlCode);
                    stmt.execute(sqlCode);
                    sqlCode = "";
                }
                sqlCode+=currentLine + "\n";
            }
            stmt.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */

        //Seed the database
        try {
            Statement stmt = c.createStatement();
            stmt.execute("INSERT INTO brand(name,description,founded,headquarters) VALUES('Microsoft','sells computers','24/12/1950','Ohio');");
            stmt.execute("SELECT * FROM brand");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
