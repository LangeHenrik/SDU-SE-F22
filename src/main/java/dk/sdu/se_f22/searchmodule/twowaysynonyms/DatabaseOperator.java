package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public interface DatabaseOperator extends Filterable {
    String create(String synonym);
    String create(String synonym, String groupMember);
    Synonym read(String synonym);
    ArrayList<Synonym> readAll(String synonym);
    String updateGroupID(String synonym, String relatedSynonym);
    String updateSpelling(String synonym, String correctedSpelling);
    boolean delete(String synonym);
}

/**
 * This interface should reside in the SearchModule -> Infrastructure Module
 * Represents a temporary Interface for integration
 */
interface Filterable {
    ArrayList<String> filter(ArrayList<String> tokens);
}