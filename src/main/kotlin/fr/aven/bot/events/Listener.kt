package fr.aven.bot.events

import dev.minn.jda.ktx.SLF4J
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object Listener: ListenerAdapter()
{
    private val log by SLF4J

    override fun onReady(event: ReadyEvent) {
        log.info("${event.jda.selfUser.asTag} is connected!")
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        super.onSlashCommandInteraction(event)
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        super.onButtonInteraction(event)
    }
}