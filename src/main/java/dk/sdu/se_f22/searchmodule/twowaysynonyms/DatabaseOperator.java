package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public interface DatabaseOperator extends Filterable {
    UUID create(String synonym);
    UUID create(String synonym, String groupMember);
    ResultSet read(String synonym);
    ResultSet readAll(String synonym);
    UUID updateGroupID(String synonym, String relatedSynonym);
    UUID updateSpelling(String synonym, String correctedSpelling);
    boolean delete(String synonym);
}

/**
 * This interface should reside in the SearchModule -> Infrastructure Module
 * Represents a temporary Interface for integration
 */
interface Filterable {
    ArrayList<String> filter(ArrayList<String> tokens);
}