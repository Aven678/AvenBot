package fr.aven.bot.commands.list.slash.music

import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.lang.LangManager
import fr.aven.bot.util.music.FormatUtils
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.math.min

class Seek: ISlashCmd
{
    override val description: String
        get() = "Seeks music"

    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        val manager = CommandManager.playerManager.guildMusicManager(event.interaction)

        if (manager.player.playingTrack == null) {
            event.interaction.reply("The player doesn't listen music.").queue()
            return
        }

        val num = event.interaction.getOption("time")?.asString
        var time: Long
        try {
            time = TimeUnit.SECONDS.toMillis(num!!.toLong())
        } catch (nfe: java.lang.NumberFormatException) {
            time = parseTime(num!!)
        }

        val track = manager.player.playingTrack
        val pos = min(track.position + time, track.duration - 1)
        track.position = pos

        event.interaction.reply(String.format("The position has been updated to %s!", FormatUtils.formatDuration(pos))).queue()
    }

    private val LETTER_PATTERN = Pattern.compile("[a-z]")
    private val NUMBER_PATTERN = Pattern.compile("[0-9]")

    private fun parseTime(request: String): Long {
        val requestLower = request.replace(" ", "").lowercase()

        val pattern = Pattern.compile("[0-9]+[a-z]")
        val matcher = pattern.matcher(requestLower)

        val matches = mutableListOf<String>()
        while (matcher.find()) matches.add(matcher.group())

        var seconds: Long = 0

        for (matche in matches) {
            val time = LETTER_PATTERN.matcher(matche).replaceAll("").toLong()
            val unit = NUMBER_PATTERN.matcher(matche).replaceAll("")
            when (unit) {
                "s" -> seconds += TimeUnit.SECONDS.toMillis(time)
                "m" -> seconds += TimeUnit.MINUTES.toMillis(time)
                "d" -> seconds += TimeUnit.DAYS.toMillis(time)
            }
        }

        return seconds
    }

    override val data: CommandData
        get() = Commands.slash(name, description).addOption(OptionType.STRING, "time", "Time (ex: 2m30s)")
    override val name: String
        get() = "seek"
}