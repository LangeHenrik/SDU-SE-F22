package dk.sdu.se_f22.sortingmodule.infrastructure.data;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import dk.sdu.se_f22.sortingmodule.infrastructure.domain.SearchQuery;
import dk.sdu.se_f22.sharedlibrary.utils.Color;
import dk.sdu.se_f22.sortingmodule.scoring.ScoreSortType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveSearchQuery {
    public static void saveSearch(SearchQuery query, String searchString) {
        int pageNumber = query.getPagination()[0];
        int pageSize = query.getPagination()[1];
        Map<Integer, Double[]> queryRangesDouble = query.getRangeDouble();
        Map<Integer, Long[]> queryRangesLong = query.getRangeLong();
        Map<Integer, Instant[]> queryRangesInstant = query.getRangeInstant();

        ArrayList<Map> queryRanges = new ArrayList<>() {
            {
                add(queryRangesDouble);
                add(queryRangesLong);
                add(queryRangesInstant);
            }
        };

        List<Integer> queryCategories = query.getCategory();
        ScoreSortType queryScoring = query.getScoring();
        String queryString = searchString;

        // Save the information
        try (
            Connection connection = DBConnection.getPooledConnection();
        ) {
            // Disable auto commit to prevent error prone queries to be inserted
            connection.setAutoCommit(false);

            // Main query
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO sorting_queries (text, page, page_size, scoring) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, searchString);
            stmt.setInt(2, pageNumber);
            stmt.setInt(3, pageSize);
            stmt.setString(4, queryScoring.toString());
            stmt.execute();

            // Get the id for the main query
            ResultSet rs = stmt.getGeneratedKeys();
            int mainQueryId = 0;
            if (rs.next()) {
                mainQueryId = rs.getInt(1);
            }
            stmt.close();

            // Range query
            for (HashMap.Entry<Integer, Double[]> range : queryRangesDouble.entrySet()) {
                stmt = connection.prepareStatement(
                        "INSERT INTO sorting_query_ranges (query_id, range_id, start_value, end_value) VALUES (?, ?, ?, ?)"
                );

                stmt.setInt(1, mainQueryId);
                stmt.setInt(2, range.getKey());
                stmt.setString(3, String.valueOf(range.getValue()[0]));
                stmt.setString(4, String.valueOf(range.getValue()[1]));
                stmt.execute();
                stmt.close();
            }

            for (HashMap.Entry<Integer, Long[]> range : queryRangesLong.entrySet()) {
                stmt = connection.prepareStatement(
                        "INSERT INTO sorting_query_ranges (query_id, range_id, start_value, end_value) VALUES (?, ?, ?, ?)"
                );

                stmt.setInt(1, mainQueryId);
                stmt.setInt(2, range.getKey());
                stmt.setString(3, String.valueOf(range.getValue()[0]));
                stmt.setString(4, String.valueOf(range.getValue()[1]));
                stmt.execute();
                stmt.close();
            }

            for (HashMap.Entry<Integer, Instant[]> range : queryRangesInstant.entrySet()) {
                stmt = connection.prepareStatement(
                        "INSERT INTO sorting_query_ranges (query_id, range_id, start_value, end_value) VALUES (?, ?, ?, ?)"
                );

                stmt.setInt(1, mainQueryId);
                stmt.setInt(2, range.getKey());
                stmt.setString(3, String.valueOf(range.getValue()[0]));
                stmt.setString(4, String.valueOf(range.getValue()[1]));
                stmt.execute();
                stmt.close();
            }




            // category query
            for (Integer category : queryCategories) {
                stmt = connection.prepareStatement(
                    "INSERT INTO sorting_query_categories (query_id, catergory_id) VALUES (?, ?)"
                );
                stmt.setInt(1, mainQueryId);
                stmt.setInt(2, category);
                stmt.execute();
                stmt.close();
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