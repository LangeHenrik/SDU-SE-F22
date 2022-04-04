package dk.sdu.se_f22.sortingmodule.infrastructure.data;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SearchQuery;
import dk.sdu.se_f22.sharedlibrary.utils.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SaveSearchQuery {
    public static void saveSearch(SearchQuery query, String searchString) {
        int pageNumber = query.getPagination()[0];
        int pageSize = query.getPagination()[1];
        ArrayList<Object> queryRanges = query.getRange();
        ArrayList<Integer> queryCategories = query.getCategory();
        int queryScoring = query.getScoring();
        String queryString = searchString;

        // Save the information
        try (
            Connection connection = DBConnection.getPooledConnection();
        ) {
            // Disable auto commit to prevent error prone quries to be inserted
            connection.setAutoCommit(false);

            // Main query
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO sorting_queries (text, page, page_size, scoring) VALUES (?, ?, ?, ?)", 
                Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, searchString);
            stmt.setInt(2, pageNumber);
            stmt.setInt(3, pageSize);
            stmt.setInt(4, queryScoring);
            stmt.execute();

            // Get the id for the main query
            ResultSet rs = stmt.getGeneratedKeys();
            int mainQueryId = 0;
            if (rs.next()) {
                mainQueryId = rs.getInt(1);
            }
            stmt.close();

            // Range query
            for (Object range : queryRanges) {
                stmt = connection.prepareStatement(
                    "INSERT INTO sorting_query_ranges (query_id, range_id, start_value, end_value) VALUES (?, ?, ?, ?)" 
                );

                // TODO: add data from range instead of empty values

                stmt.setInt(1, mainQueryId);
                stmt.setInt(2, 0);
                stmt.setDouble(3, 0.0);
                stmt.setDouble(4, 0.0);
                stmt.execute();
            }
            
            // category query
            for (Integer category : queryCategories) {
                stmt = connection.prepareStatement(
                    "INSERT INTO sorting_query_categories (query_id, catergory_id) VALUES (?, ?)"
                );
                stmt.setInt(1, mainQueryId);
                stmt.setInt(2, category);
                stmt.execute();
            }
            
            connection.setAutoCommit(true);

        } catch (SQLException error) {
            System.out.println(Color.YELLOW + "An error occured when logging query data to the database:" + Color.RED);
            System.out.println(error.getMessage());
            error.printStackTrace();
            System.out.println(Color.YELLOW_BOLD_BRIGHT + "This is not a crash, the program will move on!" + Color.RESET);
        }
    }
}