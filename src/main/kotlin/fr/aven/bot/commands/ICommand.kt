package fr.aven.bot.commands

import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.*

/**
 * Interface for commands.
 * @property name [String] The name of the command.
 * @property data [CommandData] The data of the command.
 */
interface ICommand {
    val name: String
    val data: CommandData
    val guildOnly: Boolean
        get() = true
}

/**
 * Interface for Slash commands.
 *
 * @property description [String] The description of the command.
 * @property usage [String] The usage of the command.
 * @property options [List] The options of the command.
 * @property subCommandGroups [List] The sub command groups of the command.
 * @property subCommands [List] The sub commands of the command.
 */
interface ISlashCmd : ICommand {
    val description: String
    val usage: String
        get() = "/${name}"
    val options: List<OptionData>
        get() = listOf<OptionData>()
    val subCommandGroups: List<SubcommandGroupData>
        get() = listOf<SubcommandGroupData>()
    val subCommands: List<SubcommandData>
        get() = listOf<SubcommandData>()

    /**
     * Function call when the command is executed.
     */
    suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager)
}

/**
 * Interface for user context commands
 * @property data [CommandData] The data of the command.
 */
interface IUserCmd: ICommand {
    override val data: CommandData
        get() = Commands.user(name)

    /**
     * Function call when the command is executed.
     */
    suspend fun action(event: UserContextInteractionEvent)
}

/**
 * Interface for message context commands
 * @property data [CommandData] The data of the command.
 */
interface IMessageCmd: ICommand {
    override val data: CommandData
        get() = Commands.message(name)

    /**
     * Function call when the command is executed.
     */
    suspend fun action(event: MessageContextInteractionEvent)
}
