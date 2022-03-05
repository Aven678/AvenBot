package fr.aven.bot.events

import dev.minn.jda.ktx.SLF4J
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.core.Main
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
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

        val guildMusicManager = main.manager.playerManager.guildMusicManager(event)

        with(button.id!!) {
            when {
                equals("m.old") -> guildMusicManager.scheduler.playBackTrack()
                equals("m.player") -> guildMusicManager.scheduler.changePlayerStatus()
                equals("m.skip") -> guildMusicManager.scheduler.skipTrack()
                equals("m.lyrics") -> TODO()
                equals("m.repeatPlaylist") -> guildMusicManager.scheduler.repeatPlaylist()
                equals("m.repeatTrack") -> guildMusicManager.scheduler.repeatTrack()
                equals("m.stop") -> guildMusicManager.scheduler.stopPlayer()

                startsWith("m.queue") -> main.manager.playerManager.guildMusicManager(event).sendQueueMessage(event)
            }
        }
        super.onButtonInteraction(event)
    }

    override fun onSelectMenuInteraction(event: SelectMenuInteractionEvent) {
        event.deferEdit().queue()
        main.manager.playerManager.guildMusicManager(event).searchConfirm(event)

        super.onSelectMenuInteraction(event)
    }
}