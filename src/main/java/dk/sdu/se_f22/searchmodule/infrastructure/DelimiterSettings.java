package dk.sdu.se_f22.searchmodule.infrastructure;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DelimiterSettings {
    private List<String> delimiters;

    public List<String> getDelimiters() {
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM searchtokendelimiters");
            ResultSet resultSet = stmt.executeQuery();
            delimiters = new ArrayList<>();
            while (resultSet.next()) {
                delimiters.add(resultSet.getString(1));
            }
            return delimiters;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addDelimiter(String delimiter) {
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO searchtokendelimiters (delimiter) VALUES (?)");
            stmt.setString(1, delimiter);
            stmt.execute();

            System.out.println("Delimiter added.");
        } catch (PSQLException ex){
            System.out.println("This delimiter already exist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeDelimiter(String delim) {
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM searchtokendelimiters WHERE delimiter=?"
            );
            stmt.setString(1, delim);
            for (String s : getDelimiters()) {
                if (s.equals(delim)) {
                    stmt.execute();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
