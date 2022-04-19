package Domain;

import Data.Database;
import Data.Management;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String parsedHTML = null;
        try {
            HTMLParser parser = new HTMLParser("src/main/resources/semesterproject/cms/Test.html");
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