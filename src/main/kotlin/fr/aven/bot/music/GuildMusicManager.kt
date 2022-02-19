package fr.aven.bot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import fr.aven.bot.util.Language
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.GuildChannel
import net.dv8tion.jda.api.entities.TextChannel

class GuildMusicManager(private val manager: AudioPlayerManager, private val guild: Guild, private val channel: TextChannel, val language: Language)
{
    val player: AudioPlayer = manager.createPlayer()
    val scheduler = TrackScheduler(player, guild, channel, language)

    init {
        player.addListener(scheduler)
    }

    fun sendHandler(): AudioPlayerSendHandler {
        return AudioPlayerSendHandler(player)
    }
}