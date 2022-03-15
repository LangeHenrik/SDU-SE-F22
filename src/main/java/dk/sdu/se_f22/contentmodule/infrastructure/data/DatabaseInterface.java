package dk.sdu.se_f22.contentmodule.infrastructure.data;

import java.util.ArrayList;

public interface DatabaseInterface {
    void setupDatabase();

    void createTable(String table_name);

    void saveTokens(ArrayList<String> tokens);

    ArrayList<String> loadTokens();

    void saveFilteredTokens(ArrayList<String> tokens);

    ArrayList<String> loadFilteredTokens();
}
