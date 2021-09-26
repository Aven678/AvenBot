package fr.aven.bot.modules.jda.events

import fr.aven.bot.Main
import fr.aven.bot.modules.core.CommandEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData

class InteractionListener: ListenerAdapter()
{
    override fun onReady(event: ReadyEvent) {
        var commandsArray: MutableList<CommandData> = mutableListOf()

        Main.cmdManager.commands.forEach { cmd ->
            var desc: String = cmd.help.value!!
            if (cmd.help.value!!.length > 100) desc = "Do /${cmd.invoke} -help for more details"
            commandsArray.add(CommandData(cmd.invoke, desc))
        }

        //event.jda.updateCommands().addCommands(commandsArray).queue()
        //event.jda.updateCommands().addCommands(CommandData("help", "Shows commands list")).queue()
        super.onReady(event)
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        if (event.guild == null) return
        event.deferReply().queue()
        Main.cmdManager.handleCommand(CommandEvent(null, event, null))

        super.onSlashCommand(event)
    }
}