package fr.aven.bot;

import fr.aven.bot.database.SQL;
import fr.aven.bot.events.Listener;
import fr.aven.bot.events.MemberActivityEvent;
import fr.aven.bot.jda.JDAManager;
import fr.aven.bot.events.MusicReactionListener;
import fr.aven.bot.music.SpotifyAPI;
import fr.aven.bot.util.Configuration;
import fr.aven.bot.util.DBList;
import fr.aven.bot.util.KSoft;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Main
{
    public static Long owner = 261846314554228739L;
    private static Configuration configuration;
    public static Logger LOGGER = LoggerFactory.getLogger("AvenBot");
    public static Date lastRestart;
    private static CommandManager commandManager;
    private static Listener listener;
    private static SQL database;
    private static KSoft kSoft;

    private static DBList dbl = new DBList();
    private static SpotifyAPI spotifyAPI = new SpotifyAPI();

    public static void main(String... args) throws Exception
    {
        kSoft = new KSoft();
        lastRestart = new Date();
        System.setProperty("AvenBot", "");
        configuration = new Configuration("config.json");
        database = new SQL();
        commandManager = new CommandManager();
        listener = new Listener(commandManager);
        JDAManager.getShardManager().addEventListener(listener, new MusicReactionListener(), new MemberActivityEvent());
        setActivity(Activity.ActivityType.WATCHING, configuration.getString("game",Constants.PREFIX+"help | justaven.xyz"));
        spotifyAPI.createSpotifyAPI(configuration.getString("spotify.clientID", ""), configuration.getString("spotify.clientSecret", ""));

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
        configuration.save();
        JDAManager.getShardManager().shutdown();

        System.exit(0);
    }
}
