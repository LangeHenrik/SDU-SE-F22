package dk.sdu.se_f22.contentmodule.infrastructure.data;

public interface DatabaseInterface {

    void setupDatabase();

    void executeQuery(String query);
}