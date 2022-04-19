package dk.sdu.se_f22.sortingmodule.category;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CategoryReadConfig {
    Properties result;

    public Properties getPropValues() throws IOException {
        String propFileName = "config.properties";

        try (InputStream inputStream = new FileInputStream(propFileName)) {
            Properties prop = new Properties();

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
            }
            result = prop;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return result;
    }
}
