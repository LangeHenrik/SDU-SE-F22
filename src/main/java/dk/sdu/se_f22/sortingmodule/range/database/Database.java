package dk.sdu.se_f22.sortingmodule.range.database;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Database implements DatabaseInterface {
    @Override
    public DBRangeFilter create(DBRangeFilter filter) {
        return null;
    }

    @Override
    public DBRangeFilter read(int id) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Filters WHERE FilterId = ?");
            ps.setString(1, String.valueOf(id));
            ResultSet resultSet = ps.executeQuery();
            DBRangeFilter dbRangeFilter = new DBRangeFilter(
                    resultSet.getInt("FilterId"),
                    resultSet.getString("Description"),
                    resultSet.getString("Name"),
                    resultSet.getString("ProductAttribute"),
                    resultSet.getDouble("Min"),
                    resultSet.getDouble("Max")
                    );
            if (resultSet.next()){
//                throw up;
            //uncommented because: pseudocode
            }

//            return dbRangeFilter;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public DBRangeFilter update(DBRangeFilter filter) {
        return null;
    }

    @Override
    public DBRangeFilter delete(int id) {
        return null;
    }

    @Override
    public List<DBRangeFilter> readAllFilters() {
        return null;
    }
}
