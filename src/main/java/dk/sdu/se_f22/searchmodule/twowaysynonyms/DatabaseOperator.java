package dk.sdu.se_f22.searchmodule.twowaysynonyms;

import dk.sdu.se_f22.searchmodule.infrastructure.interfaces.Filterable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public interface DatabaseOperator extends Filterable {
    String create(String synonym);
    String create(String synonym, String groupMember);
    Synonym read(String synonym);
    ArrayList<Synonym> readAll(int groupId);
    boolean updateGroupID(String synonym, String relatedSynonym);
    boolean updateSpelling(String synonym, String correctedSpelling);
    boolean delete(String synonym);
}

