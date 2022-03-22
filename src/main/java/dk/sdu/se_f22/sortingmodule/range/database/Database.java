package dk.sdu.se_f22.sortingmodule.range.database;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.range.dbrangefilter.DBRangeFilter;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterIdException;

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

    /** This method will search the database for a filter matching the id given, and will return a representation of this filter.
     *
     * @param id The id to query for in the database
     * @return an instance of {@link DBRangeFilter} if succesful otherwise null, if the result contained either no filter with this id or contained more than 1 (black magic, it can't)
     */
    @Override
    public DBRangeFilter read(int id) {
        // currently only works for a Double Filter
        Connection connection = DBConnection.getConnection();
        DBRangeFilter dbRangeFilter = null;
        try {
            //Change the table queried to reflect the actual type.
            //The current plan is that we will create a stored function, which can retrieve the filter from the correct table/view

            // Scratch the comment above, the plan has changed to this:
            // First get the type of the filter by calling the stored function:
            // "SELECT get_type_of_filter(filter_id);"
            // where filter_id is the id we are interested in retrieving
            PreparedStatement typeStatement = connection.prepareStatement("SELECT get_type_of_filter(?);");
            typeStatement.setInt(1, id);
            ResultSet typeResult = typeStatement.executeQuery();
            if (typeResult.next()){
                // Then get the filter from the correct view by using the now known data type
                // This can be done by calling the stored function: (e.g. for type double)
                // "SELECT * from get_double_filter(filter_id);"
                ResultSet filterResultSet;
                switch (typeResult.getString(1)){
                    case "Double":
                        filterResultSet = getSpecificFilter(connection, "get_double_filter", id);
                        if (filterResultSet.next()){
                            dbRangeFilter = new DBRangeFilter(
                                    filterResultSet.getInt("FilterId"),
                                    filterResultSet.getString("Description"),
                                    filterResultSet.getString("Name"),
                                    filterResultSet.getString("ProductAttribute"),
                                    //The below two lines assume the filter read is a Double filter
                                    filterResultSet.getDouble("Min"),
                                    filterResultSet.getDouble("Max")
                            );
                        }
                        break;
                    case "Long":
                        filterResultSet = getSpecificFilter(connection, "get_long_filter", id);
                        if (filterResultSet.next()){
                            dbRangeFilter = new DBRangeFilter(
                                    filterResultSet.getInt("FilterId"),
                                    filterResultSet.getString("Description"),
                                    filterResultSet.getString("Name"),
                                    filterResultSet.getString("ProductAttribute"),
                                    //The below two lines assume the filter read is a Double filter
                                    filterResultSet.getLong("Min"),
                                    filterResultSet.getLong("Max")
                            );
                        }
                        break;
                    case "Time":
                        filterResultSet = getSpecificFilter(connection, "get_time_filter", id);
                        if (filterResultSet.next()){
                            dbRangeFilter = new DBRangeFilter(
                                    filterResultSet.getInt("FilterId"),
                                    filterResultSet.getString("Description"),
                                    filterResultSet.getString("Name"),
                                    filterResultSet.getString("ProductAttribute"),
                                    //The below two lines assume the filter read is a Double filter
                                    filterResultSet.getTimestamp("Min").toInstant(),
                                    filterResultSet.getTimestamp("Max").toInstant()
                            );
                        }
                        break;
                    default:
                        break;
                }

                if (filterResultSet.next()){
                    // This means we somehow got 2 filters returned
//                throw up;
                    //uncommented because: pseudocode
                }
            }


/* Commented because it is an olf implementation, which is getting yeeted
// It is kept in as a comment to give the group a chance to compare the old vs the new implementation
// Will get deleted ASAP

            // old
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM SortingRangeDoubleView WHERE FilterId = ?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                dbRangeFilter = new DBRangeFilter(
                        resultSet.getInt("FilterId"),
                        resultSet.getString("Description"),
                        resultSet.getString("Name"),
                        resultSet.getString("ProductAttribute"),
                        //The below two lines assume the filter read is a Double filter
                        resultSet.getDouble("Min"),
                        resultSet.getDouble("Max")
                );
            }

            if (resultSet.next()){
//                throw up;
            //uncommented because: pseudocode
            }
            */

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        if(dbRangeFilter == null){
//            throw new InvalidFilterIdException("invalid id when reading from the database");
//        }
        //uncommented because it may not actually be the desired behaviour
        return dbRangeFilter;
    }

    private ResultSet getSpecificFilter(Connection conn, String sqlFunction, int id) throws SQLException {
        PreparedStatement getDoubleFilterStatement = conn.prepareStatement("SELECT * from " + sqlFunction + "(?);");
        getDoubleFilterStatement.setInt(1, id);
        return getDoubleFilterStatement.executeQuery();
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
