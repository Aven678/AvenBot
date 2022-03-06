package fr.aven.bot.commands

import dev.minn.jda.ktx.SLF4J
import dev.minn.jda.ktx.interactions.option
import dev.minn.jda.ktx.interactions.slash
import dev.minn.jda.ktx.interactions.updateCommands
import fr.aven.bot.LANG_LOADER
import fr.aven.bot.commands.music.Play
import fr.aven.bot.commands.music.Queue
import fr.aven.bot.commands.music.Volume
import fr.aven.bot.core.Main
import fr.aven.bot.music.PlayerManager
import fr.aven.bot.util.Language
import fr.aven.bot.util.lang.LangLoader
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import org.reflections.Reflections

/**
 * Manage commands
 * @property main [Main] instance
 * @property playerManager [PlayerManager] instance
 */
class CommandManager(val main: Main)
{
    private val commands = mutableListOf<ICommand>()
    private val logger by SLF4J

    val playerManager: PlayerManager = PlayerManager(main.config, main.language)

    init {
        manager = this
        logger.info("Init CommandManager...")
        registerCommand()
    }

    private fun registerCommand()
    {
        val reflections: Set<Class<out ICommand?>> = Reflections("fr.aven.bot.commands").getSubTypesOf(ICommand::class.java)
        for (instance in reflections) commands.add(instance.getConstructor().newInstance()!!)
        main.jda.awaitReady().updateCommands().addCommands(commands.map { it.data }).queue()
    }

    companion object {
        /**
         * The instance of the CommandManager
         */
        lateinit var manager: CommandManager

        /**
         * Handle the command
         * @param event [SlashCommandInteractionEvent] The event
         */
        suspend fun handleCommand(event: SlashCommandInteractionEvent){
            manager ?: throw IllegalStateException("CommandManager is not initialized")
            val cmd = manager.commands.find { it.name == event.name && it is ISlashCmd } as? ISlashCmd ?: return

            // TODO : add permission system
            if(cmd.guildOnly && !event.isFromGuild) return
            cmd.action(event, LANG_LOADER.getLangManager(event.user, event.guild))
        }
    }

}