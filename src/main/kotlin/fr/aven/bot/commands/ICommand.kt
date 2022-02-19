package fr.aven.bot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

interface ICommand
{
    val name: String
    val description: String
    val usage: String
        get() = "/${name}"

    suspend fun action(event: SlashCommandInteractionEvent)
}