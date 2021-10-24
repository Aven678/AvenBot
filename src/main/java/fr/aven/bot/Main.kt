package fr.aven.bot

import fr.aven.bot.commands.`fun`.BingoMap
import fr.aven.bot.modules.core.Language
import fr.aven.bot.modules.database.SQL
import fr.aven.bot.modules.jda.JDAManager
import fr.aven.bot.modules.jda.events.Listener
import fr.aven.bot.modules.jda.events.MemberActivityEvent
import fr.aven.bot.modules.jda.events.MusicReactionListener
import fr.aven.bot.modules.music.spotify.SpotifyAPI
import fr.aven.bot.modules.notifications.twitch.Twitch
import fr.aven.bot.modules.notifications.yt.YouTubeNotification
import fr.aven.bot.modules.tickets.TicketsChannelDB
import fr.aven.bot.util.Configuration
import fr.aven.bot.util.DBList
import fr.aven.bot.util.KSoft
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.impl.StaticLoggerBinder
import java.util.*
import kotlin.system.exitProcess

object Main
{

    @JvmField
    var logger: Logger = LoggerFactory.getLogger("AvenBot")

    @JvmField
    var lastRestart: Date
    var config: Configuration
    var mainListener: Listener

    //Commands
    var cmdManager: CommandManager
    var bingo: BingoMap = BingoMap()

    //Database Class
    var db: SQL
    var ticketsDb: TicketsChannelDB

    //Modules & APIs
    var lang: Language = Language()
    private var dblist: DBList = DBList()
    var kSoftAPI: KSoft = KSoft()
    var spotify: SpotifyAPI
    lateinit var twitch: Twitch
    lateinit var youtubeNotifs: YouTubeNotification

    init {
        logger.info("AvenBot is starting !")

        System.setProperty("console.encoding", "UTF-8")
        System.setProperty("AvenBot", "")

        lastRestart = Date()

        logger.info("Configuration initialized.")
        config = Configuration("config.json")

        logger.info("Init Database.")
        db = SQL()
        ticketsDb = TicketsChannelDB()

        logger.info("Init Spotify.")
        spotify = SpotifyAPI(
            config.getString("spotify.clientID", ""),
            config.getString("spotify.clientSecret", "")
        )

        cmdManager = CommandManager()
        logger.info("CommandManager initialized.")

        logger.info("Init JDA.")
        mainListener = Listener(cmdManager)
        JDAManager.manager.addEventListener(mainListener, MusicReactionListener(), MemberActivityEvent())
        setActivity(Activity.ActivityType.WATCHING, config.getString("game", Constants.PREFIX + "help | justaven.xyz"))

        config.save()
    }

    @JvmStatic
    fun getTicketsDB(): TicketsChannelDB {
        return ticketsDb
    }

    @JvmStatic
    fun getConfiguration(): Configuration {
        return config
    }

    @JvmStatic
    fun getDatabase(): SQL {
        return db
    }

    @JvmStatic
    fun getBingoMap(): BingoMap {
        return bingo
    }

    @JvmStatic
    fun getkSoft(): KSoft {
        return kSoftAPI
    }

    @JvmStatic
    fun getCommandManager(): CommandManager {
        return cmdManager
    }

    @JvmStatic
    fun getSpotifyAPI(): SpotifyAPI {
        return spotify
    }

    @JvmStatic
    fun getDbl(): DBList {
        return dblist
    }

    @JvmStatic
    fun getLanguage(): Language {
        return lang
    }

    @JvmStatic
    fun stop() {
        try {
            config.save()
            StaticLoggerBinder.getSingleton().loggerFactory.save()

            setActivity(Activity.ActivityType.WATCHING, "shutting down!")
            JDAManager.getShardManager().setStatus(OnlineStatus.DO_NOT_DISTURB)
            Thread.sleep(5000)
        } catch (e: Exception) {
            logger.error(e.message)
        }

        JDAManager.getShardManager().shutdown()
        exitProcess(0)
    }

    private fun setActivity(activityType: Activity.ActivityType, text: String) {

        val activity: Activity = when (activityType) {
            Activity.ActivityType.LISTENING -> Activity.listening(text)
            Activity.ActivityType.COMPETING -> Activity.competing(text)
            Activity.ActivityType.STREAMING -> Activity.streaming(text, "https://twitch.tv/Aven678")
            Activity.ActivityType.WATCHING -> Activity.watching(text)
            Activity.ActivityType.DEFAULT -> Activity.playing(text)

            Activity.ActivityType.CUSTOM_STATUS -> Activity.of(Activity.ActivityType.CUSTOM_STATUS, text)
        }

        JDAManager.getShardManager().setActivity(activity)
    }
}