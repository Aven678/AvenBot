package fr.aven.bot.commands.`fun`

import fr.aven.bot.util.ICommand
import fr.aven.bot.util.MessageUtil
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color

class NotStonksCommand: ICommand
{
    override fun handle(args: MutableList<String>?, event: GuildMessageReceivedEvent) {
        val url = "https://www.justaven.xyz/gif/not-stonks.png"

        event.channel.sendMessage(EmbedBuilder().setImage(url).setColor(Color.RED).build())
            .queue()
    }

    override fun getType(): ICommand.Type {
        return ICommand.Type.FUN
    }

    override fun getPermission(): ICommand.Permission {
        return ICommand.Permission.USER
    }

    override fun getHelp(): MessageEmbed.Field {
        return MessageEmbed.Field("NotStonks :(", "Usage: `$invoke`", false)
    }

    override fun getInvoke(): String {
        return "notstonks"
    }

    override fun haveEvent(): Boolean {
        return false
    }

    override fun onEvent(event: GenericEvent?) {
        TODO("Not yet implemented")
    }

    override fun requiredDiscordPermission(): List<Permission> {
        return listOf(Permission.MESSAGE_EMBED_LINKS)
    }
}