package fr.aven.bot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData

interface ICommand
{
    val name: String
    val description: String
    val usage: String
        get() = "/${name}"

    val options: List<OptionData>
        get() = listOf<OptionData>()

    val subCommandGroups: List<SubcommandGroupData>
        get() = listOf<SubcommandGroupData>()

    val subCommands: List<SubcommandData>
        get() = listOf<SubcommandData>()

    suspend fun action(event: SlashCommandInteractionEvent)
}