package fr.aven.bot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {

    private static final Pattern LETTER_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]");

    public static long parseTimeToMillis(String str) {
        final String normalizedText = str.replace(" ", "").toLowerCase();

        final Pattern pattern = Pattern.compile("[0-9]+[a-z]");
        final Matcher matcher = pattern.matcher(normalizedText);

        final List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }

        if (!String.join("", matches).equals(normalizedText)) {
            throw new IllegalArgumentException(str + " is not a valid time format.");
        }

        long seconds = 0;

        for (final String match : matches) {
            final long time = Long.parseLong(LETTER_PATTERN.matcher(match).replaceAll(""));
            final String unit = NUMBER_PATTERN.matcher(match).replaceAll("");
            switch (unit) {
                case "s":
                    seconds += TimeUnit.SECONDS.toMillis(time);
                    break;
                case "m":
                    seconds += TimeUnit.MINUTES.toMillis(time);
                    break;
                case "h":
                    seconds += TimeUnit.HOURS.toMillis(time);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown unit: " + unit);
            }
        }

        return seconds;
    }
}
