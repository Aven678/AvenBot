package fr.aven.bot.commands

import dev.minn.jda.ktx.SLF4J
import dev.minn.jda.ktx.interactions.option
import dev.minn.jda.ktx.interactions.slash
import dev.minn.jda.ktx.interactions.updateCommands
import fr.aven.bot.commands.music.Play
import fr.aven.bot.core.Main
import fr.aven.bot.music.PlayerManager
import fr.aven.bot.util.Language
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

class CommandManager(val main: Main)
{
    private val commands = mutableMapOf<String, ICommand>()
    private val logger by SLF4J
    val language: Language = main.language

    val playerManager: PlayerManager = PlayerManager(main.config, main.language)

    init {
        logger.info("Init CommandManager...")
        registerCommands(Play(this))
        postCommands()
    }

    private fun registerCommand(command: ICommand)
    {
        commands.putIfAbsent(command.name, command)
    }

    private fun registerCommands(vararg commands: ICommand) {
        commands.forEach { registerCommand(it) }
    }

    private fun postCommands() {
        val commandSlash = mutableListOf< SlashCommandData>()

        main.jda.awaitReady().updateCommands {
            slash("play", "Plays a song") {
                option<String>("song", "URL or title to search in YouTube", true)
            }
        }.queue()
    }

    fun handleCommand(name: String, event: SlashCommandInteractionEvent) {
        if (commands.containsKey(name))
        {
            event.deferReply().setEphemeral(true).queue()
            runBlocking { commands[name]!!.action(event) }
        }
    }
}