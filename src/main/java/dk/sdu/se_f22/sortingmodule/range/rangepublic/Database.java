package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.Instant;
import java.util.List;

public class Database implements DatabaseInterface {

    Connection connection = DBConnection.getConnection();

    /** This method will take a filter of type RangeFilter and create one of the 3 LongFilter, DoubleFilter, TimeFilter
     *
     * @param filter The filter that needs to be saved in the database
     * @return An instance of the filter created, else if the saving to the database fails it returns an exception
     */
    @Override
    public RangeFilter create(RangeFilter filter) throws InvalidFilterTypeException, SQLException {
        ResultSet queryResult;
        int ID;

        switch (filter.getType()) {
            case DOUBLE -> {
                PreparedStatement doubleStatement = connection.prepareStatement(
                        "INSERT INTO SortingRangeDoubleView (name, description, productAttribute, min, max) VALUES (?, ?, ?, ?, ?);",
                        Statement.RETURN_GENERATED_KEYS
                );
                doubleStatement.setString(1, filter.getName());
                doubleStatement.setString(2, filter.getDescription());
                doubleStatement.setString(3, filter.getProductAttribute());
                doubleStatement.setDouble(4, filter.getDbMinDouble());
                doubleStatement.setDouble(5, filter.getDbMaxDouble());
                doubleStatement.executeQuery();
                queryResult = doubleStatement.getGeneratedKeys();
            }
            case LONG -> {
                PreparedStatement longStatement = connection.prepareStatement(
                        "INSERT INTO SortingRangeLongView (name, description, productAttribute, min, max) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                longStatement.setString(1, filter.getName());
                longStatement.setString(2, filter.getDescription());
                longStatement.setString(3, filter.getProductAttribute());
                longStatement.setLong(4, filter.getDbMinLong());
                longStatement.setLong(5, filter.getDbMaxLong());
                longStatement.executeQuery();
                queryResult = longStatement.getGeneratedKeys();
            }
            case INSTANT -> {
                PreparedStatement timeStatement = connection.prepareStatement(
                        "INSERT INTO SortingRangeInstantView (name, description, productAttribute, min, max) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                timeStatement.setString(1, filter.getName());
                timeStatement.setString(2, filter.getDescription());
                timeStatement.setString(3, filter.getProductAttribute());
                timeStatement.setString(4, filter.getDbMinInstant().toString());
                timeStatement.setString(5, filter.getDbMaxInstant().toString());
                timeStatement.executeQuery();
                queryResult = timeStatement.getGeneratedKeys();
            }
            default -> throw new InvalidFilterTypeException("Didn't match any of our builtin RangeFilter types.");
        }
        if (!queryResult.next()){
            throw new SQLException("No ID to return.");
        }
        ID = queryResult.getInt(1);

        return createFilterWithID(filter, ID);

    }


    /** This method will search the database for a filter matching the id given, and will return a representation of this filter.
     *
     * @param id The id to query for in the database
     * @return an instance of {@link RangeFilter} if successful otherwise null, if the result contained either no filter with this id or contained more than 1 (black magic, it can't)
     */
    @Override
    public RangeFilter read(int id) throws UnknownFilterTypeException {
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
                System.out.println(typeResult.getString(1));
                //noinspection EnhancedSwitchMigration
                switch (typeResult.getString(1)){
                    case "Double":
                        filterResultSet = getSpecificFilter(connection, "get_double_filter", id);
                        if (filterResultSet.next()){
                            dbRangeFilter = new DoubleFilter(
                                    filterResultSet.getInt("FilterId"),
                                    filterResultSet.getString("Name"),
                                    filterResultSet.getString("Description"),
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
                                    filterResultSet.getString("Name"),
                                    filterResultSet.getString("Description"),
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
                                    filterResultSet.getString("Name"),
                                    filterResultSet.getString("Description"),
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

    RangeFilter createFilterWithID(RangeFilter filter, int id) throws InvalidFilterTypeException {
        switch (filter.getType()){
            case DOUBLE -> {
                return new DoubleFilter(id, filter.getName(), filter.getDescription(), filter.getProductAttribute(), filter.getDbMinDouble(), filter.getDbMaxDouble());
            }
            case LONG -> {
                return new LongFilter(id, filter.getName(), filter.getDescription(), filter.getProductAttribute(), filter.getDbMinLong(), filter.getDbMaxLong());
            }
            case INSTANT -> {
                return new TimeFilter(id, filter.getName(), filter.getDescription(), filter.getProductAttribute(), filter.getDbMinInstant(), filter.getDbMaxInstant());
            }
            default -> {
                throw new InvalidFilterTypeException("Didn't match any of our builtin RangeFilter types.");
            }
        }
    }

}
