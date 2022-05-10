package dk.sdu.se_f22.sortingmodule.range.rangepublic;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.range.exceptions.IdNotFoundException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.InvalidFilterTypeException;
import dk.sdu.se_f22.sortingmodule.range.exceptions.UnknownFilterTypeException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements DatabaseInterface {
    private final String[] queryAttributes = {"filterid", "name", "description", "productattribute", "min", "max"};

    /**
     * This method will take a filter of type RangeFilter and create one of the 3 LongFilter, DoubleFilter, TimeFilter
     *
     * @param filter The filter that needs to be saved in the database
     * @return An instance of the filter created, else if the saving to the database fails it returns an exception
     */
    @Override
    public RangeFilter create(RangeFilter filter) throws InvalidFilterTypeException, SQLException {
        ResultSet queryResult = null;
        ResultSet keys = null;
        PreparedStatement statement;
        int results = 0;
        int ID = 0;

        // By using try-with-ressources we do not have to manually handle closing the connection
        // Thanks AutoCloseable
        try(Connection connection = DBConnection.getPooledConnection()) {
            switch (filter.getType()) {
                case DOUBLE -> {
                    statement = connection.prepareStatement(
                            "INSERT INTO SortingRangeDoubleView (name, description, productAttribute, min, max) VALUES (?, ?, ?, ?, ?);",
                            queryAttributes
                    );
                    statement.setString(1, filter.getName());
                    statement.setString(2, filter.getDescription());
                    statement.setString(3, filter.getProductAttribute());
                    statement.setDouble(4, filter.getDbMinDouble());
                    statement.setDouble(5, filter.getDbMaxDouble());
                }
                case LONG -> {
                    statement = connection.prepareStatement(
                            "INSERT INTO SortingRangeLongView (name, description, productAttribute, min, max) VALUES (?, ?, ?, ?, ?)",
                            queryAttributes
                    );
                    statement.setString(1, filter.getName());
                    statement.setString(2, filter.getDescription());
                    statement.setString(3, filter.getProductAttribute());
                    statement.setLong(4, filter.getDbMinLong());
                    statement.setLong(5, filter.getDbMaxLong());
                }
                case TIME -> {
                    statement = connection.prepareStatement(
                            "INSERT INTO SortingRangeTimeView (name, description, productAttribute, min, max) VALUES (?, ?, ?, ?, ?)",
                            queryAttributes
                    );
                    statement.setString(1, filter.getName());
                    statement.setString(2, filter.getDescription());
                    statement.setString(3, filter.getProductAttribute());
                    statement.setTimestamp(4, Timestamp.from(filter.getDbMinInstant()));
                    statement.setTimestamp(5, Timestamp.from(filter.getDbMaxInstant()));
                }
                default -> throw new InvalidFilterTypeException("Didn't match any of our builtin RangeFilter types.");
            }

            results = statement.executeUpdate();
            keys = statement.getGeneratedKeys();

            if (results == 0) {
                throw new SQLException("No ID to return.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int created_filter_id = generatedKeys.getInt("filterid");

            return createFilterWithID(filter, created_filter_id); // Maybe returns as the specific filter type?
        }
    }


    /**
     * This method will search the database for a filter matching the id given, and will return a representation of this filter.
     *
     * @param id The id to query for in the database
     * @return an instance of {@link RangeFilter} if successful otherwise null, if the result contained either no filter with this id or contained more than 1 (black magic, it can't)
     */
    @Override
    public RangeFilter read(int id) throws UnknownFilterTypeException {
        RangeFilter dbRangeFilter = null;
        try(Connection connection = DBConnection.getPooledConnection()) {
            // First get the type of the filter by calling the stored function:
            // "SELECT get_type_of_filter(filter_id);"
            // where filter_id is the id we are interested in retrieving
            PreparedStatement typeStatement = connection.prepareStatement("SELECT get_type_of_filter(?);");
            typeStatement.setInt(1, id);
            ResultSet typeResult = typeStatement.executeQuery();
            if (typeResult.next()) {
                if (typeResult.getString(1) == null) {
                    return null;
                }
                // Then get the filter from the correct view by using the now known data type
                ResultSet filterResultSet;
                System.out.println(typeResult.getString(1));
                //noinspection EnhancedSwitchMigration
                switch (typeResult.getString(1)) {
                    case "Double":
                        filterResultSet = getSpecificFilter(connection, "get_double_filter", id);
                        if (filterResultSet.next()) {
                            dbRangeFilter = createDoubleFilterFromResultset(filterResultSet);
                        }
                        break;
                    case "Long":
                        filterResultSet = getSpecificFilter(connection, "get_long_filter", id);
                        if (filterResultSet.next()) {
                            dbRangeFilter = createLongFilterFromResultset(filterResultSet);
                        }
                        break;
                    case "Time":
                        filterResultSet = getSpecificFilter(connection, "get_time_filter", id);
                        if (filterResultSet.next()) {
                            dbRangeFilter = createTimeFilterFromResultset(filterResultSet);
                        }
                        break;
                    default:
                        throw new UnknownFilterTypeException("The filter type retrieved from the database, does not match implemented types");
                }

                //noinspection StatementWithEmptyBody
                if (filterResultSet.next()) {
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

    private LongFilter createLongFilterFromResultset(ResultSet filterResultSet) throws SQLException {
        return new LongFilter(
                filterResultSet.getInt("FilterId"),
                filterResultSet.getString("Name"),
                filterResultSet.getString("Description"),
                filterResultSet.getString("ProductAttribute"),
                filterResultSet.getLong("Min"),
                filterResultSet.getLong("Max")
        );
    }

    private TimeFilter createTimeFilterFromResultset(ResultSet filterResultSet) throws SQLException {
        return new TimeFilter(
                filterResultSet.getInt("FilterId"),
                filterResultSet.getString("Name"),
                filterResultSet.getString("Description"),
                filterResultSet.getString("ProductAttribute"),
                filterResultSet.getTimestamp("Min").toInstant(),
                filterResultSet.getTimestamp("Max").toInstant()
        );
    }

    private DoubleFilter createDoubleFilterFromResultset(ResultSet filterResultSet) throws SQLException {
        System.out.println(filterResultSet.getInt("FilterId") + " " + filterResultSet.getString("Name"));
        return new DoubleFilter(
                filterResultSet.getInt("FilterId"),
                filterResultSet.getString("Name"),
                filterResultSet.getString("Description"),
                filterResultSet.getString("ProductAttribute"),
                //The below two lines assume the filter read is a Double filter
                filterResultSet.getDouble("Min"),
                filterResultSet.getDouble("Max")
        );
    }

    private ResultSet getSpecificFilter(Connection conn, String sqlFunction, int id) throws SQLException {
        PreparedStatement getDoubleFilterStatement = conn.prepareStatement("SELECT * from " + sqlFunction + "(?);");
        getDoubleFilterStatement.setInt(1, id);
        return getDoubleFilterStatement.executeQuery();
    }

    @Override
    public RangeFilter update(RangeFilter filter) throws SQLException, InvalidFilterTypeException {
        try(Connection connection = DBConnection.getPooledConnection()) {

            PreparedStatement statement = null;

            switch (filter.getType()) {
                case DOUBLE -> {
                    statement = connection.prepareStatement(
                            "UPDATE SortingRangeDoubleView SET name=?, description=?, productAttribute=?, min=?, max=? WHERE (filterId = ?);",
                            new String[]{"filterid", "name", "description", "productattribute", "min", "max"}
                    );
                    statement.setString(1, filter.getName());
                    statement.setString(2, filter.getDescription());
                    statement.setString(3, filter.getProductAttribute());
                    statement.setDouble(4, filter.getDbMinDouble());
                    statement.setDouble(5, filter.getDbMaxDouble());
                    statement.setInt(6, filter.getId());
                }
                case LONG -> {
                    statement = connection.prepareStatement(
                            "UPDATE SortingRangeLongView SET name=?, description=?, productAttribute=?, min=?, max=? WHERE (filterId = ?);",
                            new String[]{"filterid", "name", "description", "productattribute", "min", "max"}
                    );
                    statement.setString(1, filter.getName());
                    statement.setString(2, filter.getDescription());
                    statement.setString(3, filter.getProductAttribute());
                    statement.setLong(4, filter.getDbMinLong());
                    statement.setLong(5, filter.getDbMaxLong());
                    statement.setInt(6, filter.getId());

                }
                case TIME -> {
                    statement = connection.prepareStatement(
                            "UPDATE SortingRangeTimeView SET name=?, description=?, productAttribute=?, min=?, max=? WHERE (filterId = ?);",
                            new String[]{"filterid", "name", "description", "productattribute", "min", "max"}
                    );
                    statement.setString(1, filter.getName());
                    statement.setString(2, filter.getDescription());
                    statement.setString(3, filter.getProductAttribute());
                    statement.setTimestamp(4, Timestamp.from(filter.getDbMinInstant()));
                    statement.setTimestamp(5, Timestamp.from(filter.getDbMaxInstant()));
                    statement.setInt(6, filter.getId());
                }
                default -> throw new InvalidFilterTypeException("Didn't match any of our builtin RangeFilter types.");
            }
            int results = statement.executeUpdate();
            if (results < 1) {
                System.out.println(filter);
                throw new SQLException("Nothing updated");
            }

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return switch (filter.getType()) {
                    case DOUBLE -> createDoubleFilterFromResultset(resultSet);
                    case LONG -> createLongFilterFromResultset(resultSet);
                    case TIME -> createTimeFilterFromResultset(resultSet);
                };
            }
            return null;
        }
    }

    @Override
    public RangeFilter delete(int id) throws UnknownFilterTypeException, IdNotFoundException {

        RangeFilter dbRangeFilter = read(id);
        if (dbRangeFilter == null){
            throw new IdNotFoundException("Couldn't find a filter with this ID: " + id);
        }

        PreparedStatement statement = null;
        try(Connection connection = DBConnection.getPooledConnection()) {
            switch (dbRangeFilter.getType()) {
                // IDEA: Refactor so that the switch determines the name of the view to delete from.
                // That can then be put into SQL string below.
                // It seems stupid to do, but is okay since we have full control of the variable,
                // such that this below is not inherently dangerous to injection:
//                String selectedView = "";
                // perform switch setting selectedView
//                "DELETE FROM " + selectedView + " WHERE filterId = ?;"
                // of course wrapped in the prepare statement such that:
//                statement = connection
//                        .prepareStatement(
//                                "DELETE FROM " + selectedView + " WHERE filterId = ?;",
//                                queryAttributes
//                        );
                // This approach is less error prone since it ensures that the sql statement is written once.
                // Additionally, it becomes more readable (and a tiny bit shorter).
                // - Thor
                // (p.s. if you have read this but disagree, do not let the comment stick around, it looks ugly in Pull Requests)
                case DOUBLE -> statement = connection
                        .prepareStatement(
                                "DELETE FROM sortingrangedoubleview WHERE filterId = ?;",
                                queryAttributes
                        );
                case LONG -> statement = connection
                            .prepareStatement(
                                    "DELETE FROM sortingrangelongview WHERE filterId = ?;",
                                    queryAttributes
                            );
                case TIME -> statement = connection
                            .prepareStatement(
                                    "DELETE FROM sortingrangetimeview WHERE filterId = ?;",
                                    queryAttributes
                            );
            }

            statement.setInt(1, id);
            int result_count = statement.executeUpdate();
            if (result_count < 1){
                throw new SQLException("Didn't delete filter");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return dbRangeFilter;
    }


    @Override
    public List<RangeFilter> readAllFilters() {
        List<RangeFilter> outList = new ArrayList<>();
        try(Connection connection = DBConnection.getPooledConnection()) {

            // it is deliberate that they are all in the same try catch block since if one fails, they will all fail.
            // This is desired behaviour, and also mimicks what would happen for the case where we add the exception to the method signature.

            ResultSet doubleFilters = connection.prepareStatement("SELECT * from sortingrangeDOUBLEview").executeQuery();
            while (doubleFilters.next()) {
                outList.add(
                        createDoubleFilterFromResultset(doubleFilters)
                );
            }

            ResultSet longFilters = connection.prepareStatement("SELECT * from sortingrangeLONGview").executeQuery();
            while (longFilters.next()) {
                outList.add(
                        createLongFilterFromResultset(longFilters)
                );
            }

            ResultSet timeFilters = connection.prepareStatement("SELECT * from sortingrangeTIMEview").executeQuery();
            while (timeFilters.next()) {
                outList.add(
                        createTimeFilterFromResultset(timeFilters)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // sort the list by id
        outList.sort((o1, o2) -> o1.getId() - o2.getId());

        return outList;
    }

    RangeFilter createFilterWithID(RangeFilter filter, int id) throws InvalidFilterTypeException {
        switch (filter.getType()) {
            case DOUBLE -> {
                return new DoubleFilter(id, filter.getName(), filter.getDescription(), filter.getProductAttribute(), filter.getDbMinDouble(), filter.getDbMaxDouble());
            }
            case LONG -> {
                return new LongFilter(id, filter.getName(), filter.getDescription(), filter.getProductAttribute(), filter.getDbMinLong(), filter.getDbMaxLong());
            }
            case TIME -> {
                return new TimeFilter(id, filter.getName(), filter.getDescription(), filter.getProductAttribute(), filter.getDbMinInstant(), filter.getDbMaxInstant());
            }
            default -> throw new InvalidFilterTypeException("Didn't match any of our builtin RangeFilter types.");
        }
    }
}
