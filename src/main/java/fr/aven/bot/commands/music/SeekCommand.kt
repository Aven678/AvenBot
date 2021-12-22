package fr.aven.bot.commands.music

import fr.aven.bot.Constants
import fr.aven.bot.Main
import fr.aven.bot.modules.core.CommandEvent
import fr.aven.bot.modules.music.PlayerManager
import fr.aven.bot.util.FormatUtils
import fr.aven.bot.modules.core.ICommand
import fr.aven.bot.util.NumberUtils
import fr.aven.bot.util.TimeUtils
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.jetbrains.annotations.Nullable
import java.util.concurrent.TimeUnit

class SeekCommand : ICommand
{
    override fun handle(args: MutableList<String>, event: CommandEvent) {
        val musicManager = PlayerManager.getInstance().getGuildMusicManager(event.guild, event.channel)
        val player = musicManager.player

        if (!event.guild.audioManager.isConnected)
        {
            event.channel.sendMessage(Main.getLanguage().getTextFor("stop.botNotConnected", event.guild)).queue()
            return
        }

        if (player.isPaused)
        {
            player.isPaused = false;
            musicManager.scheduler.editMessage()
        }

        if (player.playingTrack == null)
        {
            return
        }

        if (!event.guild.selfMember.voiceState?.channel?.members?.contains(event.member)!!) {
            event.channel.sendMessage(Main.getLanguage().getTextFor("stop.isNotInSameChannel", event.guild)).queue()
            return
        }

        var num: @Nullable Long? = NumberUtils.toLongOrNull(args[0])
        if (num == null) {
            try {
                num = TimeUtils.parseTimeToMillis(args[0])
            } catch (err: IllegalArgumentException) {
                event.channel.sendMessage(String.format("`%s` is not a valid number / time.", args[0])).queue()
                return
            }
        } else {
            num = TimeUnit.SECONDS.toMillis(num)
        }

        val track = player.playingTrack
        val newPosition = NumberUtils.truncateBetween(track.position + num, 0, track.duration -1)
        track.position = newPosition

        event.channel.sendMessage(String.format("The position has been updated to %s!", FormatUtils.formatDuration(newPosition))).queue()
    }

    override fun getType(): ICommand.Type {
        return ICommand.Type.MUSIC
    }

    override fun getPermission(): ICommand.Permission {
        return ICommand.Permission.DJ
    }

    override fun getHelp(): MessageEmbed.Field {
        return MessageEmbed.Field(description, "Usage: `" + Constants.PREFIX + invoke + " <destination time>`", false)
    }

    override fun getInvoke(): String {
        return "seek"
    }

    override fun getDescription(): String {
        return "Seeks the track to specified time."
    }

    override fun haveEvent(): Boolean {
        return false
    }

    override fun onEvent(event: GenericEvent?) {
        TODO("Not yet implemented")
    }

    override fun requiredDiscordPermission(): List<Permission> {
        return listOf(Permission.MESSAGE_WRITE)
    }
}