package org.mcservernetwork.client.util;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static String parse(long millis) {
        if (millis == -1) {
            return "never";
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if (minutes > 0)
            return String.format("%dm %ds ago", minutes, seconds);
        else return String.format("%ds ago", seconds);
    }

}
