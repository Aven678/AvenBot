package fr.aven.bot.commands.`fun`

import fr.aven.bot.Constants
import fr.aven.bot.modules.core.CommandEvent
import fr.aven.bot.modules.core.ICommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.apache.commons.lang3.StringUtils

class FakewarnCommand: ICommand
{
    override fun handle(args: MutableList<String>, event: CommandEvent) {
        var reason = "Not provided"

        if (event.message().mentionedUsers.size > 0) {
            var argsFinal = args.toMutableList()
            argsFinal.removeFirst()

            if (args.size > 1) reason = StringUtils.join(argsFinal, " ")
            event.channel.sendMessage(String.format("%s has been warned... Reason: %s",
                event.message().mentionedUsers[0].asMention, reason)).queue()
        }

        if (event.guild.selfMember.hasPermission(event.channel, Permission.MESSAGE_MANAGE)) event.message().delete().queue()
    }

    override fun getType(): ICommand.Type {
        return ICommand.Type.FUN
    }

    override fun getPermission(): ICommand.Permission {
        return ICommand.Permission.USER
    }

    override fun getHelp(): MessageEmbed.Field {
        return MessageEmbed.Field("FakeWarn a member", "Usage: `" + Constants.PREFIX + invoke + " <@member>`", false)
    }

    override fun getInvoke(): String {
        return "fakewarn"
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