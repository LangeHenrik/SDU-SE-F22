package dk.sdu.se_f22.productmodule.irregularwords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IrregularWords implements IIrregularWords{

   static public IrregularWords irregularWords = new IrregularWords();

    private Connection connection = null;

    public void initialize(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Irregularwords",
                    "postgres",
                    "aaaa1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createIRWord(int ID, String Word) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO irwords (ID, Word) VALUES (?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteIRWord(int serialKey) {

    }

    @Override
    public void updateIRWord() {

    }

    @Override
    public void readIRWord() {

    }

    @Override
    public void getIRWord() {

    }


    @Override
    public void createIRColumn(String Cname, String dataType, String constraints) {
        try {
            PreparedStatement PS = connection.prepareStatement("ALTER TABLE irwords ADD COLUMN ? ?");
            PS.setString(1, Cname);
            PS.setString(2, dataType);
            PS.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!constraints.equals("")) {
            try {
                PreparedStatement ps = connection.prepareStatement("ALTER TABLE irwords ADD ? (?)");
                ps.setString(1, constraints);
                ps.setString(2, Cname);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteColumnIR(String CName) {

    }

    @Override
    public void createBackup() {

    }

    @Override
    public void loadBackup() {

    }

    @Override
    public List<String> searchForIrregularWords(List<String> arrayList) {
        return arrayList;
    }

    public static void main(String[] args) {
        irregularWords.initialize();
    }
}
