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
    public UUID create(String synonym) {
        UUID uuid = null;
        PreparedStatement statement = null;
        ArrayList<Object> args = new ArrayList<>(){{
            add(UUID.randomUUID());
            add(synonym);
        }};
        try {
            statement = conn.prepareStatement(
                    "INSERT INTO twoway_synonym(uuid, synonym) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            prepareStatement(statement, args);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                uuid = (UUID)rs.getObject(1);
                System.out.println("Created synonoym with UUID: " + uuid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    /**
     * Add a new synonym to an existing synonym group.
     * @param synonym       Word to add
     * @param groupMember   Existing related Synonym
     * @return              UUID of created Synonym
     */
    @Override
    public UUID create(String synonym, String groupMember) {
        UUID uuid = null;
        PreparedStatement statement = null;

        // TODO: Get GroupMemeber group_id
        ArrayList<Object> args = new ArrayList<>(){{
            add(UUID.randomUUID());
            add(synonym);
            add(groupMember);
        }};
        try {
            statement = conn.prepareStatement(
                    "INSERT INTO twoway_synonym(uuid, synonym, group_id) VALUES (?, ?, ?)"
            );
            prepareStatement(statement, args);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                uuid = (UUID)rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    /**
     * Retrieve the data from the database related to the given synonym
     * @param synonym   Word to receive synonyms for
     * @return          ResultSet of all matching synonyms
     */
    @Override
    public ResultSet readAll(String synonym) {
        String statementFormat = "SELECT * FROM twoway_synonym WHERE synonym = ?";
        ArrayList<Object> args = new ArrayList<>(){{add(synonym);}};

        PreparedStatement stmt =getPreparedStatement(statementFormat, args);
        return readDatabase(stmt);
    }

    /**
     * Retrieve a single synonym from the database
     * @param synonym   Word to search for
     * @return          ResultSet containing the found synonym
     */
    @Override
    public ResultSet read(String synonym) {
        String statementFormat = "SELECT * FROM twoway_synonym WHERE synonym = ?";
        ArrayList<Object> args = new ArrayList<>(){{add(synonym);}};

        PreparedStatement stmt =getPreparedStatement(statementFormat, args);
        return readDatabase(stmt);
    }

    /**
     * Update a synonyms group ID
     * @param synonym           Word to update
     * @param relatedSynonym    Related Synonym
     * @return                  UUID of updated Synonym
     */
    @Override
    public UUID updateGroupID(String synonym, String relatedSynonym) {
        return null;
    }

    /**
     * Update spelling of a synonym
     * @param synonym           Word to update
     * @param correctedSpelling Corrected spelling of Synonym
     * @return                  UUID of updated Synonym
     */
    @Override
    public UUID updateSpelling(String synonym, String correctedSpelling) {
        return null;
    }

    /**
     * Delete a synonym from the database
     * @param synonym           Word to delete
     * @return                  Boolean value
     */
    @Override
    public boolean delete(String synonym) {
        return false;
    }

    /**
     * Goes through a list of tokens, to find all
     * their respective two-way synonyms.
     * @param tokens    List of keywords to find synonyms for
     * @return          List of found synonyms and tokens
     */
    @Override
    public ArrayList<String> filter(ArrayList<String> tokens) {
        return null;
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
     *
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

    private int updateDatabase(PreparedStatement statement){
        try {
            return statement.executeUpdate();

        } catch (SQLTimeoutException throwables){
            System.out.println("Driver timed out, statement execution took too long");
            return 0;
        } catch (SQLException throwables){
            System.out.println("Update could not be executed");
            return 0;
        }
    }


    /*private void executeStatement(PreparedStatement statement) {
        try{
            statement.execute();
        } catch (SQLException throwables){
            System.out.println("Couldn't execute statement");
            throwables.printStackTrace();
        }
    }*/
}
