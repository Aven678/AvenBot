package fr.aven.bot;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constants
{
    public static final String VERSION = "2.3.1 Stable";
    public static final String PREFIX = Main.getConfiguration().getString("prefix", "=");
    public static long OWNER = 261846314554228739L;
    public static long COOWNER = 537696808504262696L;
    public static long OWNER_TERTIAIRE = 315828597296332800L;
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM uuuu, HH:mm").withLocale(new Locale("US"));
}
