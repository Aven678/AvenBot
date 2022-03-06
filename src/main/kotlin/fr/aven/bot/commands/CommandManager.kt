package fr.aven.bot.commands

import dev.minn.jda.ktx.SLF4J
import fr.aven.bot.LANG_LOADER
import fr.aven.bot.commands.CommandManager.Companion.playerManager
import fr.aven.bot.core.Main
import fr.aven.bot.music.PlayerManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import org.reflections.Reflections

/**
 * Manage commands
 * @property main [Main] instance
 * @property playerManager [PlayerManager] instance
 */
class CommandManager(private val main: Main) {
    private val commands = mutableListOf<ICommand>()
    private val logger by SLF4J

    init {
        manager = this
        playerManager = PlayerManager(main.config)

        logger.info("Init CommandManager...")
        registerCommand()
    }

    private fun registerCommand() {
        val reflections: Set<Class<out ICommand>> =
            Reflections("fr.aven.bot.commands.list").getSubTypesOf(ICommand::class.java)
        for (instance in reflections) if (!instance.isInterface) commands.add(instance.getConstructor().newInstance())
        main.jda.awaitReady().updateCommands()
            .addCommands(commands.filterIsInstance(IDataCommand::class.java).map { it.data }).queue()
    }

    companion object {
        /**
         * The instance of the CommandManager
         */
        lateinit var manager: CommandManager
        lateinit var playerManager: PlayerManager

        /**
         * Handle the command
         * @param event [SlashCommandInteractionEvent] The event
         */
        suspend fun handleSlashCommand(event: SlashCommandInteractionEvent) {
            val cmd = manager.commands.find { it.name == event.name && it is ISlashCmd } as? ISlashCmd ?: return

            // TODO : add permission system
            if (cmd.guildOnly && !event.isFromGuild) return
            cmd.action(event, LANG_LOADER.getLangManager(event.user, event.guild))
        }

        /**
         * Handle the button command
         * @param event [ButtonInteractionEvent] The event
         */
        suspend fun handleButtonCommand(event: ButtonInteractionEvent) {
            val cmd = manager.commands.find { event.button.id!!.startsWith(it.name) && it is IButtonCmd } as? IButtonCmd
                ?: return
            cmd.action(event, LANG_LOADER.getLangManager(event.user, event.guild))
        }

    }

}