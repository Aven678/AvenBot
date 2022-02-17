package fr.aven.bot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.GuildChannel

class GuildMusicManager(private val manager: AudioPlayerManager, val guild: Guild)
{
    val player: AudioPlayer = manager.createPlayer()
    val scheduler = TrackScheduler(player, guild)

    init {
        player.addListener(scheduler)
    }

    fun sendHandler(): AudioPlayerSendHandler {
        return AudioPlayerSendHandler(player)
    }
}