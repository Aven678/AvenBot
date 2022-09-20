package fr.aven.bot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import dev.minn.jda.ktx.interactions.components.button
import dev.minn.jda.ktx.messages.Embed
import fr.aven.bot.util.lang.LangKey
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import java.time.Instant
import java.util.concurrent.LinkedBlockingQueue

class TrackScheduler(
    private val player: AudioPlayer,
    private val guild: Guild,
    private var channel: MessageChannel,
    private val language: LangManager,
) : AudioEventAdapter() {
    private val oldQueue = LinkedBlockingQueue<AudioTrack>()
    val queue = LinkedBlockingQueue<AudioTrack>()
    private var statusMessage = ""
    private var requester: Member? = null

    private val channelTrack = mutableMapOf<AudioTrack, MessageChannel>()
    private val requesters = mutableMapOf<AudioTrack, Member>()

    private var repeatTrack = false
    private var repeatPlaylist = false
    private var backRequested = false

    fun queue(track: AudioTrack, channel: MessageChannel, member: Member) {
        channelTrack[track] = channel
        requesters[track] = member
        oldQueue.put(track)

        if (player.playingTrack == null) {
            requester = member
            this.channel = channel
            player.startTrack(track, true)
        } else
            queue.put(track)
    }

    override fun onPlayerPause(player: AudioPlayer) {
        statusMessage()
        super.onPlayerPause(player)
    }

    override fun onPlayerResume(player: AudioPlayer) {
        statusMessage()
        super.onPlayerResume(player)
    }

    override fun onTrackEnd(player: AudioPlayer, finished_track: AudioTrack, endReason: AudioTrackEndReason) {
        if (backRequested) {
            val oldTrack = oldQueue.poll()
            if (requesters[oldTrack]!!.id != requester!!.id || requester == null) requester = requesters[oldTrack]!!
            statusMessage = ""

            player.startTrack(oldTrack.makeClone(), false)
            backRequested = false
            return
        }

        if (repeatTrack) {
            val cloneTrack = finished_track.makeClone()
            statusMessage = ""
            player.startTrack(cloneTrack, false)
            return
        }

        if (repeatPlaylist) {
            val cloneTrack = finished_track.makeClone()
            queue(cloneTrack, channel, requester!!)
        }

        if (queue.isEmpty()) {
            guild.audioManager.closeAudioConnection()
            channel.retrieveMessageById(statusMessage).queue {
                it?.delete()?.queue()
                statusMessage = ""
            }
            channel.sendMessage(language.getString(
                LangKey.keyBuilder(this, "trackEnd", "stop.confirm"),
                "Disconnected from your channel.")
            )
            return
        }

        statusMessage = ""
        val track = queue.poll()
        if (channelTrack[track]!!.id != channel.id) channel = channelTrack[track]!!
        if (requesters[track]!!.id != requester!!.id || requester == null) requester = requesters[track]!!
        player.startTrack(track, false)

        super.onTrackEnd(player, track, endReason)
    }

    override fun onTrackStart(player: AudioPlayer, track: AudioTrack) {
        statusMessage()

        super.onTrackStart(player, track)
    }

    private fun statusMessage() {
        if (player.playingTrack == null) return
        val track = player.playingTrack

        val embed = Embed {
            author {
                name = language.getString(
                    LangKey.keyBuilder(this,
                        "statusMessage",
                        if (player.isPaused) "player.paused" else "music.progress"),
                    "Music in progress")
                url = track.info.uri
                iconUrl = guild.jda.selfUser.avatarUrl
            }

            field {
                name = track.info.title
                value = "❱ ${
                    language.getString(LangKey.keyBuilder(this, "statusMessage", "music.author"),
                        "Author")
                } : ${track.info.author} " +
                        "\n❱ ${
                            language.getString(LangKey.keyBuilder(this, "statusMessage", "music.duration"),
                                "Duration")
                        } : ${getTimestamp(track.duration)}" +
                        (if (repeatTrack) "\n❱ ${
                            language.getString(LangKey.keyBuilder(this,
                                "statusMessage",
                                "music.repeatRequested"), "The music will be repeated.")
                        }" else "") +
                        (if (repeatPlaylist) "\n❱ ${
                            language.getString(LangKey.keyBuilder(this,
                                "statusMessage",
                                "music.repeatPlaylistRequested"), "Playlist repeat enabled.")
                        }" else "")
            }

            timestamp = Instant.now()
            footer {
                name = language.getString(LangKey.keyBuilder(this, "statusMessage", "music.request"),
                    "Request from ") + requester!!.user.name
                iconUrl = requester!!.user.avatarUrl
            }

            thumbnail = "https://i.ytimg.com/vi/${track.identifier}/maxresdefault.jpg"

        }

        val actionRows1 = listOf(
            button(id = "m.old", emoji = Emoji.fromUnicode("⏮️"), style = ButtonStyle.SECONDARY),
            button(id = "m.player",
                emoji = if (player.isPaused) Emoji.fromUnicode("▶️") else Emoji.fromUnicode("⏸️"),
                style = ButtonStyle.SECONDARY),
            button(id = "m.skip", emoji = Emoji.fromUnicode("⏭️"), style = ButtonStyle.SECONDARY)
        )

        val actionRows2 = listOf(button(id = "m.repeatPlaylist", emoji = Emoji.fromUnicode("\uD83D\uDD01"), style = ButtonStyle.SECONDARY),
            button(id = "m.repeatTrack", emoji = Emoji.fromUnicode("\uD83D\uDD02"), style = ButtonStyle.SECONDARY),
            button(id = "m.lyrics", emoji = Emoji.fromUnicode("\uD83D\uDCDC"), style = ButtonStyle.SECONDARY, disabled = true),
            button(id = "m.stop", label = "Stop", style = ButtonStyle.DANGER))

        if (statusMessage == "") channel.sendMessageEmbeds(embed).addActionRow(actionRows1).addActionRow(actionRows2)
            .queue { statusMessage = it.id }
        else channel.editMessageEmbedsById(statusMessage, embed).setComponents(ActionRow.of(actionRows1), ActionRow.of(actionRows2)).queue()
    }

    fun getTimestamp(milis: Long): String {
        var seconds = milis / 1000
        val hours = Math.floorDiv(seconds, 3600)
        seconds -= hours * 3600
        val mins = Math.floorDiv(seconds, 60)
        seconds -= mins * 60
        return (if (hours == 0L) "" else "$hours:") + String.format("%02d", mins) + ":" + String.format("%02d", seconds)
    }

    fun playBackTrack() {
        backRequested = true
        player.stopTrack()
    }

    fun changePlayerStatus() {
        player.isPaused = !player.isPaused
    }

    fun skipTrack() {
        player.stopTrack()
    }

    fun repeatPlaylist() {
        repeatPlaylist = !repeatPlaylist
        statusMessage()
    }

    fun repeatTrack() {
        repeatTrack = !repeatTrack
        statusMessage()
    }

    fun stopPlayer() {
        queue.clear()
        repeatTrack = false
        repeatPlaylist = false
        player.stopTrack()
    }

    fun shuffleQueue()
    {
        val tempQueue: MutableList<AudioTrack> = LinkedHashSet<AudioTrack>(queue).toMutableList()
        val currentTrack = tempQueue.removeFirst()
        tempQueue.shuffle()
        tempQueue.add(0, currentTrack)
        queue.clear()
        queue.addAll(tempQueue)
    }
}