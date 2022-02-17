package fr.aven.bot.commands

import dev.minn.jda.ktx.SLF4J
import dev.minn.jda.ktx.interactions.slash
import dev.minn.jda.ktx.interactions.updateCommands
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction
import org.reflections.Reflections

class CommandManager(val jda: JDA)
{
    private val commands = mutableMapOf<String, ICommand>()
    private val logger by SLF4J

    init {
        logger.info("Init CommandManager...")
        registerCommands()
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
        commands.forEach { commandSlash.add(it.value.slash) }

        jda.updateCommands { commandSlash }.queue()
    }

    fun handleCommand(name: String, event: SlashCommandInteractionEvent) {
        if (commands.containsKey(name))
        {
            event.deferReply().queue()
            runBlocking { commands[name]!!.action(event) }
        }
    }
}