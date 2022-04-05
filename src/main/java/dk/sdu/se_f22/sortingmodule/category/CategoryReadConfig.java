package dk.sdu.se_f22.sortingmodule.category;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CategoryReadConfig {
    HashMap<String, String> result = new HashMap<String, String>();
    InputStream inputStream;

    public HashMap<String, String> getPropValues() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            String urlValue = prop.getProperty("db_url");
            String usernameValue = prop.getProperty("db_username");
            String passwordValue = prop.getProperty("db_password");

            result.put("url", urlValue);
            result.put("username", usernameValue);
            result.put("password", passwordValue);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }
}
