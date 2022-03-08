package dk.sdu.se_f22;

import dk.sdu.se_f22.sharedlibrary.db.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Example: Get connection from DB Connection class
        Connection conn = DBConnection.getConnection();
    }
}
