package fr.aven.bot.commands.list.slash.`fun`

import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.Activities
import fr.aven.bot.util.lang.LangKey
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands

class Poker: ISlashCmd
{
    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        val invite = Activities.createInvite("poker", event.member?.voiceState?.channel?: return event
            .reply(lang.getString(
                LangKey.keyBuilder(this, "action", "isNotInChannel"),
                "Please join a voice channel first."))
            .setEphemeral(true)
            .queue())
        event.reply(invite).queue()
    }

    override val data: CommandData
        get() = Commands.slash(name, description)
    override val name: String
        get() = "poker"
    override val description: String
        get() = "Starts Poker in your channel"
}