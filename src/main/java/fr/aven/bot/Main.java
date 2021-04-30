package fr.aven.bot;

import fr.aven.bot.commands.fun.BingoMap;
import fr.aven.bot.modules.database.SQL;
import fr.aven.bot.modules.jda.events.Listener;
import fr.aven.bot.modules.jda.events.MemberActivityEvent;
import fr.aven.bot.modules.jda.JDAManager;
import fr.aven.bot.modules.jda.events.MusicReactionListener;
import fr.aven.bot.modules.music.spotify.SpotifyAPI;
/*import fr.aven.bot.modules.notifications.twitch.Twitch;
import fr.aven.bot.modules.notifications.yt.YouTubeNotification;*/
import fr.aven.bot.modules.tickets.TicketsChannelDB;
import fr.aven.bot.util.Configuration;
import fr.aven.bot.util.DBList;
import fr.aven.bot.util.KSoft;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

import java.util.Date;

public class Main
{
    private static Configuration configuration;
    public static Logger LOGGER = LoggerFactory.getLogger("AvenBot");
    public static Date lastRestart;
    private static CommandManager commandManager;
    private static Listener listener;

    private static TicketsChannelDB ticketsChannelDB;
    private static SQL database;

    private static KSoft kSoft;

    private static DBList dbl = new DBList();
    private static SpotifyAPI spotifyAPI;
    //private static Twitch twitchAPI;

    private static BingoMap bingoMap;

    //private static YouTubeNotification ytNotifs;

    public static void main(String... args) throws Exception
    {
        System.setProperty("console.encoding","UTF-8");

        kSoft = new KSoft();
        lastRestart = new Date();

        System.setProperty("AvenBot", "");

        configuration = new Configuration("config.json");
        database = new SQL();

        ticketsChannelDB = new TicketsChannelDB();
        commandManager = new CommandManager();


        listener = new Listener(commandManager);
        JDAManager.getShardManager().addEventListener(listener, new MusicReactionListener(), new MemberActivityEvent());
        setActivity(Activity.ActivityType.WATCHING, configuration.getString("game",Constants.PREFIX+"help | justaven.xyz"));

        //Init Spotify API
        spotifyAPI = new SpotifyAPI(configuration.getString("spotify.clientID", ""), configuration.getString("spotify.clientSecret", ""));
/*
        //Init Twitch API
        twitchAPI = new Twitch();
        twitchAPI.registerFeatures();

        //Init YT Notifications
        ytNotifs = new YouTubeNotification(configuration.getString("sql.host"), configuration.getString("sql.user"), configuration.getString("sql.password"));
        ytNotifs.init();*/

        //Init BingoMap
        bingoMap = new BingoMap();

        configuration.save();
    }

    public static Configuration getConfiguration() { return configuration; }

    public static SQL getDatabase() { return database; }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static KSoft getkSoft() {
        return kSoft;
    }

    public static DBList getDbl() {
        return dbl;
    }

    public static SpotifyAPI getSpotifyAPI() {
        return spotifyAPI;
    }

    public static TicketsChannelDB getTicketsDB() { return ticketsChannelDB; }

    public static BingoMap getBingoMap() {
        return bingoMap;
    }

    /*public static Twitch getTwitchAPI() {
        return twitchAPI;
    }*/

    public static void setActivity(Activity.ActivityType type, String text) {

        switch (type) {
            case LISTENING:
                JDAManager.getShardManager().setActivity(Activity.listening(text));
                break;
            case WATCHING:
                JDAManager.getShardManager().setActivity(Activity.watching(text));
                break;
            case STREAMING:
                JDAManager.getShardManager().setActivity(Activity.streaming(text, "https://www.twitch.tv/aven678")); //l'adresse twitch pourra être changée
                break;
            case DEFAULT:
                JDAManager.getShardManager().setActivity(Activity.playing(text));
                break;
        }
    }

    public static void stop()
    {
        try {
            //ytNotifs.stop();
            configuration.save();
            StaticLoggerBinder.getSingleton().getLoggerFactory().save();

            setActivity(Activity.ActivityType.WATCHING, "shutting down!");
            JDAManager.getShardManager().setStatus(OnlineStatus.DO_NOT_DISTURB);
            Thread.sleep(5000);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        JDAManager.getShardManager().shutdown();

        System.exit(0);
    }
}
