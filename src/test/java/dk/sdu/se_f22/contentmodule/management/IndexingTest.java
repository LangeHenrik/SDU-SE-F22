package dk.sdu.se_f22.contentmodule.management;

import dk.sdu.se_f22.contentmodule.management.Data.*;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class IndexingTest {
    String text = "";

    /*@Test
    void scheduleTimer() {
        Indexing.scheduleTimer(500);
        try (Scanner s = new Scanner(new FileInputStream(("intervals.txt")))) {
            while (s.hasNext()) {
                text = s.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("New index update: " + 500 + " at: " + new Date(), text);
    }*/
}