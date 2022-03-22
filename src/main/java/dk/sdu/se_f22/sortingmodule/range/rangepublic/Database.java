package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class Database implements DatabaseInterface {
    @Override
    public RangeFilter create(RangeFilter filter) {
        return null;
    }


    /** This method will search the database for a filter matching the id given, and will return a representation of this filter.
     *
     * @param id The id to query for in the database
     * @return an instance of {@link RangeFilter} if succesful otherwise null, if the result contained either no filter with this id or contained more than 1 (black magic, it can't)
     */
    @Override
    public RangeFilter read(int id) throws UnknownFilterTypeException {
        Connection connection = DBConnection.getConnection();
        RangeFilter dbRangeFilter = null;
        try {
            // Scratch the comment above, the plan has changed to this:
            // First get the type of the filter by calling the stored function:
            // "SELECT get_type_of_filter(filter_id);"
            // where filter_id is the id we are interested in retrieving
            PreparedStatement typeStatement = connection.prepareStatement("SELECT get_type_of_filter(?);");
            typeStatement.setInt(1, id);
            ResultSet typeResult = typeStatement.executeQuery();
            if (typeResult.next()){
                if(typeResult.getString(1) == null){
                    return dbRangeFilter;
                }
                // Then get the filter from the correct view by using the now known data type
                ResultSet filterResultSet;
                //noinspection EnhancedSwitchMigration
                switch (typeResult.getString(1)){
                    case "Double":
                        filterResultSet = getSpecificFilter(connection, "get_double_filter", id);
                        if (filterResultSet.next()){
                            dbRangeFilter = new DoubleFilter(
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
                            dbRangeFilter = new LongFilter(
                                    filterResultSet.getInt("FilterId"),
                                    filterResultSet.getString("Description"),
                                    filterResultSet.getString("Name"),
                                    filterResultSet.getString("ProductAttribute"),
                                    filterResultSet.getLong("Min"),
                                    filterResultSet.getLong("Max")
                            );
                        }
                        break;
                    case "Time":
                        filterResultSet = getSpecificFilter(connection, "get_time_filter", id);
                        if (filterResultSet.next()){
                            dbRangeFilter = new TimeFilter(
                                    filterResultSet.getInt("FilterId"),
                                    filterResultSet.getString("Description"),
                                    filterResultSet.getString("Name"),
                                    filterResultSet.getString("ProductAttribute"),
                                    filterResultSet.getTimestamp("Min").toInstant(),
                                    filterResultSet.getTimestamp("Max").toInstant()
                            );
                        }
                        break;
                    default:
                        throw new UnknownFilterTypeException("The filter type retrieved from the database, does not match implemented types");
                }

                //noinspection StatementWithEmptyBody
                if (filterResultSet.next()){
                    // This means we somehow got 2 filters returned
//                throw up;
                    //commented because: pseudocode
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbRangeFilter;
    }

    private ResultSet getSpecificFilter(Connection conn, String sqlFunction, int id) throws SQLException {
        PreparedStatement getDoubleFilterStatement = conn.prepareStatement("SELECT * from " + sqlFunction + "(?);");
        getDoubleFilterStatement.setInt(1, id);
        return getDoubleFilterStatement.executeQuery();
    }

    @Override
    public RangeFilter update(RangeFilter filter) {
        return null;
    }

    @Override
    public RangeFilter delete(int id) {
        return null;
    }


    @Override
    public List<RangeFilter> readAllFilters() {
        return null;
    }
}
