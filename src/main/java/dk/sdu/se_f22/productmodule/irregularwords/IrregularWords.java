package dk.sdu.se_f22.productmodule.irregularwords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class IrregularWords implements IIrregularWords{

   static public IrregularWords irregularWords = new IrregularWords();

    private Connection connection = null;
    private boolean canConnect = false;
    private String dbName = "postgres";

    public void initialize(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbName,
                    "postgres",
                    "aaaa1234");
            canConnect = true;
        } catch (SQLException e) {
            e.printStackTrace();
            canConnect = false;
        }
        if(!canConnect){
            createDB();
            createTable();
            canConnect = true;
        }
    }
    private void createDB(){
        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE DATABASE Irregulawords");
            stmt.execute();
            dbName = "Irregularwords";
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createTable(){
        try {
            PreparedStatement tableStmt = connection.prepareStatement("CREATE TABLE irwords(id INTEGER NOT NULL, word VARCHAR PRIMARY KEY);");
            tableStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean createIRWord(int ID, String Word) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO irwords (ID, Word) VALUES (?,?)");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteIRWord(String theWord) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DROP * FROM irwords WHERE word = ?");
            stmt.setString(1,theWord);
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean updateIRWord() {
return false;
    }

    @Override
    public void readIRWord() {

    }

    @Override
    public void getIRWord(String word) {

    }


    @Override
    public boolean createIRColumn(String Cname, String dataType, String constraints) {
        try {
            PreparedStatement PS = connection.prepareStatement("ALTER TABLE irwords ADD COLUMN ? ?");
            PS.setString(1, Cname);
            PS.setString(2, dataType);
            if(!constraints.equals("")) {
                addConstraints(Cname, constraints);
            }
            return PS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addConstraints(String Cname, String constraints) {
        if (!constraints.equals("")) {
            try {
                PreparedStatement ps = connection.prepareStatement("ALTER TABLE irwords ADD ? (?)");
                ps.setString(1, constraints);
                ps.setString(2, Cname);
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean deleteColumnIR(String CName) {
        return false;

    }

    @Override
    public boolean createBackup() {
return false;
    }

    @Override
    public boolean loadBackup() {
return false;
    }

    @Override
    public List<String> searchForIrregularWords(List<String> arrayList) {
        return arrayList;
    }

    public static void main(String[] args) {
        irregularWords.initialize();
    }
}
