package org.slf4j.impl;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BotLogger implements Logger
{
    protected static final SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm:ss");
    protected static final int LOG_LEVEL_TRACE = LocationAwareLogger.TRACE_INT;
    protected static final int LOG_LEVEL_DEBUG = LocationAwareLogger.DEBUG_INT;
    protected static final int LOG_LEVEL_INFO = LocationAwareLogger.INFO_INT;
    protected static final int LOG_LEVEL_WARN = LocationAwareLogger.WARN_INT;
    protected static final int LOG_LEVEL_ERROR = LocationAwareLogger.ERROR_INT;
    protected static final int LOG_LEVEL_CONSOLE = 50;


    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static String lineFormat = "[{DATE}] [{LEVEL}-{NAME}] {MESSAGE}";

    private final BotLoggerFactory source;
    private final String name;

    /** The current log level */
    private boolean debugLevel = false;
    private boolean traceLevel = false;
    private boolean infoLevel = true;
    private boolean warnLevel = true;
    private boolean errorLevel = true;

    public BotLogger(String name, BotLoggerFactory source)
    {
        this.name = name;
        this.source = source;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setTraceLevel(boolean traceLevel)
    {
        this.traceLevel = traceLevel;
    }

    public void setDebugLevel(boolean debugLevel)
    {
        this.debugLevel = debugLevel;
    }

    public void setInfoLevel(boolean infoLevel)
    {
        this.infoLevel = infoLevel;
    }

    public void setWarnLevel(boolean warnLevel)
    {
        this.warnLevel = warnLevel;
    }

    public void setErrorLevel(boolean errorLevel)
    {
        this.errorLevel = errorLevel;
    }

    /**
     * This is our internal implementation for logging regular
     * (non-parameterized) log messages.
     *
     * @param level
     *            One of the LOG_LEVEL_XXX constants defining the log level
     * @param message
     *            The message itself
     * @param t
     *            The exception whose stack trace should be logged
     */
    private void log(int level, String message, Throwable t) {
        if (!isLevelEnabled(level)) {
            return;
        }

        String date = simpleDate.format(new Date());
        String strLevel = renderLevel(level);
        String line = formatLine(date, strLevel, message);

        // Append the message
        write(date, strLevel, level, line, t);
    }

    private String formatLine(String date, String level, String message)
    {
        return lineFormat.replace("{DATE}", date)
                .replace("{LEVEL}", level).replace("{NAME}", computeShortName())
                .replace("{MESSAGE}", String.valueOf(message));
    }

    protected String renderLevel(int level) {
        switch (level) {
            case LOG_LEVEL_TRACE:
                return "TRACE";
            case LOG_LEVEL_DEBUG:
                return ("DEBUG");
            case LOG_LEVEL_INFO:
                return "INFO";
            case LOG_LEVEL_WARN:
                return "WARN";
            case LOG_LEVEL_ERROR:
                return "ERROR";
            case LOG_LEVEL_CONSOLE:
                return "CONSOLE";
        }
        throw new IllegalStateException("Unrecognized level [" + level + "]");
    }

    void write(String date, String strLevel, int level, String buf, Throwable t) {
        PrintStream targetStream = System.out;

        String color = getColorByLevel(level);

        targetStream.println(color + buf);
        source.log(buf);

        writeThrowable(date, strLevel, color, t, targetStream);
        targetStream.flush();
    }

    protected void writeThrowable(String date, String strLevel, String color, Throwable t, PrintStream targetStream) {
        if (t != null) {
            StringBuilder builder = new StringBuilder();

            builder.append(formatLine(date, strLevel, t.toString()));

            for (StackTraceElement traceElement : t.getStackTrace())
                builder.append("\n").append(formatLine(date, strLevel, "\tat "+traceElement.toString()));

            String msg = builder.toString();
            targetStream.println(color + msg);
            source.log(msg);
        }
    }

    private String getColorByLevel(int level)
    {
        switch (level) {
            case LOG_LEVEL_TRACE:
                return BotLogger.ANSI_CYAN;
            case LOG_LEVEL_DEBUG:
                return BotLogger.ANSI_PURPLE;
            case LOG_LEVEL_INFO:
                return BotLogger.ANSI_BLUE;
            case LOG_LEVEL_WARN:
                return BotLogger.ANSI_YELLOW;
            case LOG_LEVEL_ERROR:
                return BotLogger.ANSI_RED;
            case LOG_LEVEL_CONSOLE:
                return BotLogger.ANSI_GREEN;
        }
        return BotLogger.ANSI_WHITE;
    }

    private String getFormattedDate() {
        return simpleDate.format(new Date());
    }

    private String computeShortName() {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * For formatted messages, first substitute arguments and then log.
     *
     * @param level
     * @param format
     * @param arg1
     * @param arg2
     */
    private void formatAndLog(int level, String format, Object arg1, Object arg2) {
        if (!isLevelEnabled(level)) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        log(level, tp.getMessage(), tp.getThrowable());
    }

    /**
     * For formatted messages, first substitute arguments and then log.
     *
     * @param level
     * @param format
     * @param arguments
     *            a list of 3 ore more arguments
     */
    private void formatAndLog(int level, String format, Object... arguments) {
        if (!isLevelEnabled(level)) {
            return;
        }
        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        log(level, tp.getMessage(), tp.getThrowable());
    }

    protected boolean isLevelEnabled(int logLevel) {
        // log level are numerically ordered so can use simple numeric
        // comparison
        switch (logLevel) {
            case LOG_LEVEL_TRACE:
                return traceLevel;
            case LOG_LEVEL_DEBUG:
                return debugLevel;
            case LOG_LEVEL_INFO:
                return infoLevel;
            case LOG_LEVEL_WARN:
                return warnLevel;
            case LOG_LEVEL_ERROR:
                return errorLevel;
        }
        return true;
    }

    /** Are {@code trace} messages currently enabled? */
    public boolean isTraceEnabled() {
        return isLevelEnabled(LOG_LEVEL_TRACE);
    }

    /**
     * A simple implementation which logs messages of level TRACE according to
     * the format outlined above.
     */
    public void trace(String msg) {
        log(LOG_LEVEL_TRACE, msg, null);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * TRACE according to the format outlined above.
     */
    public void trace(String format, Object param1) {
        formatAndLog(LOG_LEVEL_TRACE, format, param1, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * TRACE according to the format outlined above.
     */
    public void trace(String format, Object param1, Object param2) {
        formatAndLog(LOG_LEVEL_TRACE, format, param1, param2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * TRACE according to the format outlined above.
     */
    public void trace(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_TRACE, format, argArray);
    }

    /** Log a message of level TRACE, including an exception. */
    public void trace(String msg, Throwable t) {
        log(LOG_LEVEL_TRACE, msg, t);
    }

    /** Are {@code debug} messages currently enabled? */
    public boolean isDebugEnabled() {
        return isLevelEnabled(LOG_LEVEL_DEBUG);
    }

    /**
     * A simple implementation which logs messages of level DEBUG according to
     * the format outlined above.
     */
    public void debug(String msg) {
        log(LOG_LEVEL_DEBUG, msg, null);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * DEBUG according to the format outlined above.
     */
    public void debug(String format, Object param1) {
        formatAndLog(LOG_LEVEL_DEBUG, format, param1, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * DEBUG according to the format outlined above.
     */
    public void debug(String format, Object param1, Object param2) {
        formatAndLog(LOG_LEVEL_DEBUG, format, param1, param2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * DEBUG according to the format outlined above.
     */
    public void debug(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_DEBUG, format, argArray);
    }

    /** Log a message of level DEBUG, including an exception. */
    public void debug(String msg, Throwable t) {
        log(LOG_LEVEL_DEBUG, msg, t);
    }

    /** Are {@code info} messages currently enabled? */
    public boolean isInfoEnabled() {
        return isLevelEnabled(LOG_LEVEL_INFO);
    }

    /**
     * A simple implementation which logs messages of level INFO according to
     * the format outlined above.
     */
    public void info(String msg) {
        log(LOG_LEVEL_INFO, msg, null);
    }

    public void console(String msg)
    {
        log(LOG_LEVEL_CONSOLE, msg, null);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * INFO according to the format outlined above.
     */
    public void info(String format, Object arg) {
        formatAndLog(LOG_LEVEL_INFO, format, arg, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * INFO according to the format outlined above.
     */
    public void info(String format, Object arg1, Object arg2) {
        formatAndLog(LOG_LEVEL_INFO, format, arg1, arg2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * INFO according to the format outlined above.
     */
    public void info(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_INFO, format, argArray);
    }

    /** Log a message of level INFO, including an exception. */
    public void info(String msg, Throwable t) {
        log(LOG_LEVEL_INFO, msg, t);
    }

    /** Are {@code warn} messages currently enabled? */
    public boolean isWarnEnabled() {
        return isLevelEnabled(LOG_LEVEL_WARN);
    }

    /**
     * A simple implementation which always logs messages of level WARN
     * according to the format outlined above.
     */
    public void warn(String msg) {
        log(LOG_LEVEL_WARN, msg, null);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * WARN according to the format outlined above.
     */
    public void warn(String format, Object arg) {
        formatAndLog(LOG_LEVEL_WARN, format, arg, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * WARN according to the format outlined above.
     */
    public void warn(String format, Object arg1, Object arg2) {
        formatAndLog(LOG_LEVEL_WARN, format, arg1, arg2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * WARN according to the format outlined above.
     */
    public void warn(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_WARN, format, argArray);
    }

    /** Log a message of level WARN, including an exception. */
    public void warn(String msg, Throwable t) {
        log(LOG_LEVEL_WARN, msg, t);
    }

    /** Are {@code error} messages currently enabled? */
    public boolean isErrorEnabled() {
        return isLevelEnabled(LOG_LEVEL_ERROR);
    }

    /**
     * A simple implementation which always logs messages of level ERROR
     * according to the format outlined above.
     */
    public void error(String msg) {
        log(LOG_LEVEL_ERROR, msg, null);
    }

    /**
     * Perform single parameter substitution before logging the message of level
     * ERROR according to the format outlined above.
     */
    public void error(String format, Object arg) {
        formatAndLog(LOG_LEVEL_ERROR, format, arg, null);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * ERROR according to the format outlined above.
     */
    public void error(String format, Object arg1, Object arg2) {
        formatAndLog(LOG_LEVEL_ERROR, format, arg1, arg2);
    }

    /**
     * Perform double parameter substitution before logging the message of level
     * ERROR according to the format outlined above.
     */
    public void error(String format, Object... argArray) {
        formatAndLog(LOG_LEVEL_ERROR, format, argArray);
    }

    /** Log a message of level ERROR, including an exception. */
    public void error(String msg, Throwable t) {
        log(LOG_LEVEL_ERROR, msg, t);
    }

    public void log(LoggingEvent event) {
        int levelInt = event.getLevel().toInt();

        if (!isLevelEnabled(levelInt)) {
            return;
        }
        FormattingTuple tp = MessageFormatter.arrayFormat(event.getMessage(), event.getArgumentArray(), event.getThrowable());
        log(levelInt, tp.getMessage(), event.getThrowable());
    }

    public boolean isTraceEnabled(Marker marker) {
        return isTraceEnabled();
    }

    public void trace(Marker marker, String msg) {
        trace(msg);
    }

    public void trace(Marker marker, String format, Object arg) {
        trace(format, arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        trace(format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object... arguments) {
        trace(format, arguments);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        trace(msg, t);
    }

    public boolean isDebugEnabled(Marker marker) {
        return isDebugEnabled();
    }

    public void debug(Marker marker, String msg) {
        debug(msg);
    }

    public void debug(Marker marker, String format, Object arg) {
        debug(format, arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        debug(format, arg1, arg2);
    }

    public void debug(Marker marker, String format, Object... arguments) {
        debug(format, arguments);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        debug(msg, t);
    }

    public boolean isInfoEnabled(Marker marker) {
        return isInfoEnabled();
    }

    public void info(Marker marker, String msg) {
        info(msg);
    }

    public void info(Marker marker, String format, Object arg) {
        info(format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        info(format, arg1, arg2);
    }

    public void info(Marker marker, String format, Object... arguments) {
        info(format, arguments);
    }

    public void info(Marker marker, String msg, Throwable t) {
        info(msg, t);
    }

    public boolean isWarnEnabled(Marker marker) {
        return isWarnEnabled();
    }

    public void warn(Marker marker, String msg) {
        warn(msg);
    }

    public void warn(Marker marker, String format, Object arg) {
        warn(format, arg);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        warn(format, arg1, arg2);
    }

    public void warn(Marker marker, String format, Object... arguments) {
        warn(format, arguments);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        warn(msg, t);
    }

    public boolean isErrorEnabled(Marker marker) {
        return isErrorEnabled();
    }

    public void error(Marker marker, String msg) {
        error(msg);
    }

    public void error(Marker marker, String format, Object arg) {
        error(format, arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        error(format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object... arguments) {
        error(format, arguments);
    }

    public void error(Marker marker, String msg, Throwable t) {
        error(msg, t);
    }

    public String toString() {
        return this.getClass().getName() + "(" + getName() + ")";
    }
}