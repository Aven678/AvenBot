package fr.aven.bot.events

import dev.minn.jda.ktx.SLF4J
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.core.Main
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Listener(private val main: Main) : ListenerAdapter()
{
    private val log by SLF4J

    override fun onReady(event: ReadyEvent) {
        log.info("${event.jda.selfUser.asTag} is connected!")
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        main.manager.handleCommand(event.name, event)

        super.onSlashCommandInteraction(event)
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val button = event.button
        event.deferEdit().queue()

        when (button.id)
        {
            "m.old" -> {
                main.manager.playerManager.guildMusicManager(event).scheduler.playBackTrack()
            }
            "m.player" -> {
                main.manager.playerManager.guildMusicManager(event).scheduler.changePlayerStatus()
            }
            "m.skip" -> {
                main.manager.playerManager.guildMusicManager(event).scheduler.skipTrack()
            }
            "m.lyrics" -> {
                TODO()
            }
            "m.repeatPlaylist" -> {
                main.manager.playerManager.guildMusicManager(event).scheduler.repeatPlaylist()
            }
            "m.repeatTrack" -> {
                main.manager.playerManager.guildMusicManager(event).scheduler.repeatTrack()
            }
            "m.stop" -> {
                main.manager.playerManager.guildMusicManager(event).scheduler.stopPlayer()
            }
        }
        super.onButtonInteraction(event)
    }
}