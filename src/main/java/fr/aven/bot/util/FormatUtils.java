package fr.aven.bot.util;


import java.time.*;
import java.util.stream.Collectors;

public class FormatUtils {

    public static String formatDuration(long millis) {
        return FormatUtils.formatDuration(Duration.ofMillis(millis));
    }

    public static String formatDuration(Duration duration) {
        if (duration.isNegative()) {
            throw new IllegalArgumentException("duration must be positive");
        }
        if (duration.toHours() > 0) {
            return String.format("%d:%02d:%02d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
        }
        return String.format("%d:%02d", duration.toMinutesPart(), duration.toSecondsPart());
    }

}
