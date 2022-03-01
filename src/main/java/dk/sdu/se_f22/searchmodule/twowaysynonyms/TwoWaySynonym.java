package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class TwoWaySynonym implements DatabaseOperator {
    /**
     * Add a new synonym and create a new synonym group.
     * @param synonym           Word to add
     * @return                  UUID of created Synonym
     */
    @Override
    public UUID create(String synonym) {
        return null;
    }

    /**
     * Add a new synonym to an existing synonym group.
     * @param synonym       Word to add
     * @param groupMember   Existing related Synonym
     * @return              UUID of created Synonym
     */
    @Override
    public UUID create(String synonym, String groupMember) {
        return null;
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
     * Update a synonym (mainly spelling correction)
     * @param synonym           Word to update
     * @param correctedSynonym  Corrected form of Synonym
     * @return                  UUID of updated Synonym
     */
    @Override
    public UUID update(String synonym, String correctedSynonym) {
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
}
