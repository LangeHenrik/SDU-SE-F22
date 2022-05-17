package dk.sdu.se_f22.contentmodule.management.Domain;
import dk.sdu.se_f22.contentmodule.management.Data.*;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String parsedHTML = null;
        try {
            HTMLParser parser = new HTMLParser("src/main/resources/dk/sdu/se_f22/contentmodule/management/Test.html");
            DocumentSorter dc = new DocumentSorter();
            parsedHTML = dc.getBody(parser.getParsedHTML());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Database database = Database.getDatabase();
            Management.Create(parsedHTML);
            Management.updateIndexInterval(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
