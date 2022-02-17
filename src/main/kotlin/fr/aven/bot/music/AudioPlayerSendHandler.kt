package fr.aven.bot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler
import java.nio.ByteBuffer

class AudioPlayerSendHandler(private val audioPlayer: AudioPlayer): AudioSendHandler {

    lateinit var lastFrame: AudioFrame

    override fun canProvide(): Boolean {
        lastFrame = audioPlayer.provide()
        return lastFrame != null
    }

    override fun provide20MsAudio(): ByteBuffer {
        return ByteBuffer.wrap(lastFrame.data)
    }

    @Override
    override fun isOpus(): Boolean {
        return true
    }
}