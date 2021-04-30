package fr.aven.bot.modules.notifications.yt

import fr.aven.bot.modules.jda.JDAManager
import net.dv8tion.jda.api.entities.TextChannel

/**
 * Entity class
 */
class YTNotif(val channelID: String, val discordChannel: String, val messageToDiscord: String)
{
    private val YOUTUBE_CHANNEL_BASE: String = "https://www.youtube.com/channel/"
    private val YOUTUBE_RSS_BASE: String = "https://www.youtube.com/feeds/videos.xml?channel_id="

    fun getYouTubeURL(): String
    {
        return YOUTUBE_CHANNEL_BASE + channelID
    }

    fun getRSSURL(): String
    {
        return YOUTUBE_RSS_BASE + channelID
    }

    fun getJDAChannel(): TextChannel? {
        return JDAManager.getShardManager().getTextChannelById(discordChannel)
    }
}