package fr.aven.bot.commands.list.slash.music

import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.lang.LangKey
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands

class Shuffle: ISlashCmd
{
    override val data: CommandData
        get() = Commands.slash(name, description)
    override val name: String
        get() = "shuffle"
    override val description: String
        get() = "Shuffle the queue"

    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        val manager = CommandManager.playerManager.guildMusicManager(event)

        if (manager.player.playingTrack == null)
        {
            lang.getString(LangKey.keyBuilder(this, "action", "playerNotActive"), "An error has occurred, please retry.")
            return
        }

        manager.scheduler.shuffleQueue()
        event.reply(lang.getString(LangKey.keyBuilder(this, "action", "success"), "The queue has shuffled.")).queue()
    }
}