package fr.aven.bot.util;

import org.jetbrains.annotations.Nullable;

public class NumberUtils
{
    @Nullable
    public static Long toLongOrNull(@Nullable String str) {
        if (str == null) {
            return null;
        }

        try {
            return Long.parseLong(str.trim());
        } catch (final NumberFormatException err) {
            return null;
        }
    }

    /**
     * @param str The string to parse as a positive long number, may be {@code null}.
     * @return The string parsed as a positive long number or {@code null} if the string is not a valid representation
     * of a number.
     */
    @Nullable
    public static Long toPositiveLongOrNull(@Nullable String str) {
        final Long value = NumberUtils.toLongOrNull(str);
        if (value == null || value <= 0) {
            return null;
        }
        return value;
    }

    public static long truncateBetween(long num, long min, long max) {
        return Math.max(min, Math.min(num, max));
    }
}
