package fr.aven.bot.commands.`fun`

import fr.aven.bot.Constants
import fr.aven.bot.Main
import fr.aven.bot.modules.core.CommandEvent
import fr.aven.bot.modules.core.ICommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class FishingtonCommand: ICommand
{
    val activity = Activity()

    override fun handle(args: MutableList<String>?, event: CommandEvent) {
        if (!event.member?.voiceState?.inVoiceChannel()!!) {
            event.channel.sendMessage(Main.getLanguage().getTextFor("join.isNotInChannel", event.guild)).queue()
            return
        }

        val voiceState = event.member!!.voiceState
        val channel = voiceState!!.channel

        event.channel.sendMessage("${Main.getLanguage().getTextFor("link", event.guild)} -> ${activity.getActivityLink("fishington", channel!!)}").queue()
    }

    override fun getType(): ICommand.Type {
        return ICommand.Type.FUN
    }

    override fun getPermission(): ICommand.Permission {
        return ICommand.Permission.USER
    }

    override fun getHelp(): MessageEmbed.Field {
        return MessageEmbed.Field(description, "Usage: `" + Constants.PREFIX + invoke + "`", false)
    }

    override fun getInvoke(): String {
        return "fishington"
    }

    override fun getDescription(): String {
        return "Starts Fishington with your friends :)"
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

    override fun getAlias(): MutableCollection<String> {
        return listOf("poisson", "peche", "pêche", "poissonpeche", "poisson-pêche").toMutableList()
    }
}