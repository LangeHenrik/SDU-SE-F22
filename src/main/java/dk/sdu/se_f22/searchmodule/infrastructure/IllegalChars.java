package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.SearchModule;
import dk.sdu.se_f22.sharedlibrary.SearchHits;
import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.PreparedStatement;
import java.util.List;

public class IllegalChars {


    ReplaceForbiddenChars replaceForbiddenChars = new ReplaceForbiddenChars();

    public void addChar(String character) {
        replaceForbiddenChars.addIllegalChars(character);
    }

    public void removeChar(String character) {
        replaceForbiddenChars.removeIllegalCharSetting(character);
        PreparedStatement insertStatement = DBConnection.getConnection().prepareStatement();
    }

    public List<String> getChars() {
        return replaceForbiddenChars.getIllegalChars();
    }
}
