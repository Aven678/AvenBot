package fr.aven.bot.modules.notifications.yt

import com.apptastic.rssreader.Item
import com.apptastic.rssreader.RssReader
import fr.aven.bot.Main
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class YouTubeNotification(val url: String, val user: String, val pwd: String) {

    private val ytTimer: Timer = Timer("YouTubeNotification")
    private val logger: Logger = LoggerFactory.getLogger("YouTubeNotification")
    private val db: YTDatabase = YTDatabase(url, user, pwd)
    private val ytTask: YTTask = YTTask(db)

    fun init(): YouTubeNotification {
        ytTimer.scheduleAtFixedRate(ytTask, 0, 120000)
        logger.info("YouTube Notification is initialized!")
        return this
    }

    fun stop() {
        ytTask.cancel()
        ytTimer.cancel()
    }

    class YTTask(val db: YTDatabase) : TimerTask()
    {
        val reader: RssReader = RssReader()
        var date = Main.lastRestart

        override fun run() {
            for (channel in this.db.getChannels())
            {
                val items: Stream<Item> = reader.read(channel.getRSSURL())
                val videos: List<Item> = items.filter { it.pubDateZonedDateTime.get().toInstant().toEpochMilli() > date.toInstant().toEpochMilli() } .collect(Collectors.toList())

                if (videos.isEmpty()) continue

                for (video in videos)
                {
                    if (channel.getJDAChannel() == null) continue
                    if (channel.messageToDiscord == "") continue

                    val finalMessage = channel.messageToDiscord.replace("<channelName>", video.author.get(), ignoreCase = true)
                        .replace("<url>", video.link.get(), ignoreCase = true)
                        .replace("<title>", video.title.get(), ignoreCase = true)
                        .replace("<channelURL>", channel.getYouTubeURL(), ignoreCase = true)

                    channel.getJDAChannel()!!.sendMessage(finalMessage).queue()
                }

            }

            date = Date()
        }

    }
}