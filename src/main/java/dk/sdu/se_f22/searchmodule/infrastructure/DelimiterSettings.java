package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DelimiterSettings {
    private List<String> delimiters;

    public List<String> getDelimiters() {
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
                    "SELECT delimiter FROM searchtokendelimiters");
            ResultSet resultSet = stmt.executeQuery();
            delimiters = new ArrayList<>();
            while (resultSet.next()) {
                delimiters.add(resultSet.getString(2));
            }
            return delimiters;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void appendDelimiters(String delimiter) {
        this.delimiters.add(delimiter);
    }
}
