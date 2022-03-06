package fr.aven.bot.events

import dev.minn.jda.ktx.CoroutineEventListener
import dev.minn.jda.ktx.SLF4J
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.core.Main
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CListener(private val main: Main) : CoroutineEventListener
{
    private val log by SLF4J
    override suspend fun onEvent(event: GenericEvent) {
        when(event) {
            is ReadyEvent -> onReady(event)
            is ButtonInteractionEvent -> onButtonInteraction(event)
            is SlashCommandInteractionEvent -> onSlashCommandInteraction(event)
            is SelectMenuInteractionEvent -> onSelectMenuInteraction(event)
        }
    }

    private fun onReady(event: ReadyEvent) = log.info("${event.jda.selfUser.asTag} is connected!")

    private suspend inline fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = CommandManager.handleCommand(event)


    fun onButtonInteraction(event: ButtonInteractionEvent) {
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
    }

    fun onSelectMenuInteraction(event: SelectMenuInteractionEvent) {
        event.deferEdit().queue()
        main.manager.playerManager.guildMusicManager(event).searchConfirm(event)
    }


}