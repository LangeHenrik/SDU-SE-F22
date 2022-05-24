package dk.sdu.se_f22.contentmodule.management.Data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Indexing {
    private static Timer timer = new Timer();
    private static TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            System.out.println("Not implemented yet! " + new Date());
        }
    };
    private static long interval;

    public static void scheduleTimer(long interval) {
        timer.scheduleAtFixedRate(timerTask, 0, interval);
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/dk/sdu/se_f22/contentmodule/management/intervals.txt");
            String text = "New index update: "+ interval + " at: "+ new Date();
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static long getInterval() {
        return interval;
    }
}
