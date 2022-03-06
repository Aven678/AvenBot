package fr.aven.bot.commands.music

import dev.minn.jda.ktx.Embed
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ICommand
import fr.aven.bot.commands.ISlashCmd
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.components.ActionRow
import java.awt.Color
import java.time.Instant

class Queue(private val manager: CommandManager): ISlashCmd
{
    override val name: String
        get() = "queue"
    override val description: String
        get() = "Show the player's queue."

    override suspend fun action(event: SlashCommandInteractionEvent) {
        val guildMusicManager = manager.playerManager.guildMusicManager(event)

        if (guildMusicManager.player.playingTrack == null)
        {
            event.hook.editOriginal(manager.language.getTextFor(event.guild!!, "queue.playerNotActive")).queue()
            return
        }

        guildMusicManager.sendQueueMessage(event)
    }
}