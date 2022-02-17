package fr.aven.bot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands.slash
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction

interface ICommand
{
    val name: String
    val description: String
    val usage: String
        get() = "/${name}"
    val options: Collection<OptionData>
        get() = emptyList()

    val slash: SlashCommandData
        get() = slash(name, description)

    suspend fun action(event: SlashCommandInteractionEvent)
}