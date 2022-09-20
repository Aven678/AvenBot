package fr.aven.bot.util.music

import java.time.Duration

class FormatUtils
{
    companion object {
        fun formatDuration(millis: Long): String? {
            return formatDuration(Duration.ofMillis(millis))
        }

        fun formatDuration(duration: Duration): String? {
            require(!duration.isNegative) { "duration must be positive" }
            return if (duration.toHours() > 0) {
                java.lang.String.format(
                    "%d:%02d:%02d",
                    duration.toHoursPart(),
                    duration.toMinutesPart(),
                    duration.toSecondsPart()
                )
            } else java.lang.String.format("%d:%02d", duration.toMinutesPart(), duration.toSecondsPart())
        }
    }
}