package dk.sdu.se_f22.productmodule.irregularwords;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class IrregularWordsTest {

    private Connection connection = null;
    private String dbName = "irregularwords";

    //Connecting the test class to the database before each test.
    @BeforeEach
    void setup(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbName,
                    "postgres",
                    "123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createIRWord() {
        IrregularWords wordCreator = new IrregularWords();
        ArrayList<String> list = new ArrayList<>();
        wordCreator.initialize();

        try {
            //Create words in the table
            wordCreator.createIRWord(10, "Hans");

            //Statement to confirm that word was added to the table
            PreparedStatement stmt = connection.prepareStatement("SELECT word FROM irwords WHERE word = ?");
            stmt.setString(1, "Hans");
            ResultSet result = stmt.executeQuery();

            //Get metadata from ResultSet to get ColumnCount, so While loop can be made that adds ResultSet data to ArrayList
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(result.next()){
                int i = 1;
                while(i <= columnCount) {
                    list.add(result.getString(i++));
                }
            }
            //Assert statement to confirm Hans was added
            assertTrue(list.contains("Hans"));

            //Move to delete test
            wordCreator.deleteIRWord("Hans");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteIRWord() {
        IrregularWords wordDeleter = new IrregularWords();
        wordDeleter.initialize();
        ArrayList<String> list = new ArrayList<>();

        wordDeleter.deleteIRWord("Hans");

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT word FROM irwords WHERE word = ?");
            stmt.setString(1,"Hans");
            ResultSet result = stmt.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while(result.next()){
                int i = 0;
                while(i <= columnCount){
                    list.add(result.getString(i++));
                }
            }
            assertFalse(list.contains("Hans"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateIRWord() {
    }

    @Test
    void readIRWord() {
    }

    @Test
    void getIRWord() {
    }

    @Test
    void getID() {
    }

    @Test
    void searchForIrregularWords() {
    }
    
}