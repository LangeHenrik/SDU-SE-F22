package dk.sdu.se_f22.searchmodule.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DelimiterSettings {
    private List<String> delimiters;
    private Connection connection = null;
    private String databaseName;
    private String username;
    private String password;
    static DelimiterSettings instance;

    private DelimiterSettings(){
        initializeConnection();
    }

    public static DelimiterSettings getInstance(){

        if (instance == null) {
            instance = new DelimiterSettings();
        }
        return instance;
    }

    public void initializeConnection(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+databaseName,username,password);

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }
    public List<String> getDelimiters() {
        return delimiters;
    }

    public void appendDelimiters(String delimiter) {
        this.delimiters.add(delimiter);
    }
}
