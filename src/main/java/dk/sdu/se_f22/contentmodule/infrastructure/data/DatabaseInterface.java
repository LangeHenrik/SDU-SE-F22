package dk.sdu.se_f22.contentmodule.infrastructure.data;

import java.util.ArrayList;

public interface DatabaseInterface {

    void createTable(String table_name);

    void saveTokens(String table_name, ArrayList<String> tokens);

    ArrayList<String> loadTokens(String table_name);
}
