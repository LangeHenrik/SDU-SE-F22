package dk.sdu.se_f22.brandmodule.management.services;

import static org.junit.jupiter.api.Assertions.*;
import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.Timer;
import java.util.TimerTask;

public class IndexingServiceTest {
    IIndexingService indexingService;
    Persistence p = new Persistence();

    @BeforeAll
    static void setup() {

    }

    @Test
    void indexingIntervalTimeTest() throws InterruptedException {
        // Variables that are used in the TimerTask
        final long[] start = new long[1];
        final long[] end = new long[1];
        final int[] counter = { 0 };
        final boolean[] isRunning = {true};
        final int indexInterval = 100;

        // Timer
        Timer updateIndex = new Timer();
        updateIndex.schedule(new TimerTask() {
            @Override
            public void run() {
                // System.out.println(p.getIndexingInterval());
                if (counter[0] == 0) {
                    start[0] = System.currentTimeMillis();
                    counter[0] += 1;

                } else {
                    if (counter[0] == 1) {
                        end[0] = System.currentTimeMillis();
                        isRunning[0]=false;
                    }
                    counter[0] += 1;
                }
                // Ensure that brands are indexed in regular intervals
                // p.BIM2.indexBrands(p.getAllBrands());

            }
        }, 0, indexInterval);

        // Delay to test the thread
        while(isRunning[0]){
           int a = 0;
        }
        /*for (int i = 0; i < 7500; i++) {
            for (int j = 0; j < 1000; j++) {
                for (int k = 0; k <  500; k++) {
                }
            }
        }*/

        // Check if it is right (including a buffer of 1/4th of the indexing interval
        // ms)
        int buffer = indexInterval / 4;

        try {
            assertTrue(
                    (indexInterval - buffer) < (end[0] - start[0]) && (end[0] - start[0]) < (indexInterval + buffer));
        } catch (AssertionError e) {
            System.out.println("ms_interval: " + (end[0] - start[0]) + " l_boundary: " + (indexInterval - buffer)
                    + " u_boundary: " + (indexInterval + buffer));
            e.printStackTrace();
            assertFalse(true);
        }
    }

}
