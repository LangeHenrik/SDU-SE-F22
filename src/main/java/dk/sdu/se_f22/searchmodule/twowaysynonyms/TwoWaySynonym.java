package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class TwoWaySynonym implements DatabaseOperator {
    private TwoWaySynonym() {}
    public static TwoWaySynonym getInstance() {
        return TwoWaySynonymHolder.INSTANCE;
    }

    private static class TwoWaySynonymHolder {
        private static final TwoWaySynonym INSTANCE = new TwoWaySynonym();
    }

    /**
     * Add a new synonym and create a new synonym group.
     *
     * @param synonym Word to add
     * @return UUID of created Synonym
     */
    @Override
    public UUID create(String synonym) {
        PreparedStatement statement;
        ArrayList<Object> statementList = new ArrayList<>(){{
            add(UUID.randomUUID());
            add(synonym);
        }};

        statement = Foo.prepareStatement("INSERT INTO BAR(UUID, name) VALUES (?, ?)");
        prepareStatement(statement, statementList);
        executeStatement(statement);

        return getUUID(read(synonym));
    }

    /**
     * Add a new synonym to an existing synonym group.
     *
     * @param synonym     Word to add
     * @param groupMember Existing related Synonym
     * @return UUID of created Synonym
     */
    @Override
    public UUID create(String synonym, String groupMember) {
        return null;
    }

    /**
     * Retrieve the data from the database related to the given synonym
     *
     * @param synonym Word to receive synonyms for
     * @return ResultSet of all matching synonyms
     */
    @Override
    public ResultSet read(String synonym) {
        return null;
    }

    /**
     * Update a synonyms group ID
     *
     * @param synonym        Word to update
     * @param relatedSynonym Related Synonym
     * @return UUID of updated Synonym
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
         * @return Boolean value
         */
        @Override
        public boolean delete (String synonym){
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

    private void prepareStatement (PreparedStatement statement, ArrayList<Object> parameters){
        try {
            for (int i = 1; i < parameters.size(); i++) {
                statement.setObject(i, parameters.get(i-1));
            }
        } catch (SQLException throwables) {
            System.out.println("Preparing statement failed");
            throwables.printStackTrace();
        }
    }

    private void executeStatement (PreparedStatement statement) {
        try{
            statement.execute();
        } catch (SQLException throwables){
            System.out.println("Couldn't execute statement");
            throwables.printStackTrace();
        }
    }

    private UUID getUUID (ResultSet resultSet){
        try{
            return (UUID)resultSet.getObject("UUID");
        } catch (SQLException throwables) {
            System.out.println("No UUID found");
            throwables.printStackTrace();
            return null;
        }
    }
}
