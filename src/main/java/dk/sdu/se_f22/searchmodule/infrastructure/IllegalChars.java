package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IllegalChars {


    ReplaceForbiddenChars replaceForbiddenChars = new ReplaceForbiddenChars();

    public void addChar(String character) {
        replaceForbiddenChars.addIllegalChars(character);

        //Adds illegal characters to database
        try {
            PreparedStatement insertStatement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO illegalChars (chars) VALUES (?)");
            insertStatement.setString(1, character);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> illegalCharsFromDB() {

        try {
            PreparedStatement queryStatement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM illegalChars");
            ResultSet queryResultSet = queryStatement.executeQuery();

            List<String> strings = new ArrayList<String>();
            while (queryResultSet.next()) {
                String em = queryResultSet.getString("chars");
                strings.add(em);

            }
            return strings;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void removeChar(String character) {
        replaceForbiddenChars.removeIllegalCharSetting(character);

    }



    public List<String> getChars() {
        return replaceForbiddenChars.getIllegalChars();
    }
}
