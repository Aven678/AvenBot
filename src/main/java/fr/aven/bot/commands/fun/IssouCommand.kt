package fr.aven.bot.commands.`fun`

import fr.aven.bot.modules.core.CommandEvent
import fr.aven.bot.modules.core.ICommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color

class IssouCommand: ICommand
{
    override fun handle(args: MutableList<String>?, event: CommandEvent) {
        val url = "https://www.justaven.xyz/gif/issou.jpg"

        event.channel.sendMessageEmbeds(EmbedBuilder().setImage(url).setColor(Color.PINK).build())
            .queue()
    }

    override fun getType(): ICommand.Type {
        return ICommand.Type.FUN
    }

    override fun getPermission(): ICommand.Permission {
        return ICommand.Permission.USER
    }

    override fun getHelp(): MessageEmbed.Field {
        return MessageEmbed.Field("Issou !", "Usage: `$invoke`", false)
    }

    override fun getInvoke(): String {
        return "issou"
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