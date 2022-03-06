package fr.aven.bot.commands

import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands

/**
 * Interface for commands.
 * @property name [String] The name of the command.
 * @property guildOnly [Boolean] Whether the command is guild only.
 */
interface ICommand {
    val name: String
    val guildOnly: Boolean
        get() = true
}

/**
 * Interface for commands require CommandData
 * @property data [CommandData] The data of the command.
 */
interface IDataCommand : ICommand {
    val data: CommandData
}

/**
 * Interface for Slash commands.
 *
 * @property description [String] The description of the command.
 * @property usage [String] The usage of the command.
 */
interface ISlashCmd : IDataCommand {
    val description: String
    val usage: String
        get() = "/${name}"

    /**
     * Function call when the command is executed.
     */
    suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager)
}

/**
 * Interface for user context commands
 * @property data [CommandData] The data of the command.
 */
interface IUserCmd : IDataCommand {
    override val data: CommandData
        get() = Commands.user(name)

    /**
     * Function call when the command is executed.
     */
    suspend fun action(event: UserContextInteractionEvent, lang: LangManager)
}

/**
 * Interface for message context commands
 * @property data [CommandData] The data of the command.
 */
interface IMessageCmd : IDataCommand {
    override val data: CommandData
        get() = Commands.message(name)

    /**
     * Function call when the command is executed.
     */
    suspend fun action(event: MessageContextInteractionEvent, lang: LangManager)
}

/**
 * Interface for button commands
 */
interface IButtonCmd : ICommand {
    /**
     * Function call when the command is executed.
     */
    suspend fun action(event: ButtonInteractionEvent, lang: LangManager)
}

/**
 * Interface for modal commands
 */
interface IModalCmd : ICommand {
    /**
     * Function call when the command is executed.
     */
    suspend fun action(event: ModalInteractionEvent, lang: LangManager)
}
