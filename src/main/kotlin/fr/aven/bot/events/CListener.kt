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

    private suspend inline fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) = CommandManager.handleSlashCommand(event)
    private suspend inline fun onButtonInteraction(event: ButtonInteractionEvent) = CommandManager.handleButtonCommand(event)

    private fun onSelectMenuInteraction(event: SelectMenuInteractionEvent) {
        event.deferEdit().queue()
        CommandManager.playerManager.guildMusicManager(event).searchConfirm(event)
    }


}