package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class TwoWaySynonym implements DatabaseOperator {
    private TwoWaySynonym() {}
    private final Connection conn = DBConnection.getConnection();
    public static TwoWaySynonym getInstance() {
        return TwoWaySynonymHolder.INSTANCE;
    }

    private static class TwoWaySynonymHolder {
        private static final TwoWaySynonym INSTANCE = new TwoWaySynonym();
    }

    /**
     * Add a new synonym and create a new synonym group.
     * @param synonym           Word to add
     * @return                  UUID of created Synonym
     */
    @Override
    public String create(String synonym) {
        String uuid = UUID.randomUUID().toString();
        String format = "INSERT INTO twoway_synonym (uuid, synonym) VALUES (?, ?)";
        ArrayList<Object> args = new ArrayList<>(){{
            add(uuid);
            add(synonym);
        }};
        PreparedStatement statement = getPreparedStatement(format, args);
        return (updateDatabase(statement) > 0) ? uuid : null;
    }

    /**
     * Add a new synonym to an existing synonym group.
     * @param synonym       Word to add
     * @param groupMember   Existing related Synonym
     * @return              UUID of created Synonym
     */
    @Override
    public String create(String synonym, String groupMember) {
        Integer group_id = read(groupMember).groupId();
        String uuid = UUID.randomUUID().toString();
        String format = "INSERT INTO twoway_synonym (uuid, synonym, group_id) VALUES (?, ?, ?)";
        ArrayList<Object> args = new ArrayList<>(){{
            add(uuid);
            add(synonym);
            add(group_id);
        }};
        PreparedStatement statement = getPreparedStatement(format, args);
        return (updateDatabase(statement) > 0) ? uuid : null;
    }

    /**
     * Retrieve the data from the database related to the given synonym
     * @param synonym   Word to receive synonyms for
     * @return          ArrayList of all matching synonyms
     */
    @Override
    public ArrayList<Synonym> readAll(int groupId) {
        String statementFormat = "SELECT * FROM twoway_synonym WHERE group_id = ?";
        ArrayList<Object> args = new ArrayList<>(){{add(groupId);}};
        PreparedStatement stmt = getPreparedStatement(statementFormat, args);
        ResultSet rs = readDatabase(stmt);
        return getSynonyms(rs);
    }

    /**
     * Retrieve a single synonym from the database
     * @param synonym   Word to search for
     * @return          Synonym Record of found synonym
     */
    @Override
    public Synonym read(String synonym) {
        String statementFormat = "SELECT * FROM twoway_synonym WHERE synonym = ?";
        ArrayList<Object> args = new ArrayList<>(){{add(synonym);}};
        PreparedStatement stmt = getPreparedStatement(statementFormat, args);
        ResultSet rs = readDatabase(stmt);
        try {
            if (rs.next()) {
                return new Synonym(
                        rs.getString(1), rs.getString(2), rs.getInt(3)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Update a synonyms group ID
     * @param synonym           Word to update
     * @param relatedSynonym    Related Synonym
     * @return                  UUID of updated Synonym
     */
    @Override
    public boolean updateGroupID(String synonym, String relatedSynonym) {
        Integer group_id = read(relatedSynonym).groupId();
        String format = "UPDATE twoway_synonym SET group_id = ? WHERE synonym = ?";
        ArrayList<Object> args = new ArrayList<>(){{
            add(group_id);
            add(synonym);
        }};
        PreparedStatement statement = getPreparedStatement(format, args);
        return (updateDatabase(statement) > 0);
    }

    /**
     * Update spelling of a synonym
     * @param synonym           Word to update
     * @param correctedSpelling Corrected spelling of Synonym
     * @return                  UUID of updated Synonym
     */
    @Override
    public boolean updateSpelling(String synonym, String correctedSpelling) {
        String statementFormat = "UPDATE twoway_synonym SET synonym = ? WHERE synonym = ?";
        ArrayList<Object> args = new ArrayList<>(){{
            add(correctedSpelling);
            add(synonym);
        }};
        PreparedStatement stmt = getPreparedStatement(statementFormat, args);
        return updateDatabase(stmt) > 0;
    }

    /**
     * Delete a synonym from the database
     * @param synonym           Word to delete
     * @return                  Boolean value
     */
    @Override
    public boolean delete(String synonym) {
        String statementFormat = "DELETE FROM twoway_synonym WHERE synonym = ?";
        ArrayList<Object> args = new ArrayList<>(){{add(synonym);}};
        PreparedStatement stmt = getPreparedStatement(statementFormat, args);
        return updateDatabase(stmt) > 0;
    }

    /**
     * Goes through a list of tokens, to find all
     * their respective two-way synonyms.
     * @param tokens    List of keywords to find synonyms for
     * @return          List of found synonyms and tokens
     */
    @Override
    public ArrayList<String> filter(ArrayList<String> tokens) {
        ArrayList<String> args = new ArrayList<>();
        Set<Integer> unique = new TreeSet<>();

        for(String token : tokens){
            int groupId = read(token).groupId();

            var statement = unique.contains(groupId) ?  null : unique.add(groupId);
        }

        for(Integer groupId : unique){
            readAll(groupId).forEach(s -> args.add(s.synonym()));
        }

        return args;
    }

    /**
     * @param  format        Format of the statement that is to be executed
     * @param  parameters    Params to be mapped in the Query
     * @return               A fully prepared statement if no exception is caught
     */
    private PreparedStatement getPreparedStatement(String format, ArrayList<Object> parameters){
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(format);
            prepareStatement(statement, parameters);
        } catch(SQLException exception){
            System.out.println("Formatting statement failed");
        }
        return statement;
    }

    /**
     * Helper function for preparing statements
     * @param statement     SQL Query
     * @param parameters    Params to be mapped in Query
     */
    private void prepareStatement(PreparedStatement statement, ArrayList<Object> parameters) {
        try {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i+1, parameters.get(i));
            }
        } catch (SQLException throwables) {
            System.out.println("Preparing statement failed");
        }
    }

    /**
     * An execute statement function that queries to the database
     *
     * @param statement     Prepared statement that should be executed
     * @return              returns a resultset from the query
     */
    private ResultSet readDatabase(PreparedStatement statement){
        try {
            return statement.executeQuery();
        } catch (SQLTimeoutException throwables){
            System.out.println("Driver timed out, statement execution took too long");
            return null;
        } catch (SQLException throwables){
            System.out.println("Query could not be executed");
            return null;
        }
    }

    private ArrayList<Synonym> getSynonyms(ResultSet rs, ) {
        ArrayList<Synonym> synonymList = new ArrayList<>();
        try {
            while (rs.next()) {
                synonymList.add(new Synonym(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3)));
            }
            return synonymList;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * An execute statement function that queries to the database
     *
     * @param statement     Prepared statement that should be executed
     * @return              returns an int of effected rows from the query
     */
    private int updateDatabase(PreparedStatement statement){
        try {
            return statement.executeUpdate();

        } catch (SQLTimeoutException throwables){
            System.out.println("Driver timed out, statement execution took too long");
            return 0;
        } catch (SQLException throwables){
            System.out.println(throwables.getMessage());
            System.out.println("Update could not be executed");
            return 0;
        }
    }
}
