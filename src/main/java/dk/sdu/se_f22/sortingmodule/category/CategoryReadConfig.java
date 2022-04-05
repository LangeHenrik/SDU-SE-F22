package dk.sdu.se_f22.sortingmodule.category;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CategoryReadConfig {
    HashMap<String, String> result = new HashMap<String, String>();

    public HashMap<String, String> getPropValues() throws IOException {
        String propFileName = "config.properties";

        try (InputStream inputStream = new FileInputStream(propFileName)) {
            Properties prop = new Properties();

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            String urlValue = prop.getProperty("db_url");
            String usernameValue = prop.getProperty("db_user");
            String passwordValue = prop.getProperty("db_password");

            result.put("db_url", urlValue);
            result.put("db_user", usernameValue);
            result.put("db_password", passwordValue);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return result;
    }
}
