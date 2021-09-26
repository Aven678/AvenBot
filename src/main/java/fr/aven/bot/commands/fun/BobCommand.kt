package fr.aven.bot.commands.`fun`

import fr.aven.bot.Constants
import fr.aven.bot.modules.core.CommandEvent
import fr.aven.bot.modules.core.ICommand
import fr.aven.bot.util.MessageUtil
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class BobCommand: ICommand
{
    override fun handle(args: MutableList<String>?, event: CommandEvent) {
        val url = "https://static.wikia.nocookie.net/bob-morris/images/1/1e/Lebobbob.png/revision/latest/scale-to-width-down/331?cb=20190501172814&path-prefix=fr"

        event.channel.sendMessageEmbeds(EmbedBuilder().setImage(url).setColor(MessageUtil.getRandomColor()).build()).queue()
    }

    override fun getType(): ICommand.Type {
        return ICommand.Type.FUN
    }

    override fun getPermission(): ICommand.Permission {
        return ICommand.Permission.USER
    }

    override fun getHelp(): MessageEmbed.Field {
        return MessageEmbed.Field("A picture of Bob Morris","Usage: `${Constants.PREFIX}$invoke`", false)
    }

    override fun getInvoke(): String {
        return "bob"
    }

    override fun haveEvent(): Boolean {
        return false
    }

    override fun onEvent(event: GenericEvent?) {
        TODO("Not yet implemented")
    }

    override fun requiredDiscordPermission(): MutableCollection<Permission> {
        return mutableListOf(Permission.MESSAGE_EMBED_LINKS)
    }
}