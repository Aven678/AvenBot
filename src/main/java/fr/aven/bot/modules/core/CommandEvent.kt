package fr.aven.bot.modules.core

import fr.aven.bot.Constants
import fr.aven.bot.Main.getDatabase
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.interactions.commands.OptionMapping

class CommandEvent(messageEvent: GuildMessageReceivedEvent?, slashCommandEvent: SlashCommandEvent?, prefixDefaultUsed: Boolean?)
{
    var messageEvent: GuildMessageReceivedEvent?
    var slashCommandEvent: SlashCommandEvent?
    var args: List<String>
    lateinit var split: List<String>

    var invoke: String

    init {
        this.messageEvent = messageEvent
        this.slashCommandEvent = slashCommandEvent

        if (messageEvent != null) {

            //final String prefix = "&";
            var prefix = Constants.PREFIX
            if (!prefixDefaultUsed!!) prefix = getDatabase().getGuildPrefix(messageEvent.guild) //Constants.PREFIXES.get(event.getGuild().getIdLong());

            this.split = messageEvent.message.contentRaw.replaceFirst(prefix, "").split(" ")
            this.invoke = split[0].toLowerCase()

            this.args = split.subList(1, split.size);
        } else if (slashCommandEvent != null) {
            this.args = emptyList()
            this.invoke = slashCommandEvent.name
        } else throw NullPointerException("Unkown command type")

    }

    fun reply(content: String) {
        messageEvent?.message?.reply(content)?.queue() ?: slashCommandEvent?.hook?.editOriginal(content)?.queue()
    }

    fun replyEmbeds(builder: MessageEmbed, vararg other: MessageEmbed) {
        messageEvent?.message?.replyEmbeds(builder, *other)?.queue() ?: slashCommandEvent?.hook?.editOriginalEmbeds(builder, *other)?.queue()
    }

    var channel: TextChannel = messageEvent?.channel ?: slashCommandEvent!!.textChannel

    fun isMessageEvent(): Boolean {
        return messageEvent != null
    }

    fun isSlashCommand(): Boolean {
        return slashCommandEvent != null
    }

    fun message(): Message {
        if (messageEvent == null) throw NullPointerException("Cannot get a message in a SlashCommand.")
        return messageEvent!!.message
    }

    fun options(): List<OptionMapping> {
        if (slashCommandEvent == null) throw NullPointerException("Cannot get Options in a Message.")
        return slashCommandEvent!!.options
    }

    var JDA: JDA = messageEvent?.jda ?: slashCommandEvent!!.jda
    var guild: Guild = messageEvent?.guild ?: slashCommandEvent!!.guild!!
    var author: User = messageEvent?.author ?: slashCommandEvent!!.user
    var member: Member = messageEvent?.member ?: slashCommandEvent!!.member!!

}
