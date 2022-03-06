package fr.aven.bot.commands.list.slash.music

import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.lang.LangKey
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands

/**
 * View queue
 */
class Queue: ISlashCmd
{
    override val name: String
        get() = "queue"
    override val description: String
        get() = "Show the player's queue."
    override val data: CommandData
        get() = Commands.slash(name, description)

    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        val guildMusicManager = CommandManager.playerManager.guildMusicManager(event)
        guildMusicManager.player.playingTrack ?: return event.reply(lang.getString(LangKey.keyBuilder(this, "action", "playerNotActive"),
            "The player doesn't play music!"))
            .setEphemeral(true)
            .queue()
        guildMusicManager.sendQueueMessage(event)
    }
}
