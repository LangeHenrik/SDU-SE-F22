package dk.sdu.se_f22.brandmodule.management.services;

import static org.junit.jupiter.api.Assertions.*;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import dk.sdu.se_f22.brandmodule.management.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
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
    void StartIndexInterval() throws InterruptedException {
        // Variables that are used in the TimerTask
        final long[] start = new long[1];
        final long[] end = new long[1];
        final int[] counter = {0};

        // Timer
        Timer updateIndex = new Timer();
        updateIndex.schedule(new TimerTask() {
            @Override
            public void run() {
                //  System.out.println(p.getIndexingInterval());
                if (counter[0] == 0) {
                    start[0] = System.currentTimeMillis();
                    counter[0] += 1;

                } else {
                    if (counter[0] == 1) {
                        end[0] = System.currentTimeMillis();
                    }
                    counter[0] += 1;
                }
                //Ensure that brands are indexed in regular intervals
                //  p.BIM2.indexBrands(p.getAllBrands());

            }
        }, 0, p.getIndexingInterval());


        // Delay to make the test work 80% of the time
        for (int i = 0; i < 300000; i++) {
            for (int j = 0; j < 2500; j++) {
                for (int k = 0; k < 10000; k++) {
                }
            }
        }
        // Check if it is right
        assertEquals(p.getIndexingInterval(), end[0] - start[0]);
    }
}

