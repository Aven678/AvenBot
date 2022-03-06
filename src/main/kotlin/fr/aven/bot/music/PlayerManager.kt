package fr.aven.bot.music

import api.deezer.objects.Track
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.lava.extensions.youtuberotator.YoutubeIpRotatorSetup
import com.sedmelluq.lava.extensions.youtuberotator.planner.NanoIpRoutePlanner
import com.sedmelluq.lava.extensions.youtuberotator.tools.ip.Ipv6Block
import dev.minn.jda.ktx.Embed
import dev.minn.jda.ktx.SLF4J
import fr.aven.bot.core.Config
import fr.aven.bot.util.Language
import fr.aven.bot.util.lang.LangKey
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import java.awt.Color
import java.time.Instant

class PlayerManager(private val config: Config)
{
    private val logger by SLF4J
    private var playerManager: AudioPlayerManager = DefaultAudioPlayerManager()
    private var musicManagers = mutableMapOf<Long, GuildMusicManager>()

    private val youtubeAudioSourceManager = YoutubeAudioSourceManager(true)

    init {
        logger.info("Init PlayerManager...")

        logger.info("Init YouTube IP Rotator...")
        val ipv6 = config.ipv6_block
        if (ipv6 == "none") logger.warn("Skipped IP Rotator")
        else YoutubeIpRotatorSetup(NanoIpRoutePlanner(listOf(Ipv6Block(ipv6)), true)).forSource(youtubeAudioSourceManager).setup()

        this.playerManager.registerSourceManager(youtubeAudioSourceManager)
        logger.info("Initialized!")

        AudioSourceManagers.registerLocalSource(playerManager)
        AudioSourceManagers.registerRemoteSources(playerManager)
    }

    fun guildMusicManager(event: ButtonInteractionEvent): GuildMusicManager = guildMusicManager(event.guild!!, event.textChannel)
    fun guildMusicManager(event: SelectMenuInteractionEvent): GuildMusicManager = guildMusicManager(event.guild!!, event.textChannel)
    fun guildMusicManager(interaction: SlashCommandInteraction): GuildMusicManager = guildMusicManager(interaction.guild!!, interaction.textChannel)
    private fun guildMusicManager(guild: Guild, channel: TextChannel): GuildMusicManager
    {
        musicManagers.putIfAbsent(guild.idLong, GuildMusicManager(playerManager, guild, channel))
        guild.audioManager.sendingHandler = musicManagers[guild.idLong]!!.sendHandler()
        return musicManagers[guild.idLong]!!
    }

    fun loadAndPlayDeezerTrack(interaction: SlashCommandInteraction, track: Track) = loadAndPlay(interaction, "ytsearch:${track.title} ${track.artist.name}")

    fun loadAndPlay(interaction: SlashCommandInteraction, trackUrl: String)
    {
        val guild = interaction.guild!!
        val musicManager = guildMusicManager(interaction)
        playerManager.frameBufferDuration = 5000
        playerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler
        {
            override fun trackLoaded(track: AudioTrack) {
                interaction.replyEmbeds(musicManager.embedConfirm(track)).setEphemeral(true).queue()

                play(musicManager, track, interaction)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                if (playlist.isSearchResult) musicManager.searchResult(playlist, interaction)
                else {
                    var firstTrack: AudioTrack? = playlist.selectedTrack;
                    if (firstTrack == null)
                        firstTrack = playlist.tracks.removeFirst()

                    interaction.replyEmbeds(Embed {
                        author {
                            name = musicManager.language.getString(LangKey.keyBuilder(this, "playlistLoaded", "playlist.title"), "Playlist added")
                        }

                        field {
                            name = "❱ ${playlist.name} (${playlist.tracks.size} tracks)"
                            value = "❱ ${musicManager.language.getString(LangKey.keyBuilder(this, "playlistLoaded", "playlist.firstTrack"), "First track")} : ${firstTrack!!.info.title}"
                            inline = false
                        }

                        footer {
                            name = "AvenBot by Aven#1000"
                        }

                        color = Color(255, 127, 0).rgb
                    }).setEphemeral(true).queue()

                    play(musicManager, firstTrack!!, interaction)
                    playlist.tracks.forEach { musicManager.scheduler.queue(it, interaction.textChannel, interaction.member!!) }
                }
            }

            override fun noMatches() {
                interaction.reply(musicManager.language.getString(LangKey.keyBuilder(this, "noMatches", "music.notFound"), "Nothing found, please retry (link recommended).")).queue()
            }

            override fun loadFailed(exception: FriendlyException?) {
                interaction.reply(musicManager.language.getString(LangKey.keyBuilder(this, "noMatches","music.couldntPlay"), "Could not play.")).queue()
            }

        })
    }

    fun play(musicManager: GuildMusicManager, track: AudioTrack, interaction: SlashCommandInteraction) = musicManager.scheduler.queue(track, interaction.textChannel, interaction.member!!)
}