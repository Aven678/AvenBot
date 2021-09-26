package fr.aven.bot.commands.`fun`

import fr.aven.bot.Constants
import fr.aven.bot.Main
import fr.aven.bot.modules.core.CommandEvent
import fr.aven.bot.modules.core.ICommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import okhttp3.OkHttpClient

class YoutubeTogetherCommand: ICommand
{
    private val client = OkHttpClient().newBuilder().build()
    val activity = Activity()

    override fun handle(args: MutableList<String>?, event: CommandEvent) {
        if (!event.member?.voiceState?.inVoiceChannel()!!) {
            event.channel.sendMessage(Main.getLanguage().getTextFor("join.isNotInChannel", event.guild)).queue()
            return
        }

        val voiceState = event.member!!.voiceState
        val channel = voiceState!!.channel

        event.channel.sendMessage("${Main.getLanguage().getTextFor("link", event.guild)} -> ${activity.getActivityLink("youtube", channel!!)}").queue()
    }

    override fun getType(): ICommand.Type {
        return ICommand.Type.FUN
    }

    override fun getPermission(): ICommand.Permission {
        return ICommand.Permission.USER
    }

    override fun getHelp(): MessageEmbed.Field {
        return MessageEmbed.Field("Starts YouTube Together :)", "Usage: `" + Constants.PREFIX + invoke + "`", false)
    }

    override fun getInvoke(): String {
        return "youtube"
    }

    override fun haveEvent(): Boolean {
        return false
    }

    override fun onEvent(event: GenericEvent?) {
        TODO("Not yet implemented")
    }

    override fun requiredDiscordPermission(): List<Permission> {
        return listOf(Permission.CREATE_INSTANT_INVITE)
    }
}