package fr.aven.bot.music

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
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import java.awt.Color
import java.time.Instant

class PlayerManager(private val config: Config, val language: Language)
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
        else YoutubeIpRotatorSetup(NanoIpRoutePlanner(listOf( Ipv6Block(ipv6)), true)).forSource(youtubeAudioSourceManager).setup()

        this.playerManager.registerSourceManager(youtubeAudioSourceManager)
        logger.info("Initialized!")

        AudioSourceManagers.registerLocalSource(playerManager)
        AudioSourceManagers.registerRemoteSources(playerManager)
    }

    private fun guildMusicManager(guild: Guild): GuildMusicManager
    {
        musicManagers.putIfAbsent(guild.idLong, GuildMusicManager(playerManager, guild))
        guild.audioManager.sendingHandler = musicManagers[guild.idLong]!!.sendHandler()
        return musicManagers[guild.idLong]!!
    }

    fun loadAndPlay(interaction: SlashCommandInteraction, trackUrl: String)
    {
        val guild = interaction.guild!!
        val musicManager = guildMusicManager(guild)
        playerManager.frameBufferDuration = 5000
        playerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler
        {
            override fun trackLoaded(track: AudioTrack) {
                interaction.hook.editOriginalEmbeds(Embed {
                    author {
                        name = language.getTextFor(guild, "music.add")
                        url = track.info.uri
                        iconUrl = interaction.jda.selfUser.avatarUrl
                    }

                    field {
                        name = "❱ ${language.getTextFor(guild, "music.author")} : ${track.info.author}"
                        value = "❱ ${track.info.title}"
                        inline = false
                    }

                    color = Color(0,255,151).rgb
                    timestamp = Instant.now()
                    footer {
                        name = "AvenBot by Aven#1000"
                    }

                    thumbnail = "https://i.ytimg.com/vi/${track.info.identifier}/maxresdefault.jpg"
                }).queue()

                play(musicManager, track, interaction)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                if (playlist!!.isSearchResult) TODO()
                else {
                    var firstTrack: AudioTrack? = playlist.selectedTrack;
                    if (firstTrack == null)
                        firstTrack = playlist.tracks.removeFirst()

                    interaction.hook.editOriginalEmbeds(Embed {
                        author {
                            name = language.getTextFor(guild, "playlist.title")
                        }

                        field {
                            name = "❱ ${playlist.name} (${playlist.tracks.size} tracks)"
                            value = "❱ ${language.getTextFor(guild, "playlist.firstTrack")} : ${firstTrack!!.info.title}"
                            inline = false
                        }

                        footer {
                            name = "AvenBot by Aven#1000"
                        }

                        color = Color(255, 127, 0).rgb
                    }).queue()

                    play(musicManager, firstTrack!!, interaction)
                    playlist.tracks.forEach { musicManager.scheduler.queue(it) }
                }
            }

            override fun noMatches() {
                interaction.hook.editOriginal(language.getTextFor(guild, "music.notFound")).queue()
            }

            override fun loadFailed(exception: FriendlyException?) {
                interaction.hook.editOriginal(language.getTextFor(guild, "music.couldntPlay")).queue()
            }

        })
    }

    fun play(musicManager: GuildMusicManager, track: AudioTrack, interaction: SlashCommandInteraction)
    {
        musicManager.scheduler.queue(track)
    }

}