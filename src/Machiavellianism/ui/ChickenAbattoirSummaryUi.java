package Machiavellianism.ui;

import java.awt.*;
import static java.util.concurrent.TimeUnit.*;

/**
 * Class for displaying UI screens for the script.
 */
public class ChickenAbattoirSummaryUi {
    private static final String SUMMARY_TITLE = "Chicken Abattoir";

    /**
     * Shows the summary of the statistics for the chicken abattoir.
     * @param graphics graphics to draw on
     * @param version script version
     * @param startTime start time of the script
     * @param totalChickensSlain total chickens slain
     * @param totalChickenFeathersPickedUp total chicken feathers picked up successfully
     */
    public static void ShowStatisticsSummary(
            Graphics graphics,
            String version,
            long startTime,
            int totalChickensSlain,
            int totalChickenFeathersPickedUp) {
        long runningTime = System.currentTimeMillis() - startTime;

        // Background
        graphics.setColor(new Color(0, 0, 0, 150));
        graphics.drawRect(0, 35, 180, 110);
        graphics.fillRect(0, 35, 180, 110);

        // Title
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.BOLD, 14));
        graphics.drawString(SUMMARY_TITLE, 10, 50);

        // Version
        graphics.setFont(new Font("Arial", Font.PLAIN, 10));
        graphics.drawString("v" + version + " - Machiavellianism", 10, 60);

        graphics.setFont(new Font("Arial", Font.PLAIN, 13));

        // Total picked up feathers
        graphics.drawString("Feathers: " + totalChickenFeathersPickedUp, 10, 85);

        // Total chickens killed
        graphics.drawString("Chickens slain: " + totalChickensSlain, 10, 100);

        // Running time
        int runtime = Integer.parseInt("" + runningTime);
        graphics.drawString("Run time: " + FormatTime(runtime), 10, 140);
    }

    /**
     * Format the duration into a readable string.
     * @param duration milliseconds to format time
     * @return human readable string of the duration
     */
    private static String FormatTime(long duration) {
        long days = MILLISECONDS.toDays(duration);
        long hours = MILLISECONDS.toHours(duration) - DAYS.toHours(MILLISECONDS.toDays(duration));
        long minutes = MILLISECONDS.toMinutes(duration) - HOURS.toMinutes(MILLISECONDS.toHours(duration));
        long seconds = MILLISECONDS.toSeconds(duration) - MINUTES.toSeconds(MILLISECONDS.toMinutes(duration));

        if (days == 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
    }
}
