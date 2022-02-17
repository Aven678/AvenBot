package fr.aven.bot.events

import dev.minn.jda.ktx.SLF4J
import fr.aven.bot.commands.CommandManager
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object Listener: ListenerAdapter()
{
    private val log by SLF4J
    private lateinit var manager: CommandManager

    override fun onReady(event: ReadyEvent) {
        this.manager = CommandManager(event.jda)
        log.info("${event.jda.selfUser.asTag} is connected!")
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        super.onSlashCommandInteraction(event)

        manager.handleCommand(event.name, event)
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        super.onButtonInteraction(event)
    }
}