package fr.aven.bot.commands.music

import dev.minn.jda.ktx.Embed
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ICommand
import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.lang.LangKey
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.components.ActionRow
import java.awt.Color
import java.time.Instant

/**
 * View queue
 */
class Queue(private val manager: CommandManager): ISlashCmd
{
    override val name: String
        get() = "queue"
    override val description: String
        get() = "Show the player's queue."
    override val data: CommandData
        get() = Commands.slash(name, description)

    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        val guildMusicManager = manager.playerManager.guildMusicManager(event)
        guildMusicManager.player.playingTrack ?: return event.reply(lang.getString(LangKey.keyBuilder(this, "", "playerNotActive"),
            "Aucune piste n'est en cours de lecture !"))
            .setEphemeral(true)
            .queue()
        guildMusicManager.sendQueueMessage(event)
    }
}
