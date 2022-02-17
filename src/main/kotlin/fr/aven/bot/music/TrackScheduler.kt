package fr.aven.bot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import net.dv8tion.jda.api.entities.Guild
import java.util.concurrent.LinkedBlockingQueue

class TrackScheduler(val player: AudioPlayer, val guild: Guild): AudioEventAdapter()
{
    private val queue = LinkedBlockingQueue<AudioTrack>()

    fun queue(track: AudioTrack)
    {
        if (player.playingTrack == null)
            player.startTrack(track, false)
        else
            queue.put(track)
    }

    override fun onPlayerPause(player: AudioPlayer?) {
        super.onPlayerPause(player)
    }

    override fun onPlayerResume(player: AudioPlayer?) {
        super.onPlayerResume(player)
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        super.onTrackEnd(player, track, endReason)
    }

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        super.onTrackStart(player, track)
    }

    override fun onTrackException(player: AudioPlayer?, track: AudioTrack?, exception: FriendlyException?) {
        super.onTrackException(player, track, exception)
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        super.onTrackStuck(player, track, thresholdMs)
    }
}