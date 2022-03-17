package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        PreparedStatement statement = null;
        ArrayList<Object> statementList = new ArrayList<>(){{
            add(UUID.randomUUID());
            add(synonym);
        }};
        try {
            statement = conn.prepareStatement("INSERT INTO twoway_synonym_temp(uuid, synonym) VALUES (?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        prepareStatement(statement, statementList);
        executeStatement(statement);

        // TODO: Return actual UUID
        return UUID.randomUUID();
    }

    /**
     * Add a new synonym to an existing synonym group.
     * @param synonym       Word to add
     * @param groupMember   Existing related Synonym
     * @return              UUID of created Synonym
     */
    @Override
    public UUID create(String synonym, String groupMember) {
        PreparedStatement statement = null;

        // TODO: Get GroupMemeber group_id
        ArrayList<Object> statementList = new ArrayList<>(){{
            add(UUID.randomUUID());
            add(synonym);
            add(groupMember);
        }};

        try {
            statement = conn.prepareStatement("INSERT INTO twoway_synonym_temp(uuid, synonym, group_id) VALUES (?, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        prepareStatement(statement, statementList);
        executeStatement(statement);
        // TODO: Return actual UUID
        return UUID.randomUUID();
    }

    /**
     * Retrieve the data from the database related to the given synonym
     * @param synonym   Word to receive synonyms for
     * @return          ResultSet of all matching synonyms
     */
    @Override
    public ResultSet read(String synonym) {
        return null;
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
        PreparedStatement statement = null;

        try{
            statement = conn.prepareStatement("DELETE FROM twoway_synonym_temp WHERE synonym = ?");

            statement.setObject(1, synonym);

            int row = statement.executeUpdate();

            if(row > 0){
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

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

    private void prepareStatement(PreparedStatement statement, ArrayList<Object> parameters) {
        try {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i+1, parameters.get(i));
            }
        } catch (SQLException throwables) {
            System.out.println("Preparing statement failed");
            throwables.printStackTrace();
        }
    }

    private void executeStatement(PreparedStatement statement) {
        try{
            statement.execute();
        } catch (PSQLException throwables) {
            System.out.println("Your synonym already exists in the database. Note that identical words cannot exist in the database");
        }
        catch (SQLException throwables){
            System.out.println("Couldn't execute statement");
            throwables.printStackTrace();
        }
    }

    private UUID getUUID(ResultSet resultSet) {
        try{
            return (UUID)resultSet.getObject("UUID");
        } catch (SQLException throwables) {
            System.out.println("No UUID found");
            throwables.printStackTrace();
            return null;
        }
    }
}
