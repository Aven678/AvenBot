package fr.aven.bot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.minn.jda.ktx.Embed
import dev.minn.jda.ktx.interactions.SelectMenu
import dev.minn.jda.ktx.interactions.button
import dev.minn.jda.ktx.interactions.option
import dev.minn.jda.ktx.interactions.secondary
import fr.aven.bot.util.Language
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import net.dv8tion.jda.api.interactions.components.selections.SelectOption
import java.awt.Color
import java.time.Instant
import java.util.concurrent.LinkedBlockingQueue

class GuildMusicManager(private val manager: AudioPlayerManager, private val guild: Guild, private val channel: TextChannel, val language: Language)
{
    val player: AudioPlayer = manager.createPlayer()
    val scheduler = TrackScheduler(player, guild, channel, language)

    //Key : UserID
    private val search = mutableMapOf<String, MutableList<AudioTrack>>()
    private var searchMessage = ""

    init {
        player.addListener(scheduler)
    }

    fun sendHandler(): AudioPlayerSendHandler {
        return AudioPlayerSendHandler(player)
    }

    fun searchResult(playlist: AudioPlaylist, interaction: SlashCommandInteraction) {
        if (searchMessage != "")
        {
            interaction.hook.editOriginalEmbeds(Embed { description = language.getTextFor(interaction.guild!!, "play.confirmChoice") }).queue()
            return
        }

        val tracks = mutableListOf<AudioTrack>()
        val options = mutableListOf<SelectOption>()
        interaction.hook.editOriginalEmbeds(Embed {
            field {
                name = language.getTextFor(interaction.guild!!, "music.searchTitle")
                value = language.getTextFor(interaction.guild!!, "music.search")
            }

            color = Color(255,0,0).rgb
        }).setActionRows(
            ActionRow.of(
                SelectMenu("music:search") {
                    for (i in 0..4)
                    {
                        tracks.add(playlist.tracks[i])
                        option(playlist.tracks[i].info.title, "${i+1}", default = false)
                    }

                    option("Cancel", "cancel", default = false)
                }
            )
        ).queue {
            if (search.containsKey(interaction.user.id)) search[interaction.user.id]!!.addAll(tracks)
            else search[interaction.user.id] = tracks
        }
    }

    fun searchConfirm(event: SelectMenuInteractionEvent): Boolean
    {
        if (!search.contains(event.user.id)) return false

        val choice = event.selectedOptions.first().value

        if (choice == "cancel")
        {
            event.interaction.hook.editOriginal(language.getTextFor(guild, "music.canceled")).setEmbeds().setActionRows().queue()
            search[event.user.id]!!.clear()
            return true
        }

        val tracks = search[event.user.id]
        val track = tracks!![choice.toInt()-1]

        search[event.user.id]!!.clear()

        scheduler.queue(track, event.textChannel, event.member!!)

        event.interaction.hook.editOriginalEmbeds(embedConfirm(track)).setActionRows().queue()
        return true
    }

    fun embedConfirm(track: AudioTrack): MessageEmbed
    {
        return Embed {
            author {
                name = language.getTextFor(guild, "music.add")
                url = track.info.uri
                iconUrl = guild.jda.selfUser.avatarUrl
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
        }
    }

    fun sendQueueMessage(event: ButtonInteractionEvent)
    {
        val page = event.button.id!!.split(".")[2].toInt()
        event.interaction
            .hook
            .editOriginalEmbeds(queueEmbed(page))
            .setActionRows(ActionRow.of(
                if (page >= 0) secondary("m.queue.${page-1}", label = "Previous page", emoji = Emoji.fromUnicode("⬅️")) else null,
                secondary("m.queue.${page+1}", label = "Next page", emoji = Emoji.fromUnicode("➡️"))
            )).queue()
    }

    fun sendQueueMessage(event: SlashCommandInteractionEvent)
    {
        event.replyEmbeds(queueEmbed(0))
            .addActionRows(ActionRow.of(
                secondary("m.queue.1", label = "Next page", emoji = Emoji.fromUnicode("➡️"))
            )).queue()
    }

    private fun queueEmbed(page: Int): MessageEmbed
    {
        val builder = StringBuilder()
        var queue = LinkedBlockingQueue(scheduler.queue).toMutableList()

        builder.append("**${language.getTextFor(guild, "music.progress")}**")
        val playingTrack = player.playingTrack
        builder.append("\n[${playingTrack.info.title}](${playingTrack.info.uri}) ${scheduler.getTimestamp(playingTrack.duration)} \n")

        if (queue.size > 10) queue = queue.subList(10 *page, if (10*page+10 > queue.size) queue.size else 10*page+10)

        var count = 0
        queue.forEach {
            count += 1
            builder.append("\n`${count + (10*page)}`[${it.info.title}](${it.info.uri}) ${scheduler.getTimestamp(it.duration)}")
        }

        return Embed {
            title = language.getTextFor(guild, "queue.title").format(scheduler.queue.size)
            description = builder.toString()

            footer {
                name = "Page ${page+1}/${if (scheduler.queue.size >= 10) (scheduler.queue.size / 10) else 1}"
            }

            timestamp = Instant.now()

            color = Color(255, 0, 0).rgb
        }
    }
}